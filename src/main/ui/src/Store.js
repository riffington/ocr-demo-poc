import { createStore, combineReducers, compose, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import AllReducers from './reducers/Reducers.js';

//code to setup redux dev tools
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export function buildStore() {
    return createStore(
        combineReducers({ app: AllReducers }),
        // buildInitialState(),
        composeEnhancers(applyMiddleware(thunk))
    );
}

// export function buildInitialState() {
//     return {
//         jwtToken: null,
//         loginError: '',
//         currentTrainingData: {},
//     };
// }
