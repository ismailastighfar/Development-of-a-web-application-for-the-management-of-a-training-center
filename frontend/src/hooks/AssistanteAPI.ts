    import API from "../API";

// Generated by https://quicktype.io

export interface AssistantData {
    id:       number;
    nom:      string;
    surname:  string;
    phone:    string;
    password: string;
    email:    string;
}

//General Headers
const headers = {
    "Content-Type": "application/json",
};

//Get Assistant by id
export const GetAssistantById = (id: number) => {
    return API.get(`/assistants/${id}` , { headers })
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
};


//Create Assistant
export const CreateAssistant = (data: AssistantData) => {
    return API.post(`/assistants`, data , { headers })
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
};

//Update Assistant
export const UpdateAssistant = (data: AssistantData) => {
    return API.put(`/assistants/${data.id}`, data , { headers })
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
};


// Get All Assistants
export const GetAllAssistants = () => {
    return API.get(`/assistants` , { headers })
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
};


// Delete Assistant
export const DeleteAssistant = (id: number) => {
    return API.delete(`/assistants/${id}` , { headers })
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
};
