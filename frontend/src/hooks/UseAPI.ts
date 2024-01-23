import API from "../API"
export interface UserData {
    nom?: string;
    surname?: string;
    phone: string;
    password: string;
    email: string;
    dateOfBirth:string;

}

const headers = {
    'Content-Type': 'application/json', // Example header, adjust as needed
  };



export const createUser = (userData: UserData) => {
    // Send the `userData` directly, not as { userData: userData }
    return API.post("/individuals", userData , {headers})
      .then((res) => res)
      .catch((error) => {

        const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
        throw new Error(errorMessage);        
      });

}

interface AuthResponse {
  'refresh-token': string;
  'access-token': string;
  id: string;
}

export const logIn = (userData: UserData) => {

    const headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
    };


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

// Helper function to save the authentication token to localStorage
const saveAuthToken = (key: string, token: string) => {
  localStorage.setItem(key, token);
};