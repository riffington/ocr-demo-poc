import { connect } from 'react-redux';
import UserDropdownMenu from '../components/UserDropdownMenu.jsx';
import { signOut } from '../actions/Actions.js';

export const mapDispatchToProps = (dispatch) => {
    return {
        signOut: (token) => dispatch(signOut(token)),
    };
};

export default connect(null, mapDispatchToProps)(UserDropdownMenu);
