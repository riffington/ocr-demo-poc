import React from 'react';
import { Link } from 'react-router-dom';
import { labelPage, spellcheckPage } from '../Constants.js';

const CountSummary = ({ counts }) => {
    const { spellcheckTaskCount, labelTaskCount } = counts;

    return (
        <div className="">
            <div className="spellchecking">
                <div>
                    <span className="title">
                        Spell checking tasks available: {spellcheckTaskCount}
                    </span>
                </div>
                <div>
                    {spellcheckTaskCount ? (
                        <Link to={spellcheckPage}>Begin Task</Link>
                    ) : (
                        <button disabled>No Tasks Available</button>
                    )}
                </div>
            </div>
            <div className="labelling">
                <div>
                    <span className="title">
                        Labelling tasks available: {labelTaskCount}
                    </span>
                </div>
                <div>
                    {labelTaskCount ? (
                        <Link to={labelPage}>Begin Task</Link>
                    ) : (
                        <button disabled>No Tasks Available</button>
                    )}
                </div>
            </div>
        </div>
    );
};

export default CountSummary;
