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

