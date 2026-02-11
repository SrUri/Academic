import { useState, useEffect, useCallback } from 'react';
import '../css/GameBoard.css';
import appleImage from '../assets/apple.png';
import greenSnakeImage from '../assets/greensnake.png';
import blueSnakeImage from '../assets/bluesnake.png';
const boardSize = 10;

const GameBoard = ({ playerId, playerName }) => {
    const [gameState, setGameState] = useState(null);
    const [ws, setWs] = useState(null);
    const [direction, setDirection] = useState({ x: 1, y: 0 });
    const [gameOver, setGameOver] = useState(false);
    const [endMessage, setEndMessage] = useState('');
    const [scoreSubmitted, setScoreSubmitted] = useState(false);

    useEffect(() => {
        const socket = new WebSocket('ws://10.112.159.250:8080');
        socket.onopen = () => {
            socket.send(JSON.stringify({ type: 'setName', playerName }));
        };
        setWs(socket);

        socket.onmessage = (event) => {
            const data = JSON.parse(event.data);
            console.log("Received game state:", data);
            if (data.type === 'init') {
                setGameState(data.gameState);
            } else if (data.type === 'update') {
                setGameState(data.gameState);
            } else if (data.type === 'error') {
                alert(data.message);
                socket.close();
            } else if (data.type === 'gameOver') {
                const { winner } = data;
                setGameOver(true);
                setEndMessage(playerId === winner ? 'You Win!' : 'Game Over');
            }
        };

        return () => socket.close();
    }, [playerId, playerName]);

    const handleKeyDown = useCallback((e) => {
        if (gameOver) return;

        let newDirection;
        switch (e.key) {
            case 'ArrowUp':
                if (direction.y !== 1) newDirection = { x: 0, y: -1 };
                break;
            case 'ArrowDown':
                if (direction.y !== -1) newDirection = { x: 0, y: 1 };
                break;
            case 'ArrowLeft':
                if (direction.x !== 1) newDirection = { x: -1, y: 0 };
                break;
            case 'ArrowRight':
                if (direction.x !== -1) newDirection = { x: 1, y: 0 };
                break 
            default:
                return;
        }

        if (newDirection) {
            setDirection(newDirection);
            if (ws) {
                ws.send(JSON.stringify({ direction: newDirection }));
            }
        }
    }, [gameOver, direction, ws]);

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);
        return () => document.removeEventListener('keydown', handleKeyDown);
    }, [handleKeyDown]);

    useEffect(() => {
        if (gameState) {
            setGameOver(gameState.gameOver);
        }
    }, [gameState]);

    useEffect(() => {
        if (gameOver && endMessage !== 'You Win!' && !scoreSubmitted && gameState) {
          const points = gameState.scores[`player${playerId}`]; 
          if (!scoreSubmitted) {
            setScoreSubmitted(true);
            console.log(`Sending score for ${playerName}: ${points} points`);
            fetch('http://10.112.159.250:8081/api/scores', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({
                playerName,
                points,
              }),
            })
              .then((response) => {
                if (!response.ok) {
                  console.error('Error al enviar la puntuación');
                } else {
                  console.log(`Score for ${playerName} submitted successfully`);
                }
              })
              .catch((error) => console.error('Error:', error));
          }
        }
      }, [gameOver, endMessage, gameState, playerId, playerName, scoreSubmitted]);
      

    const handleRestart = () => {
        if (!gameOver) return;
        setGameOver(false);
        setEndMessage('');
        setDirection({ x: 1, y: 0 });
        setScoreSubmitted(false);
        if (ws) {
            ws.send(JSON.stringify({ type: 'restart' }));
        }
    };

    const renderCell = (col, row) => {
        const snake1 = gameState?.snakes.player1;
        const snake2 = gameState?.snakes.player2;
        const isSnake1 = snake1.some((segment) => segment.x === col && segment.y === row);
        const isSnake2 = snake2.some((segment) => segment.x === col && segment.y === row);
        const isFood = gameState?.food.x === col && gameState?.food.y === row;
        
        let cellStyle = {};
        if (isFood) {
            cellStyle = { backgroundImage: `url(${appleImage})`, backgroundSize: 'cover', backgroundPosition: 'center', backgroundColor: 'red' };
        } else if (isSnake1) {
            const isHead = snake1[0].x === col && snake1[0].y === row;
            cellStyle = isHead 
                ? { backgroundImage: `url(${greenSnakeImage})`, backgroundSize: 'cover', backgroundPosition: 'center', backgroundColor: 'green' }
                : { backgroundColor: 'green' };
        } else if (isSnake2) {
            const isHead = snake2[0].x === col && snake2[0].y === row;
            cellStyle = isHead 
                ? { backgroundImage: `url(${blueSnakeImage})`, backgroundSize: 'cover', backgroundPosition: 'center', backgroundColor: 'blue' }
                : { backgroundColor: 'blue' };
        }
    
        return (
            <div
                key={`${row}-${col}`}
                className="tile"
                style={cellStyle}
            />
        );
    };

    if (!gameState) return <div>Loading...</div>;

    const player1Score = gameState.scores.player1;
    const player2Score = gameState.scores.player2;

    return (
        <div className="game-container">
            <div className="score-board">
                <div>Score Player 1: {player1Score}</div>
                <div>Score Player 2: {player2Score}</div>
            </div>
            <div className="board-container">
                <div className="board">
                    {Array.from({ length: boardSize }).map((_, row) =>
                        Array.from({ length: boardSize }).map((_, col) => renderCell(col, row))
                    )}
                </div>
                {gameOver && (
                    <div className="try-again-container">
                        <button onClick={handleRestart}>Try Again</button>
                    </div>
                )}
            </div>

            {gameOver && (
                <div className="end-message">
                    <div className={endMessage === 'You Win!' ? 'you-win' : 'game-over'}>
                        {endMessage}
                    </div>
                </div>
            )}
        </div>
    );
};

export default GameBoard;