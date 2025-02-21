import * as React from 'react';
import {useNavigate, useParams} from "react-router";

export default function WriteReview() {
    const navigate = useNavigate();
    const params = useParams();

    const userName = params.userName ?? 'Default User';


    return (
        <div>
            <h3>{`Hello, ${userName}!`}</h3>
            <h1>Scrie un review pentru furnizorii de servicii de la evenimentul tau</h1>
            <button onClick={() => navigate('/vendors')}>Navigheaza la sectiunea FURNIZORI</button>
        </div>
    );
}