import { connect } from 'react-redux';
import Main from '../components/Main.jsx';

export const mapStateToProps = (state) => ({
    // token: state.app.auth.jwtToken,
});

export default connect(mapStateToProps, null)(Main);
