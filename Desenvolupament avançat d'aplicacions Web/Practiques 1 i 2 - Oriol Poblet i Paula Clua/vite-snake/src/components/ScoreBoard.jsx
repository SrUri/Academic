import { useEffect, useState } from 'react';
import '../css/ScoreBoard.css';

const ScoreBoard = () => {
    const [scores, setScores] = useState([]);

    useEffect(() => {
        fetch('http://10.112.159.250:8081/api/scores')
            .then((response) => response.json())
            .then((data) => setScores(data));
    }, []);

    // Separar podio (top 3) y el resto
    const podium = scores.slice(0, 3);
    const others = scores.slice(3);

    return (
        <div className="scoreboard">
            <h2>Top Scores</h2>
            <div className="podium">
                {podium.map((player, index) => (
                    <div
                        key={index}
                        className={`circle ${index === 0 ? 'gold' : index === 1 ? 'silver' : 'bronze'}`}
                    >
                        <div className="rank">{index + 1}</div>
                        <div className="player-name">{player.playerName}</div>
                        <div className="points">{player.points} pts</div>
                    </div>
                ))}
            </div>

            {others.length > 0 && (
                <table className="other-scores">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Player</th>
                            <th>Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        {others.map((player, index) => (
                            <tr key={index}>
                                <td>{index + 4}</td>
                                <td>{player.playerName}</td>
                                <td>{player.points} pts</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default ScoreBoard;
