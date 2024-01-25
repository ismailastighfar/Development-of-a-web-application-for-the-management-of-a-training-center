import Formations from "./formations/Formations";
import Home from "./home/Home";
import TrainerForm from "./Trainers/TrainerForm";

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
    {
        label: "Trainer Detail",
        path: "/trainerdetail/:id",
        element: <TrainerForm />,
        showInNav: false,
    },
    {
        label: "Trainer Detail",
        path: "/trainerdetail",
        element: <TrainerForm />,
        showInNav: false,
    },
];
