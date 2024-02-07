import { createContext, useContext, useState, ReactNode, FC} from "react";
import { UserData } from "../hooks/UserAPI";
import AccessDenied from "../layouts/AccessDenied"


interface AuthContextProps {
    user: UserData | null;
    login: (user: UserData) => void;    
    logout: () => void;
    authRout: (Roles: string[], Children: any) => any;
    getUserRole: () => string;
    userHasRole: (Roles?: string[]) => boolean;
}


const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const Roles = {

    Individual: "INDIVIDUAL",
    Trainer: "TRAINER",
    Admin: "ADMIN",
    Assistance: "ASSISTANT"

};

export const AuthProvider: FC<{ children: ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<UserData | null>(
        () => {
            // Initialize user state from localStorage if available
            const storedUser = localStorage.getItem("user");
            return storedUser ? JSON.parse(storedUser) : null;
        });

    const login = (userData: UserData) => {
        setUser(userData);
        // You may also want to persist the user data (e.g., in localStorage)
        localStorage.setItem("user", JSON.stringify(userData));
    };

    const logout = () => {
        setUser(null);
        // Remove user data from localStorage on logout
        localStorage.removeItem("user");
        localStorage.removeItem("userRole");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("userId");
    };

    const getUserRole = () => localStorage.getItem("userRole") || Roles.Individual;

    const userHasRole = (Roles?: string[]) => Roles?.includes(getUserRole()) || !Roles?.length;

    const authRout = (Roles: string[], Children: any) => {

        if(Roles.includes(getUserRole()) || !Roles.length){
            return Children
        }
        return <>
            <AccessDenied />
        </>
    }

    const contextValue: AuthContextProps = {
        user,
        login,
        logout,
        authRout,
        getUserRole,
        userHasRole
    };

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};



