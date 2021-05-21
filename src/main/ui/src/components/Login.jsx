import React, { useState } from 'react';
import { Redirect } from 'react-router-dom';
import Cookies from 'js-cookie';
import { JWT_TOKEN_NAME } from '../Constants.js';

const Login = ({ message, disableSubmit, fetchAuthToken }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        fetchAuthToken(username, password);
        e.preventDefault();
    };

    if (Cookies.get(JWT_TOKEN_NAME)) {
        return <Redirect to="/dashboard" />;
    }

    return (
        <div>
            <form
                onSubmit={(e) => {
                    handleSubmit(e);
                }}
            >
                <div>
                    <label htmlFor="username">Username</label>
                    <input
                        id="username"
                        type="text"
                        name="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        type="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <br />
                <input type="submit" value="Login" disabled={disableSubmit} />
                <br />
                <span>{message}</span>
            </form>
        </div>
    );
};

export default Login;
