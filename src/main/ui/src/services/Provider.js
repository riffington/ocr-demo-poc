import axios from 'axios';

const AUTHENTICATE_API_URL = 'authenticate';
const SIGNOUT_API_URL = 'signout';
const TRAINING_DATA_API_URL = 'api/pages/{id}';
const TASK_COUNTS_DATA_URL = 'api/task/counts?contentType={ct}';
const SPELLCHECK_TASK_URL_BASE = 'api/spellcheck/line';
const SPELLCHECK_TASK_URL_GET = `${SPELLCHECK_TASK_URL_BASE}?contentType={ct}`;

export const doLogin = (username, password) => {
    return axios.post(AUTHENTICATE_API_URL, {
        username,
        password,
    });
};

export const doLogout = () => {
    return axios.get(SIGNOUT_API_URL);
};

export const getTrainingPageData = (pageId, coordType) => {
    const url = TRAINING_DATA_API_URL.replace('{id}', pageId);
    return axios.post(url, {
        coordinatesType: coordType,
    });
};

export const getTaskCountsData = (contentType) => {
    const url = TASK_COUNTS_DATA_URL.replace('{ct}', contentType);
    return axios.get(url);
};

export const getSpellcheckTask = (contentType) => {
    const url = SPELLCHECK_TASK_URL_GET.replace('{ct}', contentType);
    return axios.get(url);
};

export const saveSpellcheckResult = (body) => {
    const url = SPELLCHECK_TASK_URL_BASE;
    return axios.put(url, { ...body });
};
