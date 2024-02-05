import API from "../API";


//General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
};

//Send a password recovery email
export const sendEmail =  (email: string) => {

    return API.get(`/forgot-password/${email}`, {headers})
    .then((res) => res)
    .catch((error) => {
        const errorMessage: string = error.response.data?.message || error.message || "Conflict error occurred.";
        throw new Error(errorMessage);        
    });
}

//Reset the password
export const resetPassword =  (token: string, newPassword: string) => {


    return API.get(`/reset-password/${token}/${newPassword}`, {headers})
    .then((res) => res)
    .catch((error) => {
        const errorMessage: string = error.response.data?.message || error.message || "Conflict error occurred.";
        throw new Error(errorMessage);        
    });
}