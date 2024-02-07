import API from "../API"


export interface TrainingData {
    id:number;
    title?: string;
    city?: string;
    hours?: number | null;
    cost?: number | null ;
    availableSeats?: number | null;
    minSeats?: number | null;
    endEnrollDate?: string | null;
    maxSessions?: number | null;
    objectives?: string;
    detailed_program?: string;
    category?: string;
    forCompany?: boolean;
    trainerId: number | null ;
    companyId: number | null ;
}


//General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
};

//create training Request
export const CreateTraining = (trainingData: TrainingData) => {

    return API.post("/trainings", trainingData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//update training
export const UpdateTraining = (trainingData: TrainingData) => {

    return API.put(`/trainings/${trainingData.id}`, trainingData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}


//get training by id
export const getTrainingById = (id: number) => {

    return API.get(`/trainings/${id}`, {headers})   
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}


//Assign training to trainer and company
export const AssignTrainingToCompany = (trainingId : number , trainerId : number , companyId : number ) => {

    return API.post(`/trainings/${trainingId}/assign/company/${companyId}/trainer/${trainerId}` , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//get all trainings by page
export const getTrainingList = (page: number , size : number) => {

    // /trainings/search?category=<string>&city=<string>&startDate=<string>&page=<integer>&size=<integer>&sort=<string>,<string>`

    return API.get(`/trainings/search?page=${page}&size=${size}` , {headers}) 
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}


//get all trainings
export const getAllTrainings = () => {

    return API.get(`/trainings` , {headers}) 
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//get categories
export const getCategories = () => {

    return API.get(`/trainings/categories` , {headers}) 
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}



// Assing Trainer to Training
export const AssignTrainerToTraining = (trainingId : number , trainerId : number ) => {

    return API.post(`/trainings/${trainingId}/assign/trainer/${trainerId}/individuals` , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//Delete Training
export const DeleteTraining = (id: number) => {

    return API.delete(`/trainings/${id}` , {headers})
        .then((res) => res)
        .catch((error) => {
        const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
        throw new Error(errorMessage);        
    });

}

