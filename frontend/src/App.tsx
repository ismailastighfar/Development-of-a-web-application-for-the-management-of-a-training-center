import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/public/Home";
import Authentificate from "./pages/authentification/Authentificate";
import { useAuth } from "./context/UserContext";
function App() {
    const { user } = useAuth();
    return (
        <Router>
            <Routes>
                <Route path="/auth" element={<Authentificate />} />
                <Route
                    path="/"
                    element={user ? <Home /> : <Authentificate />}
                />
            </Routes>
        </Router>
    );
}

export default App;
