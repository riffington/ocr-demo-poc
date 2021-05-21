import { connect } from 'react-redux';
import Routes from '../components/Routes.jsx';
import Cookies from 'js-cookie';
import { JWT_TOKEN_NAME } from '../Constants.js';

export const mapStateToProps = (state) => ({
    token: Cookies.get(JWT_TOKEN_NAME),
});

export default connect(mapStateToProps, null)(Routes);
