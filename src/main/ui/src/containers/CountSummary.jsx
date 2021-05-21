import { connect } from 'react-redux';
import CountSummary from '../components/CountSummary.jsx';

export const mapStateToProps = (state) => ({
    counts: state.app.tasks.countsData,
});

export default connect(mapStateToProps, null)(CountSummary);
