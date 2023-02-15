import React from 'react';
import {applyMiddleware, combineReducers, createStore} from 'redux';
import {Provider} from 'react-redux';
import thunkMiddleware from 'redux-thunk';

import App from './containers/App.jsx';
import userReducer from './reducers/user';
import userServiceReducer from './reducers/userService';
import professionServiceReducer from './reducers/professionService';
import loaderReducer from './reducers/loader';
import {composeWithDevTools} from "redux-devtools-extension";

const rootReducer = combineReducers({
    user: userReducer,
    userService: userServiceReducer,
    professionService: professionServiceReducer,
    loader: loaderReducer,
});

const store = createStore(
    rootReducer,
    composeWithDevTools(
        applyMiddleware(thunkMiddleware)
    )
);

export default () => (
  <Provider store={store} >
    <App />
  </Provider>
)
