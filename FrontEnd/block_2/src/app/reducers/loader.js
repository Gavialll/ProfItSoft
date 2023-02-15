import {ERROR, LOADER_OFF, LOADER_ON} from "../constants/actionTypes";

const initialState = {
    loader: false,
    error: false,
    message: {
        loading: "Loading...",
        error: "Error!!!"
    }
}
export default (state = initialState, actions)=>{
    switch (actions.type){
        case LOADER_ON:{
            return {
                ...state,
                loader: true
            }
        }
        case LOADER_OFF:{
            return {
                ...state,
                loader: false
            }
        }
        case ERROR:{
            return {
                ...state,
                loader: true,
                error: true,
                message : {
                    ...state.message,
                    error: `Error!!! -> ${actions.message}`
                }
            }
        }
        default: return state
    }
}