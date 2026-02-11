<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>GAMES SHOP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ShopStyle.css" />
</head>

<body>

    <header>
        <h1>GAMES SHOP</h1>
        <a href="${pageContext.request.contextPath}/Web/Login"" id="login-btn">Login</a>
    </header>

    <div id="games-container">
        <!-- Game 1 -->
        <div class="game-box" onclick="redirectToGamePage('game1')">
            <img src="game1.jpg" alt="Game 1">
            <h3>Game 1</h3>
            <p>Console 1</p>
            <p>$49.99</p>
            <p>In Stock</p>
        </div>

        <!-- Game 2 -->
        <div class="game-box" onclick="redirectToGamePage('game2')">
            <img src="game2.jpg" alt="Game 2">
            <h3>Game 2</h3>
            <p>Console 2</p>
            <p>$39.99</p>
            <p>Out of Stock</p>
        </div>

        <!-- Game 3 -->
        <div class="game-box" onclick="redirectToGamePage('game3')">
            <img src="game3.jpg" alt="Game 3">
            <h3>Game 3</h3>
            <p>Console 3</p>
            <p>$59.99</p>
            <p>In Stock</p>
        </div>

        <!-- Game 4 -->
        <div class="game-box" onclick="redirectToGamePage('game4')">
            <img src="game4.jpg" alt="Game 4">
            <h3>Game 4</h3>
            <p>Console 4</p>
            <p>$29.99</p>
            <p>Out of Stock</p>
        </div>

        <!-- Game 5 -->
        <div class="game-box" onclick="redirectToGamePage('game5')">
            <img src="game5.jpg" alt="Game 5">
            <h3>Game 5</h3>
            <p>Console 5</p>
            <p>$79.99</p>
            <p>In Stock</p>
        </div>

        <!-- Game 6 -->
        <div class="game-box" onclick="redirectToGamePage('game6')">
            <img src="game6.jpg" alt="Game 6">
            <h3>Game 6</h3>
            <p>Console 6</p>
            <p>$49.99</p>
            <p>Out of Stock</p>
        </div>
    </div>

    <script>
        function redirectToGamePage(gameId) {
            <a href="${pageContext.request.contextPath}/Web/Login"" id="login-btn">Login</a>
        }
    </script>

</body>
</html>