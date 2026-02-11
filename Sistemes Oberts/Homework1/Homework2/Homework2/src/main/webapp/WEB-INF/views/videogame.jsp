<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles del Juego</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        header {
            background-color: #40E0D0; /* Color turquesa /
            color: #fff;
            text-align: right;
            padding: 10px;
            position: relative;
        }

        #login-btn {
            background-color: #FFC0CB; / Color rosa claro */
            padding: 5px 10px;
            color: #fff;
            text-decoration: none;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .game-details {
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .game-details img {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
        }

        .game-info {
            text-align: left;
            margin-top: 20px;
        }

        .add-to-cart-btn {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
</head>
<body>

    <header>
        <h1>GAMES SHOP</h1>
        <a href="#" id="login-btn">Login</a>
    </header>

    <div class="game-details">
        <img src="game1.jpg" alt="Game 1">
        <div class="game-info">
            <h2>Detalles del Juego</h2>
            <p><strong>Nombre:</strong> Game 1</p>
            <p><strong>Descripción:</strong> Una breve descripción del juego.</p>
            <p><strong>Tipo de Juego:</strong> Acción</p>
            <p><strong>Dirección:</strong> 123 Calle del Juego, Ciudad</p>
            <button class="add-to-cart-btn" onclick="addToCart('Game 1', '$49.99')">Añadir al Carrito</button>
        </div>
    </div>

    <script>
        function addToCart(gameName, price) {
            // Aquí puedes implementar la lógica para añadir el juego al carrito
            alert(¡${gameName} añadido al carrito por ${price}!);
        }
    </script>

</body>
</html>