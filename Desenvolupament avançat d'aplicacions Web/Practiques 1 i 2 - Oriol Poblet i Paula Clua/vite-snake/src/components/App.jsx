import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import GameBoard from '../components/GameBoard';
import ScoreBoard from '../components/ScoreBoard'; 
import NavBar from '../components/Navbar'; 
import '../css/App.css';
import logo from '../assets/logo.png'; // Importar el logo

function App() {
    const [playerName, setPlayerName] = useState('');
    const [isNameSet, setIsNameSet] = useState(false);
    const [selectedPlayer, setSelectedPlayer] = useState('');
    const [isPlayerSelected, setIsPlayerSelected] = useState(false); 

    const handleNameSubmit = (e) => {
        e.preventDefault();
        if (playerName.trim()) {
            setIsNameSet(true);
        }
    };

    const handlePlayerSelect = (player) => {
        setSelectedPlayer(player);
        setIsPlayerSelected(true); 
    };

    if (!isNameSet) {
        return (
            <div className="name-entry-container">
                <div className="name-entry-box">
                    <img src={logo} alt="Snake Game Logo" className="logo" /> 
                    <h1 className="name-entry-title">Welcome to Snake Game</h1>
                    <form onSubmit={handleNameSubmit} className="name-entry-form">
                        <label htmlFor="playerName" className="name-entry-label">
                            Enter your name:
                        </label>
                        <input
                            type="text"
                            id="playerName"
                            value={playerName}
                            onChange={(e) => setPlayerName(e.target.value)}
                            className="name-entry-input"
                            placeholder="Your name"
                        />
                        <button type="submit" className="name-entry-button">
                            Start Game
                        </button>
                    </form>
                </div>
            </div>
        );
    }
    
    return (
        <Router>
            <div className="App">
                <NavBar />
                <h1>Snake Game</h1>
                {!isPlayerSelected && ( 
                    <>
                        <p className="play-as">Play as:</p>
                        <div className="button-container">
                            <Link to="/snake1">
                                <button
                                    className="player1-button"
                                    onClick={() => handlePlayerSelect('Player 1')}
                                >
                                    Player 1
                                </button>
                            </Link>
                            <Link to="/snake2">
                                <button
                                    className="player2-button"
                                    onClick={() => handlePlayerSelect('Player 2')}
                                >
                                    Player 2
                                </button>
                            </Link>
                        </div>
                    </>
                )}
                {selectedPlayer && <p className="playing-as">Playing as {selectedPlayer}</p>}
                <Routes>
                    <Route path="/snake1" element={<GameBoard playerId={1} playerName={playerName} />} />
                    <Route path="/snake2" element={<GameBoard playerId={2} playerName={playerName} />} />
                    <Route path="/score" element={<ScoreBoard />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;