import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { routesData } from "./pages/Routes";
import Authentificate from "./pages/authentification/Authentificate";
import LayoutSide from "./layouts/LayoutSide";

const router = createBrowserRouter([
    {
        element: <LayoutSide />,
        children: routesData.map(({ path, element }) => ({ path, element })),
    },
    {
        path: "/auth",
        element: <Authentificate />,
    },
]);

function App() {
    return <RouterProvider router={router} />;
}

export default App;
