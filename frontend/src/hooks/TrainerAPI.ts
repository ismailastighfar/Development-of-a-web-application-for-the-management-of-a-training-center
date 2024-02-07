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

//General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
};

//create user Request
export const ApplyToTrainer = (trainerData: TrainerData) => {
        
    return API.post("/trainers/apply", trainerData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//get trainer by id 
export const getTrainerById = (id: number) => {

    return API.get(`/trainers/${id}`, {headers})   
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}

//save new trainer
export const SaveTrainer = (trainerData: TrainerData) => {
    
    return API.post("/trainers", trainerData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//update trainer
export const UpdateTrainer = (trainerData: TrainerData) => {

    return API.put("/trainers/"+trainerData.id, trainerData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
    
}

//get all trainers
export const getAllTrainers = () => {

    return API.get(`/trainers` , {headers}) 
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//get trainers by status 
export const getTrainersByIsAccept = (IsAccepted : boolean) => {

    return API.get(`/trainers/isAccepted/${IsAccepted}` , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}


//Accept trainer
export const AcceptTrainer = (id: number) => {

    const test_header = {
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
        'Content-Type': 'application/json', // Example header, adjust as needed
    };

    console.log(test_header)
    
    return API.post("/trainers/accept/"+id ,{}, {headers :test_header})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//Delete Trainer
export const DeleteTrainer = (id: number) => {

    return API.delete("/trainers/"+id , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

