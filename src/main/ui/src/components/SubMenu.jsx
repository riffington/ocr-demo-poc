import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { dashboardPage, labelPage, spellcheckPage } from '../Constants.js';

const SubMenu = () => {
    const { pathname } = useLocation();

    const activeStyle = {
        color: 'rgb(238, 238, 238)',
        textDecoration: 'underline',
        textDecorationColor: 'orange',
    };
    const normalStyle = {
        textDecoration: 'none',
        color: 'rgb(238, 238, 238)',
    };

    return (
        <div className="menu-subheader">
            <ul>
                <li>
                    <Link
                        to={dashboardPage}
                        style={
                            pathname === dashboardPage
                                ? activeStyle
                                : normalStyle
                        }
                    >
                        Dashboard
                    </Link>
                </li>
                {pathname === labelPage ? (
                    <li>
                        <Link
                            to={labelPage}
                            style={
                                pathname === labelPage
                                    ? activeStyle
                                    : normalStyle
                            }
                        >
                            Label
                        </Link>
                    </li>
                ) : (
                    <li></li>
                )}
                {pathname === spellcheckPage ? (
                    <li>
                        <Link
                            to={spellcheckPage}
                            style={
                                pathname === spellcheckPage
                                    ? activeStyle
                                    : normalStyle
                            }
                        >
                            Spell Check
                        </Link>
                    </li>
                ) : (
                    <li></li>
                )}
            </ul>
        </div>
    );
};

export default SubMenu;
