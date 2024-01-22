import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./scss/App.scss";
import { AuthProvider } from "./context/UserContext";

ReactDOM.createRoot(document.getElementById("root")!).render(
    <React.StrictMode>
        <AuthProvider>
            <App />
        </AuthProvider>
    </React.StrictMode>
);
