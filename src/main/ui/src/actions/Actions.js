import * as ActionTypes from './ActionTypes.js';
import {
    doLogin,
    doLogout,
    getTrainingPageData,
    getTaskCountsData,
    getSpellcheckTask,
    saveSpellcheckResult,
} from '../services/Provider.js';
import { SuccessfullyLoggedOut } from '../Constants.js';

export const fetchAuthToken = (username, password) => (dispatch) => {
    dispatch({ type: ActionTypes.SIGN_IN });
    return doLogin(username, password)
        .then((response) => {
            dispatch(fetchAuthTokenSuccess(response));
        })
        .catch((error) => {
            dispatch(fetchAuthTokenError(error));
        });
};

export const fetchAuthTokenSuccess = (data) => ({
    type: ActionTypes.SIGN_IN_SUCCESS,
});

export const fetchAuthTokenError = (error) => ({
    type: ActionTypes.SIGN_IN_ERROR,
    loginError: error.message,
});

export const signOut = (token) => (dispatch) => {
    dispatch({ type: ActionTypes.SIGN_OUT });
    return doLogout(token)
        .then((response) => {
            dispatch(signOutSuccess({ response }));
        })
        .catch((error) => {
            dispatch(signOutError(error));
        });
};

export const signOutSuccess = (data) => ({
    type: ActionTypes.SIGN_OUT_SUCCESS,
    jwtToken: data.response.data.jwtToken,
    logoutMessage: SuccessfullyLoggedOut,
});

export const signOutError = (error) => ({
    type: ActionTypes.SIGN_OUT_ERROR,
    errorMessage: error.message,
});

export const fetchTrainingData = (pageId, coordType) => (dispatch) => {
    dispatch({ type: ActionTypes.FETCH_TRAINING_DATA });
    return getTrainingPageData(pageId, coordType)
        .then((response) => {
            dispatch(fetchTrainingDataSuccess({ response }));
        })
        .catch((error) => {
            dispatch(fetchTrainingDataError(error));
        });
};

export const fetchTrainingDataSuccess = (data) => ({
    type: ActionTypes.FETCH_TRAINING_DATA_SUCCESS,
    trainingData: data.response.data,
});

export const fetchTrainingDataError = (error) => ({
    type: ActionTypes.FETCH_TRAINING_DATA_ERROR,
    errorMessage: error.message,
});

export const fetchTaskCountsData = (contentType) => (dispatch) => {
    dispatch({ type: ActionTypes.FETCH_TASK_COUNTS_DATA });
    return getTaskCountsData(contentType)
        .then((response) => {
            dispatch(fetchTaskCountsDataSuccess({ response, contentType }));
        })
        .catch((error) => {
            dispatch(fetchTaskCountsDataError(error));
        });
};

export const fetchTaskCountsDataSuccess = (data) => ({
    type: ActionTypes.FETCH_TASK_COUNTS_DATA_SUCCESS,
    profile: data.contentType,
    taskCounts: data.response.data,
});

export const fetchTaskCountsDataError = (error) => ({
    type: ActionTypes.FETCH_TASK_COUNTS_DATA_ERROR,
    errorMessage: error.message,
});

export const fetchSpellcheckTask = (contentType) => (dispatch) => {
    dispatch({ type: ActionTypes.FETCH_SPELLCHECK_TASK });
    return getSpellcheckTask(contentType)
        .then((response) => {
            dispatch(fetchSpellcheckTaskSuccess({ response }));
        })
        .catch((error) => {
            dispatch(fetchSpellcheckTaskError(error));
        });
};

export const fetchSpellcheckTaskSuccess = (data) => ({
    type: ActionTypes.FETCH_SPELLCHECK_TASK_SUCCESS,
    taskData: data.response.data,
});

export const fetchSpellcheckTaskError = (error) => ({
    type: ActionTypes.FETCH_SPELLCHECK_TASK_ERROR,
    errorMessage: error.message,
});

export const saveSpellcheckTaskResult = (data) => (dispatch) => {
    dispatch({ type: ActionTypes.SAVE_SPELLCHECK_TASK_RESULT });
    return saveSpellcheckResult(data)
        .then(() => {
            dispatch(saveSpellcheckTaskResultSuccess());
        })
        .catch((error) => {
            dispatch(saveSpellcheckTaskResultError(error));
        });
};

export const saveSpellcheckTaskResultSuccess = () => ({
    type: ActionTypes.SAVE_SPELLCHECK_TASK_RESULT_SUCCESS,
});

export const saveSpellcheckTaskResultError = (error) => ({
    type: ActionTypes.SAVE_SPELLCHECK_TASK_RESULT_ERROR,
    errorMessage: error.message,
});
