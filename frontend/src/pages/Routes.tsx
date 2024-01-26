import Formations from "./formations/Formations";
import Home from "./home/Home";
import TrainerForm from "./Trainers/TrainerForm";
import CompanyForm from "./Company/CompanyForm";
import TrainingForm from "./Training/TrainingForm";

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
    {
        label: "Company Detail",
        path: "/companydetail/:id",
        element: <CompanyForm />,
        showInNav: false,
    },
    {
        label: "Company Detail",
        path: "/companydetail",
        element: <CompanyForm />,
        showInNav: false,
    },
    {
        label: "Training Detail",
        path: "/trainingdetail/:id",
        element: <TrainingForm />,
        showInNav: false,
    },
    {
        label: "Training Detail",
        path: "/trainingdetail",
        element: <TrainingForm />,
        showInNav: false,
    },
];
