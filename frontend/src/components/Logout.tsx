import { useAuth } from "../context/UserContext";
import { useNavigate} from 'react-router-dom';

const navigate = useNavigate();

const Logout = () => {
    const handleLogoutOnClick = () => {
        useAuth().logout();
        navigate('/auth');
    };
    return (
        true && (
            <button
                className="btn btn-primary"
                type="button"
                onClick={handleLogoutOnClick}
            >
                Logout
            </button>
        )
    );
};

export default Logout;
