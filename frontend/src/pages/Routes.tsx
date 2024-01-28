import Formations from "./formations/Formations";
import Home from "./home/Home";
import TrainerForm from "./Trainers/TrainerForm";
import CompaniesList from "./Company/CompaniesList";
import CompanyForm from "./Company/CompanyForm";
import TrainingForm from "./Training/TrainingForm";
import TrainersList from "./Trainers/TrainersList";
import TrainingList from "./Training/TrainingList";
import CalendarPlanification from "./Planification/CalendarPlanification";
import TrainingSession from "../components/TrainingSession";

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
    {
        label: "Companies",
        path: "/companies",
        element: <CompaniesList />,
        showInNav: true,
    },
    {
        label: "Trainers",
        path: "/trainers",
        element: <TrainersList />,
        showInNav: true,
    },
    {
        label: "Trainings",
        path: "/trainings",
        element: <TrainingList />,
        showInNav: true,
    },
    {
        label: "Planification",
        path: "/planification",
        element: <CalendarPlanification />,
        showInNav: true,
    },
    {
        // start : Date | undefined,
        // end : Date | undefined,
        // startTime : string | undefined,
        // endTime : string
        label: "Training Session",
        path: "/trainingsession",
        element: <TrainingSession trainingId={0} trainingSessionId={0} start={undefined} end={undefined} startTime={""} endTime={""} onSaveClicked={<></>}/>,
        showInNav: true,
    },
];
