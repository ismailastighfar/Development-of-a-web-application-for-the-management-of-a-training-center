import { redirect } from "react-router-dom";
import { useAuth } from "../../context/UserContext";

function Home() {
    const { user } = useAuth();
    if (user === undefined) {
        redirect("/auth");
    }
    console.log(user);
    return <div>Welcome to homepage!</div>;
}

export default Home;
