import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { appName } from '../Constants.js';
import UserDropdownMenu from '../containers/UserDropdownMenu.jsx';
import Dashboard from '../containers/Dashboard.jsx';
import ImageMapComponent from '../containers/ImageMapComponent.jsx';
import SpellcheckTask from '../containers/SpellcheckTask.jsx';
import SubMenu from './SubMenu.jsx';

const Main = () => {
    return (
        <Router>
            <>
                <div className="flex-container menu-header">
                    <div className="flex-child app-title">
                        <span>{appName}</span>
                    </div>
                    <div className="flex-child">
                        <UserDropdownMenu></UserDropdownMenu>
                    </div>
                </div>
                <SubMenu></SubMenu>
                <div id="body">
                    <Switch>
                        <Route path="/dashboard" component={Dashboard}></Route>
                        <Route
                            path="/spellcheck"
                            component={SpellcheckTask}
                        ></Route>
                        <Route
                            path="/label"
                            component={ImageMapComponent}
                        ></Route>
                    </Switch>
                </div>
            </>
        </Router>
    );
};

export default Main;
