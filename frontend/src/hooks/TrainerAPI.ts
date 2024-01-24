import API from "../API"


export interface TrainerData {
    nom?: string;
    surname?: string;
    phone: string;
    email: string;
    keywords: string;
    description:string;
}

//create user Request
export const ApplyToTrainer = (trainerData: TrainerData) => {

        //General Header
    const headers = {
        'Content-Type': 'application/json', // Example header, adjust as needed
    };
        
    return API.post("/trainers/apply", trainerData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}