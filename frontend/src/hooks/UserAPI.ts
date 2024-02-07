import API from "../API"
import { useAuth , Roles } from "../context/UserContext";

export interface UserData {
    id: number;
    nom?: string;
    surname?: string;
    phone?: string;
    password?: string;
    email?: string;
    dateOfBirth?:string;

}

//General Header
const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
  };


//create user Request
export const createUser = (userData: UserData) => {
    // Send the `userData` directly, not as { userData: userData }
    return API.post("/individuals", userData , {headers})
      .then((res) => res)
      .catch((error) => {
        const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
        throw new Error(errorMessage);        
      });

}


//Login Request

interface AuthResponse {
  'refresh-token': string;
  'access-token': string;
  id: string;
}

export const logIn = (userData: UserData) => {

    const headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
    };


// export interface UserData {
//   id: number;
//   nom?: string;
//   surname?: string;
//   phone: string;
//   password: string;
//   email: string;
//   dateOfBirth:string;

// }

    const loginData = {
        "email":userData.email,
        "password":userData.password
    }

    return API.post("/login", loginData , {headers})
    .then((res) => {
      const authResponse: AuthResponse = res.data;
      saveAuthToken('refreshToken', authResponse['refresh-token']);
      saveAuthToken('accessToken', authResponse['access-token']);
      saveAuthToken('userId', authResponse.id);
      return res;
    })
    .catch((error) => {
      const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
      throw new Error(errorMessage);        
    });
}

//Get user role : 
export const getUserRoleRequest = (UserId : number) => {

    return API.get(`/roleUser/${UserId}`, {headers})
    .then((res) => {
      const userRole =  res.data[0]?.roleName || Roles.Individual;
      saveAuthToken('userRole', userRole);
      console.log('user role is : ',  userRole);
      return userRole;
    })
    .catch((error) => {
      const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
      throw new Error(errorMessage);        
    }); 
}

//Become a Trainer Request : 

export const BecomeTainer = () => {

}

// Helper function to save the authentication token to localStorage
const saveAuthToken = (key: string, token: string) => {
  localStorage.setItem(key, token);
};

