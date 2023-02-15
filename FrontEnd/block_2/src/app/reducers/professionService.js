import {ADD_PROFESSIONS} from "../constants/actionTypes";

const initialState = {
    professions: []
}

export default (state = initialState, actions)=>{
    switch (actions.type){
        case ADD_PROFESSIONS:{
            return {
                ...state,
                professions: actions.professions,
            }
        }
        default: return state
    }
}