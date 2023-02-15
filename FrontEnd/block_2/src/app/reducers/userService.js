import {ADD_USERS, DELETE_BY_ID} from "../constants/actionTypes";

const initialState = {
    items: [],
}
export default (state = initialState, actions)=>{
    switch (actions.type){

        case ADD_USERS:{
            actions.items.map(item => {
                item.profession = item.professionName
                delete item.professionName
            })
            return {
                ...state,
                items: actions.items,
            }
        }
        case DELETE_BY_ID:{
            let updateItems = state.items.filter(item => item.id !== actions.id);

            return {
                ...state,
                items: updateItems,
            }
        }
        default: return state
    }
}