import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./pages/public/Home";
import Authentificate from "./pages/authentification/Authentificate";
const router = createBrowserRouter([
    {
        path: "/",
        element: <Home />,
    },
    {
        path: "auth",
        element: <Authentificate />,
    },
]);
function App() {
    return <RouterProvider router={router} />;
}

export default App;
