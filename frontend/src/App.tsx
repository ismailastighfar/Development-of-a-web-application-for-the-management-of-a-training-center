import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { routesData } from "./pages/Routes";
import { FrontOfficeRout } from "./PagesFrontOffice/FrontOfficeRout";
import LayoutTop from "./layouts/LayoutTop";
import Authentificate from "./pages/authentification/Authentificate";
import LayoutSide from "./layouts/LayoutSide";
import PasswordRecovery from "./pages/authentification/RecoveryPassword.tsx/PasswordRecovery";
import EvaluateTraining from "./pages/Evaluation/EvaluateTraning";
import {Roles, useAuth} from "./context/UserContext"



function App() {

    const { authRout } = useAuth();
    // Define routes with authentication checks
    const FrontOfficeRoutes = FrontOfficeRout.map(({ path, element, roles }) => ({
        path,
        element: authRout(roles || [], element),
        }));

    const BackOfficeRouts = routesData.map(({ path, element, roles }) => ({
        path,
        element: authRout(roles || [], element),
    }));


    const router = createBrowserRouter([
        {
            element:<LayoutTop />,
            children: FrontOfficeRoutes,
        },
        {
            element: <LayoutSide />,
            children: BackOfficeRouts,
        },
        {
            path: "/auth",
            element: <Authentificate />,
        },
        {
            path: "/PasswordRecovery",
            element: <PasswordRecovery />,
        },
        {
            path: "/EvaluateTraining/:id?",
            element: authRout([Roles.Individual] ,<EvaluateTraining />),
        }
    ]);

    return <RouterProvider router={router} />;
}

export default App;
