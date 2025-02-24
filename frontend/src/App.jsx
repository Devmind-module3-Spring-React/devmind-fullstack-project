import * as React from 'react';
import PropTypes from 'prop-types';
import PasswordIcon from '@mui/icons-material/Password';
import DashboardIcon from '@mui/icons-material/Dashboard';
import DescriptionIcon from '@mui/icons-material/Description';
import CoPresentIcon from '@mui/icons-material/CoPresent';
import {ReactRouterAppProvider} from "@toolpad/core/react-router";
import {Outlet, useNavigate} from "react-router";
import {Provider, useDispatch, useSelector} from "react-redux";
import store from "./redux/stores/stores.js";
import {fetchUserFromToken, logout} from "./redux/reducers/authSlice.js";
import {useEffect} from "react";


function App(props) {

    const {window} = props;
    const navigate = useNavigate();

    // Fetch user from the Redux store
    const userData = useSelector((state) => state.auth.user);
    const dispatch = useDispatch();


    const [session, setSession] = React.useState({
        user: {
            name: '',
            email: '',
            image: '',
        },
    });

    // Persist user between brower tabs - separate redux instances
    const jwt = useSelector((state) => state.auth.jwt);
    React.useEffect(() => {
        if (jwt && !userData) {
            dispatch(fetchUserFromToken());
        }
    }, [jwt, userData, dispatch]);

    // Effect to update session when userData changes
   useEffect(() => {
        if (userData?.username && userData?.email) {
            setSession({
                user: {
                    name: userData.username,
                    email: userData.email,
                    image: 'https://www.eagletechacademy.com/wp-content/uploads/2024/07/user.svg',
                },
            });
        } else {
            // Clear session when userData is null
            setSession(null);
        }
    }, [userData]);

    const NAVIGATION = [
        {
            kind: 'header',
            title: 'Meniu',
        },
        {
            title: 'Home',
            icon: <DashboardIcon/>,
        },
        {
            segment: 'login',
            title: 'Log in ->',
            icon: <PasswordIcon/>,
        },
        {kind: 'divider'},
        {
            segment: 'vendors',
            title: 'Furnizori de servicii',
            icon: <CoPresentIcon/>,
            pattern: 'vendors{/:vendorId}*',
        },
        ...(session && session.user ? [
            {
                segment: 'write-review',
                title: 'Scrie un review',
                icon: <DescriptionIcon/>,
            },
        ] : []),
    ];

    const authentication = React.useMemo(() => {
        return {
            signIn: () => {
                if (userData) {
                setSession({
                    user: {
                        name: userData.username,
                        email: userData.email,
                        image: 'https://www.eagletechacademy.com/wp-content/uploads/2024/07/user.svg',
                    },
                });
                } else {
                    navigate('/login');
                }
            },
            signOut: () => {
                setSession(null);
                dispatch(logout())
            },
        };
    }, [dispatch, navigate, userData]);

    return (
        // <Provider store={store}>
            <ReactRouterAppProvider
                session={session}
                authentication={authentication}
                navigation={NAVIGATION}
                branding={{
                    logo: <img
                        src="https://www.svgrepo.com/show/248010/stars-star.svg"
                        alt="Wedding Vibe logo"
                        style={{
                            width: '50px',
                            height: 'auto',
                            maxWidth: '90%'
                        }}/>,
                    title: 'Wedding Vibes',
                    homeUrl: '/',
                }}
            >
                {/*Outlet is the current displayed component according to the routing from main.jsx*/}
                <Outlet/>

            </ReactRouterAppProvider>
            // preview-end
        // </Provider>
    );
}

App.propTypes = {
    /**
     * Injected by the documentation to work in an iframe.
     * Remove this when copying and pasting into your project.
     */
    window: PropTypes.func,
};

export default App;
