import API from "../API"

export interface CompanyData {
    id:number;
    name?: string;
    address?: string;
    phone: string;
    email: string;
    url: string;
}

//General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
};

//create company Request
export const CreateCompany = (companyData: CompanyData) => {

    return API.post("/companies", companyData , {headers})
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//get company by id
export const getCompanyById = (id: number) => {

    return API.get(`/companies/${id}`, {headers})   
        .then((res) => res.data)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}

//save new company 
export const SaveCompany = (companyData: CompanyData) => {

    return API.post("/companies", companyData , {headers}) 
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });

}

//update company
export const UpdateCompany = (companyData: CompanyData) => {

    return API.put(`/companies/${companyData.id}`, companyData , {headers}) 
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}


//get all companies
export const getAllCompanies = () => {

    return API.get(`/companies` , {headers}) 
        .then((res) => res)
        .catch((error) => {
            const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
            throw new Error(errorMessage);        
        });
}



