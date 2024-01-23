import { useState } from "react";
import Form from "./Form";

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
                </ul>
                <header>
                    <h1
                        className={
                            "login-headings " +
                            (option === 1 ? "sign-in" : "sign-up")
                        }
                    >
                        <span>Sign in to your account</span>
                        <span>Create an account</span>
                    </h1>
                </header>
                <Form option={option} onOptionChange={handleOptionChange}/>
            </div>
        </div>
    );
};
export default Authentificate;
