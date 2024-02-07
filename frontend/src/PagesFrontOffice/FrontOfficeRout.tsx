import Home from "./home/Home"
import UserTrainingsList from "./userTraining/UserTrainingList"
import {Roles} from "../context/UserContext"

export const FrontOfficeRout =[
        {
            label: "Home",
            path: "/frontoffice/home",
            element: <Home />,
            requireAuth: false,
            showInNav: true,
        },
        {
            label: "My Trainings",
            path: "/frontoffice/mytrainings",
            element: <UserTrainingsList />,
            requireAuth: true,
            roles: [Roles.Individual],
            showInNav: true,
        },
]
