import API from "../API"
interface userData {
    name?: string;
    surname?: string;
}
export const createUser = (userData: userData) => {
    API.post("/user", {userData})
    .then((res) => res);
}