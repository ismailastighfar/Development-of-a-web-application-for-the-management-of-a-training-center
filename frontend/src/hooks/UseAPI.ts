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
      .then((res) => res.data)
      .catch((error) => {

        const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
         throw new Error(errorMessage);        
      });

}

export const logIn = (userData: UserData) => {

    const loginData = {
        "email":userData.email,
        "password":userData.password
    }

    return API.post("/login", loginData , {headers})

    .then((res) => res.data)

    .catch((error) => {

      const errorMessage: string = error.response.data?.message || "Conflict error occurred.";
       throw new Error(errorMessage);        
    });


}