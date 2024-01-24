import SideMenu from "./SideMenu";
import { Outlet } from "react-router-dom";

const LayoutSide = () => {
    return (
        <div className="layout layout-side">
            <SideMenu />
            <div className="main-content">
                <Outlet />
            </div>
        </div>
    );
};

export default LayoutSide;
