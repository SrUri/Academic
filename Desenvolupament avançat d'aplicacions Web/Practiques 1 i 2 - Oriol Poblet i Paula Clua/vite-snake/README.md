
Para configurar la ejecución de la práctica se deben seguir los siguientes pasos.

1- Desde la misma terminal del IDE, ejecutar en tres terminales distintas lo siguiente dentro del path C:\Users\paula\OneDrive\Escriptori\SNAKEURI2\SNAKEURI2\Practica2DAAW\vite-snake:
1.1- npm run dev
1.2- node server.js
1.3- mvn spring-boot:run

2- En el archivo GameBoard.jsx, debemos modificar la url del tunel de socket, para que apunte a la que nos da ngrok
2.1- Entra a ngrok.exe
2.2- Te autenticas con tu clave
2.3- Ejecutas el siguiente comando para generar una URL de server.js: ngrok http 8080
2.4- Copias la URL que te da ngrok, que tiene un aspecto asi: https://ffad-46-6-42-210.ngrok-free.app
2.5- Pegas la URL en la siguiente linea:         const socket = new WebSocket('ws://localhost:8080');

De forma que quede asi:
        const socket = new WebSocket('wss://ffad-46-6-42-210.ngrok-free.app');


3- Jugar:
3.1- Buscas en tu PC tu IP de Wifi, en mi caso es:
   Dirección IPv4. . . . . . . . . . . . . . : 192.168.1.135

3.2- Debes copiar esa IP en tus dispositivos con el path correcto, de esta manera: http://192.168.1.100:5173/snake1

3.3- A DISFRUTAR!