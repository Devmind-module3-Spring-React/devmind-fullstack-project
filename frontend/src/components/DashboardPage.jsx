import React from "react";
import { Button, useTheme } from "@mui/material";
import { useNavigate } from "react-router";

//Landing page, Home
const LandingPage = () => {
    const navigate = useNavigate();
    const theme = useTheme(); // Adjust depending on black and white theme

    return (
        <div
            style={{
                height: "100vh",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                textAlign: "center",
                padding: "20px",
                backgroundColor: theme.palette.background.default, // Se adaptează automat la temă
                color: theme.palette.text.primary, // Textul devine automat negru pe alb și alb pe negru
            }}
        >
            <h1 style={{ fontSize: "3rem", fontWeight: "bold" }}>
                Descoperă și evaluează furnizori de servicii pentru evenimente
            </h1>
            <p style={{ fontSize: "1.5rem", maxWidth: "700px" }}>
                Planifică evenimentul perfect! Explorează furnizorii, citește recenzii autentice sau adaugă propriile recenzii și alege cei mai buni parteneri pentru evenimentul tău special.
            </p>
            <div style={{ marginTop: "20px", display: "flex", gap: "20px" }}>
                <Button onClick={() => navigate("/vendors")} variant="contained" color="primary" size="large">
                    Explorează Furnizorii
                </Button>
            </div>
        </div>
    );
};

export default LandingPage;
