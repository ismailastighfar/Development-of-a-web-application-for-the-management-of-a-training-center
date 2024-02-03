import Home from "./home/Home"
import UserTrainingsList from "./userTraining/UserTrainingList"

export const FrontOfficeRout =[
        {
            label: "Home",
            path: "/frontoffice",
            element: <Home />,
            requireAuth: false,
            showInNav: true,
        },
        {
            label: "My Trainings",
            path: "/frontoffice/mytrainings",
            element: <UserTrainingsList />,
            requireAuth: true,
            showInNav: true,
        },
]