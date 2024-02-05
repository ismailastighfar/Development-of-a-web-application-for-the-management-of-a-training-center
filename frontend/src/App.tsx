import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { routesData } from "./pages/Routes";
import { FrontOfficeRout } from "./PagesFrontOffice/FrontOfficeRout";
import LayoutTop from "./layouts/LayoutTop";
import Authentificate from "./pages/authentification/Authentificate";
import LayoutSide from "./layouts/LayoutSide";
import PasswordRecovery from "./pages/authentification/RecoveryPassword.tsx/PasswordRecovery";


const router = createBrowserRouter([
    {
        element:<LayoutTop />,
        children: FrontOfficeRout.map(({ path, element }) => ({ path, element })),
    },
    {
        element: <LayoutSide />,
        children: routesData.map(({ path, element }) => ({ path, element })),
    },
    {
        path: "/auth",
        element: <Authentificate />,
    },
    {
        path: "/PasswordRecovery",
        element: <PasswordRecovery />,
    }
]);

function App() {
    return <RouterProvider router={router} />;
}

export default App;
