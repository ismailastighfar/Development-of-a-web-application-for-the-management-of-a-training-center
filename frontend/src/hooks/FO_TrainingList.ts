import API from "../API";


export interface TrainingPage {
    category: string;
    city: string;
    startDate: string;
    page: number;
    size: number;
    sort: string;
}


//Get Training List
export const GetTrainingList =  (trainingPage: TrainingPage) => {
    return API.get(`/trainings/search?category=${trainingPage.category}&city=${trainingPage.city}&startDate=${trainingPage.startDate}&page=${trainingPage.page}&size=${trainingPage.size}&sort=${trainingPage.sort}`)
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}


//Enroll to Training
export const EnrollToTraining = (trainingId: number , IndividualId: number) => {
    return API.post(`/trainings/individual/${IndividualId}/enroll/${trainingId}`)
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}

//Get Tranings of an Individual
export const GetIndividualTrainings = (individualId: number) => {
    return API.get(`/individuals/${individualId}/trainings`)
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}

//Cancel Enroll to Training
export const CancelEnrollToTraining = (trainingId: number , IndividualId: number) => {
    return API.post(`/trainings/individual/${IndividualId}/enroll/cancel/${trainingId}`)
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}

