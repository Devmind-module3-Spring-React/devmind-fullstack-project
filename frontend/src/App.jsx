import * as React from 'react';
import PropTypes from 'prop-types';
import PasswordIcon from '@mui/icons-material/Password';
import DashboardIcon from '@mui/icons-material/Dashboard';
import DescriptionIcon from '@mui/icons-material/Description';
import CoPresentIcon from '@mui/icons-material/CoPresent';
import {ReactRouterAppProvider} from "@toolpad/core/react-router";
import {Outlet} from "react-router";


function App(props) {


    const {window} = props;

    const [session, setSession] = React.useState({
        user: {
            name: 'TestUser',
            email: 'TestUser@outlook.com',
            image: 'https://www.eagletechacademy.com/wp-content/uploads/2024/07/user.svg',
        },
    });

    const NAVIGATION = [
        {
            kind: 'header',
            title: 'Meniu',
        },
        {
            title: 'Home',
            icon: <DashboardIcon />,
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
                setSession({
                    user: {
                        name: 'TestUser',
                        email: 'TestUser@outlook.com',
                        image: 'https://www.eagletechacademy.com/wp-content/uploads/2024/07/user.svg',
                    },
                });
            },
            signOut: () => {
                setSession(null);
            },
        };
    }, []);


    return (
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
                homeUrl: 'api/home',
            }}
        >
            {/*Outlet is the current displayed component according to the routing from main.jsx*/}
            <Outlet/>

        </ReactRouterAppProvider>
        // preview-end
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
