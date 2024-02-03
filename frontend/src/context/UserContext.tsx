import { createContext, useContext, useState, ReactNode, FC} from "react";
import { UserData } from "../hooks/UserAPI";

interface AuthContextProps {
    user: UserData | null;
    login: (user: UserData) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

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
