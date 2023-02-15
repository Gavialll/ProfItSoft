import {ADD_USERS, DELETE_BY_ID} from "../constants/actionTypes";
import {getJson, patchJson, postJson} from "requests";
import {error, loaderOff, loaderOn} from "./loader";
import config from "config";
import * as PAGES from "constants/pages";

const {
    BASE_URL,
    USERS_SERVICE,
} = config;

export const addUsers = (items) => {
    return {
        type: ADD_USERS,
        items
    }
};

export const deleteById = (id) => {
    return {
        type: DELETE_BY_ID,
        id
    }
};

export const fetchGetUsers = () => (dispatch) => {
    dispatch(loaderOn())
    getJson({
        params: {
            from: 0,
            size: 100
        },
        url: `${BASE_URL}/${USERS_SERVICE}`,
    }).then((response) => {
        dispatch(addUsers(response))
        dispatch(loaderOff())
    }).catch(response => {
        dispatch(error(response.statusText))
    })
}

export const fetchDeleteUser = (id) => (dispatch) => {

    dispatch(loaderOn())

    fetch(`${BASE_URL}/${USERS_SERVICE}/${id}`, {
        method: "DELETE"
    }).then(response => {
        if(response.ok){
            dispatch(deleteById(id))
            dispatch(loaderOff())
        }else {
            throw response
        }
    }).catch(response => {
        dispatch(error(response.statusText))
    })
}

export const fetchGetUserById = (id, setUser) => (dispatch) => {
    dispatch(loaderOn())

    getJson({
            url: `${BASE_URL}/${USERS_SERVICE}/${id}`
        }
    ).then(response => {
        response.profession = response.professionName
        delete response.professionName

        dispatch(loaderOff())
        setUser(response)

    }).catch(response => {
        dispatch(error(response.statusText))
    })
}

export const fetchUpdateUser = (user, changePage, locationSearch) => (dispatch) => {
    dispatch(loaderOn())

    user.professionName = user.profession
    delete user.profession

    patchJson({
            body: user,
            url: `${BASE_URL}/${USERS_SERVICE}/${user.id}`
        }
    ).then(response => {
        delete locationSearch.id
        if (response.status === "OK") {
            dispatch(loaderOff())
            changePage({
                locationSearch: {
                    ...locationSearch,
                },
                path: `/${PAGES.USERS}`,
            })
        } else {
            throw response
        }
    }).catch(response => {
        dispatch(error(response.statusText))
    })
}

export const fetchCreateUser = (user, changePage, locationSearch) => (dispatch) => {
    dispatch(loaderOn())
    let newUser = {
        "name": user.name,
        "age": user.age,
        "professionName": user.profession
    }
    postJson({
            body: newUser,
            url: `${BASE_URL}/${USERS_SERVICE}`
        }
    ).then(response => {
        if (response.status === "CREATED") {
            dispatch(loaderOff())
            changePage({
                locationSearch: {
                    ...locationSearch,
                },
                path: `/${PAGES.USERS}`,
            })
        } else {
            throw response
        }
    }).catch(response => {
        dispatch(error(response.statusText))
    })
}


