import {configureStore} from "@reduxjs/toolkit";
import rootReducer from "../reducers/reducers.js";
import authReducer from "../reducers/authSlice.js";

const store = configureStore ({
    reducer: {
        vendors: rootReducer,
        auth: authReducer,
    }
});
export default store;