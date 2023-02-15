import {ADD_PROFESSIONS} from "../constants/actionTypes";
import {getJson} from "requests";
import config from 'config';
import {error} from "./loader";

export const addProfessions = (professions) => {
    return {
        type: ADD_PROFESSIONS,
        professions
    }
};

export const fetchGetProfessions = () => (dispatch) => {
    const {
        BASE_URL,
        PROFESSIONS_SERVICE
    } = config;

    getJson({
        url: `${BASE_URL}/${PROFESSIONS_SERVICE}`
    }).then(response =>{
        dispatch(addProfessions(response))
    }).catch(response => {
        dispatch(error(response.statusText))
    })
}