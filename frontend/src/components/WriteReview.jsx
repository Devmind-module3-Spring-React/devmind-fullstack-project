import * as React from 'react';
import {useNavigate, useParams} from "react-router";

export default function WriteReview() {
    const navigate = useNavigate();
    const params = useParams();

    const userName = params.userName ?? 'Default User';


    return (
        <div>
            <h3>{`Hello, ${userName}!`}</h3>
            <h1>Welcome to my page!</h1>
            <h2>This is my Devmind FullStack project!</h2>
            <button onClick={() => navigate('/vendors')}>Go to VENDORS section</button>
        </div>
    );
}