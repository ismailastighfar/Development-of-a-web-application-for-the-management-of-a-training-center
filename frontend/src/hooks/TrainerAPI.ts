import API from "../API"


export interface TrainerData {
    id:number;
    nom?: string;
    surname?: string;
    phone: string;
    email: string;
    password: string;
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

//get trainer by id 
export const getTrainerById = (id: number) => {
    return API.get(`/trainers/${id}`)   
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}

//save new trainer
export const SaveTrainer = (trainerData: TrainerData) => {

    //General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
};
    
return API.post("/trainers", trainerData , {headers})
    .then((res) => res)
    .catch((error) => {
        const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
        throw new Error(errorMessage);        
    });

}

//update trainer
export const UpdateTrainer = (trainerData: TrainerData) => {

        //General Header
    const headers = {
        'Content-Type': 'application/json', // Example header, adjust as needed
    };
    return API.put("/trainers/"+trainerData.id, trainerData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
    
    }