import { combineReducers } from 'redux';
import * as ActionTypes from '../actions/ActionTypes.js';
import { ErrorMessageLookup } from '../Constants.js';

const initialAuth = {
    isLoggingIn: false,
    loginError: '',
    logoutMessage: '',
};

const initialTraining = {
    isFetchingTrainingData: false,
    data: null,
    currentSelectedAreaData: new Map(),
    errorMessage: '',
};

const initialTasks = {
    isFetchingCountsData: false,
    isFetchingTaskData: false,
    isSavingTaskResults: false,
    shouldRefreshCounts: true,
    selectedProfile: '',
    countsData: null,
    taskData: null,
    errorMessage: '',
};

const AuthReducer = (state = initialAuth, action) => {
    switch (action.type) {
        case ActionTypes.SIGN_IN:
            return { ...state, isLoggingIn: true };
        case ActionTypes.SIGN_IN_SUCCESS:
            return {
                ...state,
                isLoggingIn: false,
                loginError: '',
            };
        case ActionTypes.SIGN_IN_ERROR:
            const convertedMessage =
                ErrorMessageLookup[action.loginError] || action.loginError;
            return {
                ...state,
                isLoggingIn: false,
                loginError: convertedMessage,
            };
        case ActionTypes.SIGN_OUT_SUCCESS:
            return {
                logoutMessage: action.logoutMessage,
            };
        case ActionTypes.SIGN_OUT_ERROR:
            return {
                ...state,
                errorMessage: action.errorMessage,
            };
        default:
            return state;
    }
};

const TrainingDataReducer = (state = initialTraining, action) => {
    switch (action.type) {
        case ActionTypes.FETCH_TRAINING_DATA:
            return { ...state, isFetchingTrainingData: true };
        case ActionTypes.FETCH_TRAINING_DATA_SUCCESS:
            return {
                ...state,
                isFetchingTrainingData: false,
                data: action.trainingData,
            };
        case ActionTypes.FETCH_TRAINING_DATA_ERROR:
            const convertedMessage = action.errorMessage;
            return {
                ...state,
                isFetchingTrainingData: false,
                errorMessage: convertedMessage,
            };
        case ActionTypes.SELECT_AREA:
            const mapCopy = new Map(state.currentSelectedAreaData);
            if (mapCopy.has(action.areaData.key)) {
                mapCopy.delete(action.areaData.key);
            } else {
                mapCopy.set(action.areaData.key, action.areaData.value);
            }
            return {
                ...state,
                currentSelectedAreaData: mapCopy,
            };
        default:
            return state;
    }
};

const TaskDataReducer = (state = initialTasks, action) => {
    switch (action.type) {
        case ActionTypes.FETCH_TASK_COUNTS_DATA:
            return {
                ...state,
                shouldRefreshCounts: false,
                isFetchingCountsData: true,
                errorMessage: '',
            };
        case ActionTypes.FETCH_TASK_COUNTS_DATA_SUCCESS:
            return {
                ...state,
                shouldRefreshCounts: false,
                isFetchingCountsData: false,
                selectedProfile: action.profile,
                countsData: action.taskCounts,
                taskData: null,
            };
        case ActionTypes.FETCH_TASK_COUNTS_DATA_ERROR:
            return {
                ...state,
                shouldRefreshCounts: false,
                isFetchingCountsData: false,
                errorMessage: action.errorMessage,
            };
        case ActionTypes.FETCH_SPELLCHECK_TASK:
            return {
                ...state,
                isFetchingTask: true,
                errorMessage: '',
            };
        case ActionTypes.FETCH_SPELLCHECK_TASK_SUCCESS:
            return {
                ...state,
                shouldRefreshCounts: true,
                isFetchingTaskData: false,
                taskData: action.taskData,
            };
        case ActionTypes.FETCH_SPELLCHECK_TASK_ERROR:
            return {
                ...state,
                isFetchingTask: false,
                errorMessage: action.errorMessage,
            };
        case ActionTypes.SAVE_SPELLCHECK_TASK_RESULT:
            return {
                ...state,
                isSavingTaskResults: true,
                errorMessage: '',
            };
        case ActionTypes.SAVE_SPELLCHECK_TASK_RESULT_SUCCESS:
            return {
                ...state,
                isSavingTaskResults: false,
                taskData: null,
            };
        case ActionTypes.SAVE_SPELLCHECK_TASK_RESULT_ERROR:
            return {
                ...state,
                isSavingTaskResults: false,
                errorMessage: action.errorMessage,
            };
        default:
            return state;
    }
};

const appReducers = combineReducers({
    auth: AuthReducer,
    training: TrainingDataReducer,
    tasks: TaskDataReducer,
});

// this ensures that on a successful logout we reset all state as needed
const rootReducer = (state, action) => {
    switch (action.type) {
        case ActionTypes.SIGN_OUT_SUCCESS:
            return appReducers(undefined, action);
        default:
            return appReducers(state, action);
    }
};

export default rootReducer;
