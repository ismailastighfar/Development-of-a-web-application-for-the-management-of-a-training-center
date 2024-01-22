import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/public/Home";
import Authentificate from "./pages/authentification/Authentificate";
function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<Authentificate />} />
            </Routes>
        </Router>
    );
}

export default App;
