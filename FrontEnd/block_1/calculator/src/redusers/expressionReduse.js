let initialState = {
    expressions : []
}

export function expressionReduce(state = initialState, action){
    switch (action.type) {
        case "ADD": {
            return {
                ...state,
                expressions: action.expressions
            }
        }
        default: return state
    }
}