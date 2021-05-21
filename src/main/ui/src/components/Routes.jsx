import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
} from 'react-router-dom';
import Login from '../containers/Login.jsx';
import Main from '../containers/Main.jsx';

const Routes = ({ token }) => {
    return (
        <Router>
            <Switch>
                <Route path="/login" component={Login}></Route>
                <PrivateRoute
                    path="/"
                    component={Main}
                    jwt={token}
                ></PrivateRoute>
            </Switch>
        </Router>
    );
};

const PrivateRoute = ({ component: Component, jwt, ...rest }) => (
    <Route
        {...rest}
        render={(props) =>
            jwt ? (
                <Component {...props} />
            ) : (
                <Redirect
                    to={{ pathname: '/login', state: { from: props.location } }}
                />
            )
        }
    />
);

export default Routes;
