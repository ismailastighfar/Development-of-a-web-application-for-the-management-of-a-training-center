import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/UserContext";
import { useEffect } from "react";

function Home() {
    const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate("/auth");
        }
    }, []);
    return <div>Welcome to homepage!</div>;
}

export default Home;
