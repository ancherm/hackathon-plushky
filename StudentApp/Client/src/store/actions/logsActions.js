import { requester } from '../../infrastructure';
import {
    FETCH_ALL_LOGS_SUCCESS, FETCH_ALL_LOGS_BEGIN, FETCH_ALL_LOGS_ERROR,
    FINDBYUSERNAME_LOGS_SUCCESS, FINDBYUSERNAME_LOGS_BEGIN, FINDBYUSERNAME_LOGS_ERROR,
    CLEARBYUSERNAME_LOGS_SUCCESS, CLEARBYUSERNAME_LOGS_BEGIN, CLEARBYUSERNAME_LOGS_ERROR,
    CLEAR_ALL_LOGS_SUCCESS, CLEAR_ALL_LOGS_BEGIN, CLEAR_ALL_LOGS_ERROR,
} from './actionTypes';

function fetchAllLogsSuccess(response) {
    return {
        type: FETCH_ALL_LOGS_SUCCESS,
        payload: response
    }
}

function fetchAllLogsBegin() {
    return {
        type: FETCH_ALL_LOGS_BEGIN,
    }
}

function fetchAllLogsError(error, message, status, path) {
    return {
        type: FETCH_ALL_LOGS_ERROR,
        error,
        message,
        status,
        path,
    }
}

function fetchAllLogsAction() {
    return (dispatch) => {
        dispatch(fetchAllLogsBegin())
        return requester.get('/logs/all', (response) => {
            if (response.error) {
                const { error, message, status, path } = response;
                dispatch(fetchAllLogsError(error, message, status, path));
            } else {
                dispatch(fetchAllLogsSuccess(response));
            }
        }).catch(err => {
            if (err.status === 403 && err.message === 'Срок действия вашего токена JWT истек. Пожалуйста, войдите в систему!') {
                localStorage.clear();
            }
            dispatch(fetchAllLogsError('', `Error: ${err.message}`, err.status, ''));
        })
    }
};

function findByUserNameSuccess(response, search) {
    return {
        type: FINDBYUSERNAME_LOGS_SUCCESS,
        payload: response,
        message: `Successfully loaded logs for "${search}".`
    }
}

function findByUserNameBegin() {
    return {
        type: FINDBYUSERNAME_LOGS_BEGIN,
    }
}

function findByUserNameError(error, message, status, path) {
    return {
        type: FINDBYUSERNAME_LOGS_ERROR,
        error,
        message,
        status,
        path,
    }
}

function findLogsByUserNameAction(search) {
    return (dispatch) => {
        dispatch(findByUserNameBegin())
        return requester.get('/logs/findByUserName/' + search, (response) => {
            if (response.error) {
                const { error, message, status, path } = response;
                dispatch(findByUserNameError(error, message, status, path));
            } else {
                dispatch(fetchAllLogsSuccess(response));
                dispatch(findByUserNameSuccess(response, search));
            }
        }).catch(err => {
            if (err.status === 403 && err.message === 'Срок действия вашего токена JWT истек. Пожалуйста, войдите в систему!') {
                localStorage.clear();
            }
            dispatch(findByUserNameError('', `Error: ${err.message}`, err.status, ''));
        })
    }
};

function clearByUserNameSuccess(response) {
    return {
        type: CLEARBYUSERNAME_LOGS_SUCCESS,
        payload: response,
    }
}

function clearByUserNameBegin() {
    return {
        type: CLEARBYUSERNAME_LOGS_BEGIN,
    }
}

function clearByUserNameError(error, message, status, path) {
    return {
        type: CLEARBYUSERNAME_LOGS_ERROR,
        error,
        message,
        status,
        path,
    }
}

function clearLogsByUserNameAction(selected) {
    return (dispatch) => {
        dispatch(clearByUserNameBegin())
        return requester.delete('/logs/clearByName/' + selected, {}, (response) => {
            if (response.error) {
                const { error, message, status, path } = response;
                dispatch(clearByUserNameError(error, message, status, path));
            } else {
                dispatch(clearByUserNameSuccess(response));
                dispatch(fetchAllLogsAction());
            }
        }).catch(err => {
            if (err.status === 403 && err.message === 'Срок действия вашего токена JWT истек. Пожалуйста, войдите в систему!') {
                localStorage.clear();
            }
            dispatch(clearByUserNameError('', `Error: ${err.message}`, err.status, ''));
        })
    }
};

function clearAllLogsSuccess(response) {
    return {
        type: CLEAR_ALL_LOGS_SUCCESS,
        payload: response,
    }
}

function clearAllLogsBegin() {
    return {
        type: CLEAR_ALL_LOGS_BEGIN,
    }
}

function clearAllLogsError(error, message, status, path) {
    return {
        type: CLEAR_ALL_LOGS_ERROR,
        error,
        message,
        status,
        path,
    }
}

function clearAllLogsAction() {
    return (dispatch) => {
        dispatch(clearAllLogsBegin())
        return requester.delete('/logs/clear', {}, (response) => {
            if (response.error) {
                const { error, message, status, path } = response;
                dispatch(clearAllLogsError(error, message, status, path));
            } else {
                dispatch(clearAllLogsSuccess(response));
            }
        }).catch(err => {
            if (err.status === 403 && err.message === 'Срок действия вашего токена JWT истек. Пожалуйста, войдите в систему!') {
                localStorage.clear();
            }
            dispatch(clearAllLogsError('', `Error: ${err.message}`, err.status, ''));
        })
    }
};

export { fetchAllLogsAction, findLogsByUserNameAction, clearLogsByUserNameAction, clearAllLogsAction };