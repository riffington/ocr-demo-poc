import React, { useRef } from 'react';
import jwt_decode from 'jwt-decode';
import Cookies from 'js-cookie';
import { JWT_TOKEN_NAME } from '../Constants.js';
import { useDetectOutsideClick } from '../Hooks/useDetectOutsideClick.js';

const UserDropdownMenu = ({ signOut }) => {
    const dropdownRef = useRef(null);
    const [isActive, setIsActive] = useDetectOutsideClick(dropdownRef, false);

    const onMenuButtonClick = () => setIsActive(!isActive);

    const token = Cookies.get(JWT_TOKEN_NAME);
    const tokenInfo = jwt_decode(token);

    const onProfileClick = (e) => {
        console.log(
            'This is where you would be taken to your Profile page. Coming Soon!'
        );
        e.preventDefault();
    };

    const onLogoutClick = (e) => {
        signOut(token);
        e.preventDefault();
    };

    return (
        <div className="container">
            <div className="menu-container">
                <button onClick={onMenuButtonClick} className="menu-trigger">
                    <span>
                        {tokenInfo.firstName + ' ' + tokenInfo.lastName}
                    </span>
                    <img
                        src={'api/image/' + tokenInfo.profileImage}
                        alt="User avatar"
                    />
                </button>
                <nav
                    ref={dropdownRef}
                    className={`menu ${isActive ? 'active' : 'inactive'}`}
                >
                    <ul>
                        <li>
                            <a onClick={onProfileClick}>Profile</a>
                        </li>
                        <li>
                            <a onClick={onLogoutClick}>Logout</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    );
};

export default UserDropdownMenu;
