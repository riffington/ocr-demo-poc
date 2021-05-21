import { connect } from 'react-redux';
import CoordinateTypeMenu from '../components/CoordinateTypeMenu.jsx';
import { fetchTrainingData } from '../actions/Actions.js';

export const mapStateToProps = (state) => ({
    pageId: state.app.training.data?.id,
    currentCoordsType: state.app.training.data?.coordinatesType,
});

export const mapDispatchToProps = (dispatch) => {
    return {
        changeCoordsType: (pageId, type) =>
            dispatch(fetchTrainingData(pageId, type)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(CoordinateTypeMenu);
