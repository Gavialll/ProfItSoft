import {getToken,} from 'token';

const getHeaders = () => ({
    Accept: 'application/json',
    Authorization: `Bearer ${getToken()}`,
    'Content-Type': 'application/json',
});

const fetchGet = ({params = {}, url}) => {
    url = new URL(url);
    url.search = new URLSearchParams(params).toString();
    return fetch(
        url,
        {
            headers: getHeaders(),
            method: 'GET',
        }
    );
};

const fetchPost = ({body, params = {}, url}) => {
    url = new URL(url);
    url.search = new URLSearchParams(params).toString();

    return fetch(
        url,
        {
            body: JSON.stringify(body),
            headers: getHeaders(),
            method: 'POST',
        }
    );
};

const fetchPatch = ({body, params = {}, url}) => {
    url = new URL(url);
    url.search = new URLSearchParams(params).toString();

    return fetch(
        url,
        {
            body: JSON.stringify(body),
            headers: getHeaders(),
            method: 'PATCH',
        }
    );
};

export const getJson = ({
                            params,
                            url,
                        }) => {
    return fetchGet({
        params,
        url,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        throw response;
    });
};

export const postJson = ({
                             body,
                             params,
                             url,
                         }) => {
    return fetchPost({
        body,
        params,
        url,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        throw response;
    });
};

export const patchJson = ({
                             body,
                             params,
                             url,
                         }) => {
    return fetchPatch({
        body,
        params,
        url,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        throw response;
    });
};
