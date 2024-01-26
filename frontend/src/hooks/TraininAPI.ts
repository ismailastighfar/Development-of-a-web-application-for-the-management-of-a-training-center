import API from "../API"


export interface TrainingData {
    id:number;
    title?: string;
    city?: string;
    hours?: number | null;
    cost?: number | null ;
    availableseats?: number | null;
    startDate?: Date | null;
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

    return API.post(`/trainings/${trainerId}/assign/company/${companyId}/trainer/${trainingId}` , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}