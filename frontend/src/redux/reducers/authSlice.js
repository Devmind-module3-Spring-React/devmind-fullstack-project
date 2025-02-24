import { createSlice } from '@reduxjs/toolkit';

const authSlice = createSlice({
    name: 'auth',
    initialState: {
        user: null,
        jwt: localStorage.getItem('jwt') || null,
        loading: false,
        error: null,
    },
    reducers: {
        loginStart(state) {
            state.loading = true;
            state.error = null;
        },
        loginSuccess(state, action) {
            state.loading = false;
            state.jwt = action.payload.token;
            //we need action.payload.userResponseDto because in backend we return an object that contains the token (see the line above) and the userResponseDto ->> see UserLoginResponseDto
            state.user = action.payload.userResponseDto;
            localStorage.setItem('jwt', action.payload.token);
        },
        loginFailure(state, action) {
            state.loading = false;
            // state.error = action.payload.error;
            state.error = action.payload;
        },
        logout(state) {
            localStorage.removeItem('jwt');
            state.user = null;
            state.jwt = null;
            state.loading = false;
            state.error = null;
        },
        registerStart(state) {
            state.loading = true;
            state.error = null;
        },
        registerSuccess(state, action) {
            state.loading = false;
            state.user = action.payload;
        },
        registerFailure(state, action) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    loginStart,
    loginSuccess,
    loginFailure,
    logout,
    registerStart,
    registerSuccess,
    registerFailure,
} = authSlice.actions;

export const loginUser = (username, password) => (dispatch) => {
    dispatch(loginStart());

    fetch('http://localhost:8081/api/v1/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    })
        .then((response) => response.json())
        .then((data) => {
            //If wrong username or password are inserted, ResponseEntity with an error is returned from backend
            // See GenericControllerAdvice and handleUsernameNotFoundException which adds in the map the key "error" and the value <<error text>>
            if (data.error) {
                throw new Error(data.error);
            }
            dispatch(loginSuccess(data));
        })
        .catch((error) => {
            //dispatch the error text. see javascript Error class -> it contains a message
            dispatch(loginFailure(error.message));
        });
};

export const registerUser = (userData) => (dispatch) => {
    dispatch(registerStart());

    fetch('http://localhost:8081/api/v1/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.error) {
                throw new Error(data.error);
            }
            dispatch(registerSuccess(data));
        })
        .catch((error) => {
            dispatch(registerFailure(error.message));
        });
};

// Persist user between brower tabs - separate redux instances
export const fetchUserFromToken = () => (dispatch) => {
    const token = localStorage.getItem('jwt');

    if (!token) {
        return;
    }

    dispatch(loginStart());

    fetch('http://localhost:8081/api/v1/validate-token', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Invalid token');
            }
            return response.json();
        })
        .then((userData) => {
            // Reconstruct the expected login response format
            const loginData = {
                token: token,
                userResponseDto: userData
            };
            dispatch(loginSuccess(loginData));
        })
        .catch((error) => {
            localStorage.removeItem('jwt');
            dispatch(loginFailure(error.message));
        });
};

export default authSlice.reducer;
