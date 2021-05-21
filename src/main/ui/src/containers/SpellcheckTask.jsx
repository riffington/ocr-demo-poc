import { connect } from 'react-redux';
import SpellcheckTask from '../components/SpellcheckTask';
import {
    fetchSpellcheckTask,
    saveSpellcheckTaskResult,
} from '../actions/Actions.js';

export const mapStateToProps = (state) => ({
    contentType: state.app.tasks.selectedProfile,
    isFetchingTask: state.app.tasks.isFetchingTaskData,
    isSavingTask: state.app.tasks.isSavingTaskResults,
    currentTaskData: state.app.tasks.taskData,
    message: state.app.tasks.errorMessage,
});

export const mapDispatchToProps = (dispatch) => {
    return {
        fetchSpellcheckTask: (contentType) =>
            dispatch(fetchSpellcheckTask(contentType)),
        saveSpellcheckTaskResult: (data) =>
            dispatch(saveSpellcheckTaskResult(data)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpellcheckTask);
