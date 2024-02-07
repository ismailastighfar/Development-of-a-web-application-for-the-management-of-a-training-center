import { NavLink } from "react-router-dom";
import { routesData } from "../pages/Routes";
import logo from "../assets/Logo.png";
import AuthBackOffice from "../components/BackOfficeAuthenfication";
import {useAuth} from "../context/UserContext"


const SideMenu = () => {
    const { userHasRole } = useAuth();
    return (
        <div className="side-menu">
            <div className="app-logo">
                <img src={logo} alt="app logo" />
            </div>
            <div className="nav-items">
                {routesData.map((route, index) => {
                    return (
                        route.showInNav && userHasRole(route.roles) && (
                            <NavLink
                                to={route.path}
                                className={({ isActive }) =>
                                    isActive ? "active" : ""
                                }
                                key={index}
                            >
                                {route.label}
                            </NavLink>
                        )
                    );
                })}
            </div>
            <AuthBackOffice />
        </div>
    );
};

export default SideMenu;
