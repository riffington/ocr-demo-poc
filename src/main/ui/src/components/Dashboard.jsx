import React, { useEffect } from 'react';
import { Ring } from 'react-spinners-css';
import CountSummary from '../containers/CountSummary.jsx';
import '../css/styles.css';

const Dashboard = ({
    isFetchingData,
    doCountsRefresh,
    profile,
    counts,
    fetchTaskCountsData,
}) => {
    useEffect(() => {
        if (doCountsRefresh && profile) {
            fetchTaskCountsData(profile);
        }
    }, []);

    const handleChange = (e) => {
        const value = e.target.value;
        if (value) {
            fetchTaskCountsData(value);
        }
        e.preventDefault();
    };

    return (
        <div className="">
            <div>
                <span>Please select profile type: </span>
                <select
                    style={{ fontSize: '14px' }}
                    defaultValue={profile}
                    onChange={handleChange}
                >
                    <option value=""></option>
                    <option value="Briefs">Appellate Brief</option>
                    <option value="Trials">Trial Documents</option>
                    <option value="Cases">Caselaw Documents</option>
                </select>
            </div>
            <div className="top_spacing">
                <div>
                    {isFetchingData ? (
                        <>
                            <h2>Loading counts...</h2>
                            <Ring color="#ff992b"></Ring>
                        </>
                    ) : counts ? (
                        <CountSummary></CountSummary>
                    ) : (
                        <div className="top_spacing">
                            No data available for current selection.
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
