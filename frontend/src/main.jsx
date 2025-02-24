import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import {createBrowserRouter, RouterProvider} from "react-router";
import Layout from "./components/Layout.jsx";
import VendorsList from "./components/VendorsList.jsx";
import WriteReview from "./components/WriteReview.jsx";
import DashboardPage from "./components/DashboardPage.jsx";
import VendorService from "./components/VendorService.jsx";
import AddVendor from "./components/AddVendor.jsx";
import AuthForm from "./components/LoginSignup/AuthForm.jsx";
import {Provider} from "react-redux";
import store from "./redux/stores/stores.js";


const router = createBrowserRouter([
    {
        Component: App,
        children: [
            {
                path: '/',
                Component: Layout,
                children: [
                    {
                        path: '',
                        Component: DashboardPage,
                    },
                    {
                        path: 'vendors',
                        Component: VendorsList,
                    },
                    {
                        path: 'vendors/:vendorId/services',
                        element: <VendorService />,
                    },
                    {
                        path: 'vendors/add',
                        Component: AddVendor,
                    },
                    {
                        path: 'write-review/',
                        Component: WriteReview,
                    },
                    // {
                    //     path: 'write-review/:userName',
                    //     Component: WriteReview,
                    // },
                ],
            },
            {
                path: 'login',
                Component: AuthForm,
            },
        ],
    },

]);

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <Provider store={store}>
            <RouterProvider router={router}/>
        </Provider>
    </StrictMode>,
)
