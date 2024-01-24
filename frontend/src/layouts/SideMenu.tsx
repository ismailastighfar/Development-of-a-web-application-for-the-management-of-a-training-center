import { NavLink } from "react-router-dom";
import { routesData } from "../pages/Routes";
import logo from "../assets/Logo.png";
import BecomeTrainer from "../components/BecomeTrainer";

const SideMenu = () => {
    return (
        <div className="side-menu">
            <div className="app-logo">
                <img src={logo} alt="app logo" />
            </div>
            <div className="nav-items">
                {routesData.map((route, index) => {
                    return (
                        route.showInNav && (
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
            <BecomeTrainer />
        </div>
    );
};

export default SideMenu;
