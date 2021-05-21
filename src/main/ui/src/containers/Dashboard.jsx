import { connect } from 'react-redux';
import Dashboard from '../components/Dashboard.jsx';
import { fetchTaskCountsData } from '../actions/Actions.js';

export const mapStateToProps = (state) => ({
    isFetchingData: state.app.tasks.isFetchingCountsData,
    doCountsRefresh: state.app.tasks.shouldRefreshCounts,
    profile: state.app.tasks.selectedProfile,
    counts: state.app.tasks.countsData,
});

export const mapDispatchToProps = (dispatch) => {
    return {
        fetchTaskCountsData: (contentType) =>
            dispatch(fetchTaskCountsData(contentType)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Dashboard);
