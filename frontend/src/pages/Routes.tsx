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
import IndivualdTraningList from "./Individuals/IndividualsTraning";
import {Roles} from "../context/UserContext"


export const routesData = [
    {
        label: "Home",
        path: "/",
        element: <Home />,
        roles: [Roles.Admin, Roles.Assistance, Roles.Trainer],
        showInNav: false,
    },
    {
        label: "Trainer Detail",
        path: "/trainerdetail/:id?",
        element: <TrainerForm />,
        roles: [Roles.Admin, Roles.Assistance],
        showInNav: false,
    },
    {
        label: "Company Detail",
        path: "/companydetail/:id?",
        element: <CompanyForm />,
        roles: [Roles.Admin, Roles.Assistance],
        showInNav: false,
    },
    {
        label: "Training Detail",
        path: "/trainingdetail/:id?",
        element: <TrainingForm />,
        roles: [Roles.Admin, Roles.Assistance , Roles.Trainer],
        showInNav: false,
    },
    {
        label: "Trainings",
        path: "/trainings",
        element: <TrainingList />,
        roles: [Roles.Admin, Roles.Assistance , Roles.Trainer],
        showInNav: true,
    },
    {
        label: "Planification",
        path: "/planification/:Trainingid?",
        element: <CalendarPlanification />,
        roles: [Roles.Admin, Roles.Assistance , Roles.Trainer],
        showInNav: false,
    },
    {
        label: "Companies",
        path: "/companies",
        element: <CompaniesList />,
        roles: [Roles.Admin, Roles.Assistance],
        showInNav: true,
    },
    {
        label: "Trainers",
        path: "/trainers",
        element: <TrainersList />,
        roles: [Roles.Admin, Roles.Assistance],
        showInNav: true,
    },
    {
        label: "Individuals",
        path: "/individuals/:id",
        element: <IndivualdTraningList />,
        roles: [Roles.Admin, Roles.Assistance , Roles.Trainer],
        showInNav: false,
    },
    {
        label: "Assistants",
        path: "/assistants",
        element: <AssistantsList />,
        roles: [Roles.Admin],
        showInNav: true,
    },
    {
        label: "AssistantForm",
        path: "/assistantform/:id?",
        element: <AssistantForm />,
        roles: [Roles.Admin],
        showInNav: false,
    }
];
