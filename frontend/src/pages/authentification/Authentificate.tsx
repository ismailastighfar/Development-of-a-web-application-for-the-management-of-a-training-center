import { useState } from "react";
import Form from "./Form";
import  SignupTrainer from "./SignupTrainer"

const Authentificate = () => {
    const [option, setOption] = useState<number>(1);

    const handleOptionChange = (newOption: number) => {
        setOption(newOption);
    };

    return (
        <div className="login">
            <div className="login-card">
                <ul className="options">
                    <li
                        className={option === 1 ? "active" : ""}
                        onClick={() => setOption(1)}
                    >
                        Sign in
                    </li>
                    <li
                        className={option === 2 ? "active" : ""}
                        onClick={() => setOption(2)}
                    >
                        Sign up
                    </li>
                    <li
                        className={option === 3 ? "active" : ""}
                        onClick={() => setOption(3)}
                    >
                        Sign up as Trainer
                    </li>
                </ul>
                <header>
                    <h1
                        className={
                            "login-headings " +
                            (option === 1 ? "sign-in" : "sign-up")
                        }
                    >
                        <span>Sign in to your account</span>
                        <span>Create account {option === 3 ? "as Trainer" : ""}</span>
                    </h1>
                </header>
               
                {option === 1 || option === 2 ? (
                    <Form option={option} onOptionChange={handleOptionChange} />
                ) : (
                    <SignupTrainer onOptionChange={handleOptionChange} />
                )}
                
            </div>
        </div>
    );
};
export default Authentificate;
