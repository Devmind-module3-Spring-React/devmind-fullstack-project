import {configureStore} from "@reduxjs/toolkit";
import rootReducer from "../reducers/reducers.js";

const store = configureStore ({
    reducer: {
        vendors: rootReducer
    }
});
export default store;