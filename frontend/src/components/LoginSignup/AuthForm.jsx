import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser, registerUser } from '../../redux/reducers/authSlice.js';
import { Navigate } from 'react-router';
import './AuthForm.css'


const AuthForm = () => {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '' // Only used for registration
    });

    const resetFormData = () => ({
        username: '',
        password: '',
        email: '' // Only used for registration
    });

    const dispatch = useDispatch();
    const { jwt, loading, error } = useSelector((state) => state.auth);

    // Handle input change
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();

        //dispatch login action to redux store
        if (isLogin) {
            dispatch(loginUser(formData.username, formData.password));
        } else {
            //dispatch register action
            dispatch(registerUser(formData));
            // e.target.reset();
            setFormData(resetFormData());
        }
    };

    // Redirect after login
    if (jwt) {
        return <Navigate to="/" />;
    }

    return (
        <div className="wrapper">
            <div className="form-box">
                {/* Login / Register Containers */}
                <div className={isLogin ? "login-container" : "register-container"}>
                    <div className="top">
                        <span>
                            {isLogin ? "Don't have an account?" : "Already have an account?"}
                            <a onClick={() => setIsLogin(!isLogin)} style={{ cursor: "pointer" }}>
                                {isLogin ? " Sign Up" : " Log In"}
                            </a>
                        </span>
                    </div>
                    <header className="header-login-page">{isLogin ? "Login" : "Sign Up"}</header>

                    <form onSubmit={handleSubmit}>
                        {!isLogin && (
                            <div className="input-box">
                                <i className="fa fa-envelope"></i>
                                <input
                                    type="email"
                                    name="email"
                                    className="input-field"
                                    placeholder="Email"
                                    value={formData.email}
                                    onChange={handleChange}
                                />
                            </div>
                        )}
                        <div className="input-box">
                            <i className="fa fa-user"></i>
                            <input
                                type="text"
                                name="username"
                                className="input-field"
                                placeholder="Username"
                                value={formData.username}
                                onChange={handleChange}
                                required
                                autoComplete="username"
                            />
                        </div>
                        <div className="input-box">
                            <i className="fa fa-lock"></i>
                            <input
                                type="password"
                                name="password"
                                className="input-field"
                                placeholder="Password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                                autoComplete={isLogin ? "current-password" : "new-password"}
                            />
                        </div>
                        <button className="submit" type="submit" disabled={loading}>
                            {loading ? 'Processing...' : isLogin ? 'Login' : 'Sign Up'}
                        </button>
                        {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}
                    </form>
                </div>
            </div>
        </div>
    );
};

export default AuthForm;
