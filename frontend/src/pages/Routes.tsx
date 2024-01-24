import Home from "./home/Home";
import Formations from "./Formations/Formations";

export const routesData = [
    {
        label: "Home",
        path: "/",
        element: <Home />,
        showInNav: true,
    },
    {
        label: "Formations",
        path: "/formations",
        element: <Formations />,
        showInNav: true,
    },
];
