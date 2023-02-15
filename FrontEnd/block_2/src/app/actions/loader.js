import {ERROR, LOADER_OFF, LOADER_ON} from "../constants/actionTypes";

export const loaderOn = () => ({
    type: LOADER_ON,
});
export const loaderOff = () => ({
    type: LOADER_OFF,
});
export const error = (message) => ({
    type: ERROR,
    message: message
});