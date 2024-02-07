import { useAuth } from "../context/UserContext";
import { useNavigate } from "react-router-dom";

const AuthBackOffice = () => {

    const navigate = useNavigate();

    const { user , logout} = useAuth();

    const handleBlick = () => {
        if (user) {
            logout();
        }
        navigate("/auth");
    };
    return (
        true && (
            <button
                className="btn btn-primary"
                type="button"
                onClick={handleBlick}
            >
                {user ? "Logout" : "Login"}
            </button>
        )
    );
};

export default AuthBackOffice;
