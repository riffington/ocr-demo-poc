import { connect } from 'react-redux';
import Login from '../components/Login.jsx';
import { fetchAuthToken } from '../actions/Actions.js';

export const mapStateToProps = (state) => ({
    message: state.app.auth.loginError || state.app.auth.logoutMessage,
    disableSubmit: state.app.auth.isLoggingIn,
});

export const mapDispatchToProps = (dispatch) => {
    return {
        fetchAuthToken: (username, password) =>
            dispatch(fetchAuthToken(username, password)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
