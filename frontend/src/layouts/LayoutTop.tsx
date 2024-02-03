import TopMenu from "./TopMenu";
import { Outlet } from "react-router-dom";

const LayoutTop = () => {
    return (
        <div className="app">
            <TopMenu />
            <main className="app-main">
                <Outlet />
            </main>
        </div>
    );
};

export default LayoutTop;