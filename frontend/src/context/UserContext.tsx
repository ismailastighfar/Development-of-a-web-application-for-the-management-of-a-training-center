import { createContext, useContext, useState, ReactNode, FC } from "react";
import { UserData } from "../hooks/UseAPI";

interface AuthContextProps {
    user: UserData | null;
    login: (user: UserData) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider: FC<{ children: ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<UserData | null>(null);

    const login = (userData: UserData) => {
        setUser(userData);
        // You may also want to persist the user data (e.g., in localStorage)
    };

    const logout = () => {
        setUser(null);
        // You may also want to clear the persisted user data
    };

    const contextValue: AuthContextProps = {
        user,
        login,
        logout,
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
