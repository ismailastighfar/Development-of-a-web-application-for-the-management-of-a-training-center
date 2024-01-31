import Formations from "./formations/Formations";
import Home from "./home/Home";
import TrainerForm from "./Trainers/TrainerForm";
import CompaniesList from "./Company/CompaniesList";
import CompanyForm from "./Company/CompanyForm";
import TrainingForm from "./Training/TrainingForm";
import TrainersList from "./Trainers/TrainersList";
import TrainingList from "./Training/TrainingList";
import CalendarPlanification from "./Planification/CalendarPlanification";
import AssistantForm from "./assistante/AssistantceForm";
import AssistantsList from "./assistante/AssistantesList";

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
        path: "/trainerdetail/:id?",
        element: <TrainerForm />,
        showInNav: false,
    },
    {
        label: "Company Detail",
        path: "/companydetail/:id?",
        element: <CompanyForm />,
        showInNav: false,
    },
    {
        label: "Training Detail",
        path: "/trainingdetail/:id?",
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
        path: "/planification/:Trainingid?",
        element: <CalendarPlanification />,
        showInNav: false,
    },
    {
        label: "Assistants",
        path: "/assistants",
        element: <AssistantsList />,
        showInNav: true,
    },
    {
        label: "AssistantForm",
        path: "/assistantform/:assistantId?",
        element: <AssistantForm />,
        showInNav: false,
    }
];
