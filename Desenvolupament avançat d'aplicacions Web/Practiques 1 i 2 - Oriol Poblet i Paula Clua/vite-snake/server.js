import { WebSocketServer } from 'ws';

const wss = new WebSocketServer({ host: '0.0.0.0', port: 8080 });
let players = {}; // Aquí guardamos las conexiones de los jugadores

const initialGameState = () => ({
  snakes: {
    player1: [{ x: 2, y: 2 }],
    player2: [{ x: 7, y: 7 }],
  },
  food: { x: Math.floor(Math.random() * 10), y: Math.floor(Math.random() * 10) },
  directions: {
    player1: { x: 0, y: 0 },
    player2: { x: 0, y: 0 },
  },
  scores: { // Nueva propiedad para las puntuaciones
    player1: 0,
    player2: 0,
  },
  gameOver: false,
});

let gameState = initialGameState();

const resetFoodPosition = () => {
  do {
    gameState.food = { x: Math.floor(Math.random() * 10), y: Math.floor(Math.random() * 10) };
  } while (
    gameState.snakes.player1.some(
      (segment) => segment.x === gameState.food.x && segment.y === gameState.food.y
    ) ||
    gameState.snakes.player2.some(
      (segment) => segment.x === gameState.food.x && segment.y === gameState.food.y
    )
  );
};

resetFoodPosition();
let gameInterval;

const startGameLoop = () => {
  gameInterval = setInterval(() => {
    if (gameState.gameOver) {
      clearInterval(gameInterval);
      return;
    }

    Object.keys(gameState.snakes).forEach((player) => {
      const snake = gameState.snakes[player];
      const dir = gameState.directions[player];

      if (!dir || (dir.x === 0 && dir.y === 0)) return;

      const head = snake[0];
      const newHead = { x: head.x + dir.x, y: head.y + dir.y };

      const isCollision =
        newHead.x < 0 ||
        newHead.y < 0 ||
        newHead.x >= 10 ||
        newHead.y >= 10 ||
        snake.some((segment) => segment.x === newHead.x && segment.y === newHead.y) ||
        Object.keys(gameState.snakes).some((otherPlayer) => {
          if (otherPlayer !== player) {
            return gameState.snakes[otherPlayer].some(
              (segment) => segment.x === newHead.x && segment.y === newHead.y
            );
          }
          return false;
        });

      if (isCollision) {
        gameState.gameOver = true;
        const loser = player;
        const winner = Object.keys(gameState.snakes).find((p) => p !== loser);

        Object.values(players).forEach((client) => {
          if (client.readyState === client.OPEN) {
            client.send(
              JSON.stringify({
                type: 'gameOver',
                winner,
                loser,
              })
            );
          }
        });
        return;
      }

      snake.unshift(newHead);

      if (newHead.x === gameState.food.x && newHead.y === gameState.food.y) {
        // Si la serpiente come una manzana, genera una nueva posición para la comida
        resetFoodPosition();
        gameState.scores[player]++; // Incrementa la puntuación del jugador
      } else {
        // Si no come una manzana, elimina el último segmento
        snake.pop();
      }
    });

    // Envía el estado del juego actualizado a todos los clientes
    Object.values(players).forEach((client) => {
      if (client.readyState === client.OPEN) {
        client.send(JSON.stringify({ type: 'update', gameState }));
      }
    });
  }, 200);
};

wss.on('connection', (ws) => {
  let playerId = null;

  // Comprobamos cuántos jugadores están conectados
  const availableSlots = Object.keys(players).filter((key) => key === 'player1' || key === 'player2').length;

  if (availableSlots < 2) {
    playerId = availableSlots === 0 ? 'player1' : 'player2';
    players[playerId] = ws;

    console.log(`${playerId} has connected`);
    ws.send(JSON.stringify({ type: 'init', playerId, gameState }));

    ws.on('message', (message) => {
      const parsedMessage = JSON.parse(message);
      if (parsedMessage.type === 'setName') {
        // Reinicia el estado del juego al establecer un nuevo nombre
        if (!players[playerId].name) {
            players[playerId].name = parsedMessage.playerName;
            console.log(`${parsedMessage.playerName} assigned to ${playerId}`);
            
            // Reiniciar el estado del juego
            gameState = initialGameState();
            resetFoodPosition();
    
            // Detener cualquier intervalo previo antes de iniciar uno nuevo
            clearInterval(gameInterval);
            startGameLoop();
    
            // Notificar a todos los jugadores que el juego ha sido reiniciado
            Object.values(players).forEach((client) => {
                if (client.readyState === client.OPEN) {
                    client.send(JSON.stringify({ type: 'init', playerId, gameState }));
                }
            });
        }
    }
    
    

      const { type, direction } = parsedMessage;
      if (type === 'restart') {
        gameState = initialGameState();
        resetFoodPosition();
    
        // Detener cualquier intervalo previo antes de iniciar uno nuevo
        clearInterval(gameInterval);
        startGameLoop();
    
        Object.values(players).forEach((client) => {
            if (client.readyState === client.OPEN) {
                client.send(JSON.stringify({ type: 'init', playerId, gameState }));
            }
        });
    } else if (gameState.directions[playerId] && !gameState.gameOver) {
        gameState.directions[playerId] = direction || { x: 0, y: 0 };
      }
    });

    ws.on('close', () => {
      console.log(`${playerId} has disconnected`);
      delete players[playerId];
    });
  } else {
    ws.send(JSON.stringify({ type: 'error', message: 'Game is full' }));
    ws.close();
  }
});

startGameLoop();

console.log('WebSocket server running on ws://localhost:8080');