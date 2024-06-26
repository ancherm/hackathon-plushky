const BASE_URL = 'http://localhost:8000';

const getAuthHeader = () => {
    const token = localStorage.getItem("token");

    return (token && token.length)
        ? { 'Authorization': `Bearer ${token}` }
        : {}
}

export default {
    get: (endpoint, callback) => {
        return fetch(BASE_URL + endpoint, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                ...getAuthHeader(),
            },
        }).then(checkStatus)
            .then(callback);
    },

    post: (endpoint, data, callback) => {
        return fetch(BASE_URL + endpoint, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                ...getAuthHeader(),
            },

            body: JSON.stringify(data)
        }).then(checkStatus)
            .then(callback);
    },

    put: (endpoint, data, callback) => {
        return fetch(BASE_URL + endpoint, {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                ...getAuthHeader(),
            },
            body: JSON.stringify(data)
        }).then(checkStatus)
            .then(callback);
    },

    delete: (endpoint, data, callback) => {
        return fetch(BASE_URL + endpoint, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                ...getAuthHeader(),
            },
            body: JSON.stringify(data)
        }).then(checkStatus)
            .then(callback)
    },

    update: (data) => {
        return fetch(BASE_URL + '/users/update', {
            method: 'put',
            body: JSON.stringify(data),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                ...getAuthHeader(),
            }
        }).then(checkStatus)
            .then(() => console.log('updated!!!'))

    },

    addPhoto: (endpoint, data, callback) => {
        return fetch(BASE_URL + endpoint, {
            method: 'POST',
            headers: {
                ...getAuthHeader()
            },
            body: data
        }).then(checkStatus)
            .then(callback)
    }
}

function checkStatus(response) {
    if (response.status >= 200 && response.status < 300) {
        return response.json()
    } else {
        var error = new Error(response.statusText);
        if (response.status === 403 && response.url === (BASE_URL + '/login')) {
            error.message = 'Неверные учетные данные!';
            error.response = response;
            throw error;
        } else if (response.status === 403 && response.type === 'cors') {
            console.log('err response: ', response)
            error.message = 'Срок действия вашего токена JWT истек. Пожалуйста, войдите в систему!'
            error.status = 403;
            error.type = 'cors'
            throw error;
        } else if (response.status === 400) {
            console.log('err response: ', response)
            error.message = 'Ошибка: Неверный запрос'
            error.status = 400;
            error.type = 'cors'
            throw error;
        }
        else {
            return response.json();
        }
    }
}