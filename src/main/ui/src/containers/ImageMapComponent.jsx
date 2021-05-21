import { connect } from 'react-redux';
import * as ActionTypes from '../actions/ActionTypes.js';
import ImageMapComponent from '../components/ImageMapComponent.jsx';
import { fetchTrainingData } from '../actions/Actions.js';

export const mapStateToProps = (state) => ({
    isFetchingData: state.app.training.isFetchingTrainingData,
    trainingData: state.app.training.data,
    selectedData: state.app.training.currentSelectedAreaData,
});

export const mapDispatchToProps = (dispatch) => {
    return {
        fetchTrainingData: (pageId, coordType) =>
            dispatch(fetchTrainingData(pageId, coordType)),
        selectArea: (currentSelectedArea) =>
            dispatch({
                type: ActionTypes.SELECT_AREA,
                areaData: currentSelectedArea,
            }),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(ImageMapComponent);
