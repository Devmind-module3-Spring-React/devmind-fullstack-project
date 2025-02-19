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
                        path: 'vendors/:vendorId',
                        element: <VendorService />,
                        pattern: 'vendors{/:vendorId}*',
                    },
                    {
                        path: 'write-review/',
                        Component: WriteReview,
                    },
                    {
                        path: 'write-review/:userName',
                        Component: WriteReview,
                    },
                ],
            },
        ],
    },
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <RouterProvider router={router} />
  </StrictMode>,
)
