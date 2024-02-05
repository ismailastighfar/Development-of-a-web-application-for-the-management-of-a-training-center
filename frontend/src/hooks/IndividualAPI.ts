import API from "../API";


export interface IndividualData { 

    nom: string;
    surname: string;
    email: string;
    phone: string;

}

//General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
};

// Get Indvidual Data of a Training
export const getIndividualsbyTraning = (Trainingid: number) => {

    return API.get(`/trainings/${Trainingid}/individuals`, {headers})   
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}
