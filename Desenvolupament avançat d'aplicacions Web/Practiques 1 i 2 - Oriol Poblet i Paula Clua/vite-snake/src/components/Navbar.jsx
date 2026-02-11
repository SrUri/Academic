// src/components/Navbar.jsx
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png';
import '../css/Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-logo">
                <img src={logo} alt="Logo" />
            </div>
            <div className="navbar-links">
                <Link to="/">
                    <button className="navbar-button">Jugar</button>
                </Link>
                <Link to="/score">
                    <button className="navbar-button">Top Scores</button>
                </Link>
            </div>
        </nav>
    );
};

export default Navbar;