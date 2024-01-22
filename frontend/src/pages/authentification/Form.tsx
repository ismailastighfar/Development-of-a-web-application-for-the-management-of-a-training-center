import { ChangeEvent, useState } from "react";
import { createUser } from "../../hooks/UseAPI";
interface formData {
    name?: string;
    surname?: string;
}

const Form = ({ option }: { option: number }) => {
    const [formData, setFormData] = useState<formData>({
        name: "",
        surname: "",
    });
    // Handle form field changes
    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };
    const handleSubmit = () => {
        createUser(formData);
    };
    return (
        <form className="login-form" onSubmit={handleSubmit}>
            <div
                className={
                    "login-form-fields " +
                    (option === 1 ? "sign-in" : "sign-up")
                }
            >
                <div className="form-group sign-up-input">
                    <label htmlFor="name">Name</label>
                    <input
                        id="name"
                        name="name"
                        type="text"
                        placeholder="Name"
                        value={formData?.name}
                        onChange={handleFormChange}
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="surname">Surname</label>
                    <input
                        id="surname"
                        name="surname"
                        type="text"
                        placeholder="Surname"
                        value={formData?.surname}
                        onChange={handleFormChange}
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="city">City</label>
                    <input
                        id="city"
                        name="city"
                        type="text"
                        placeholder="City"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="E-mail"
                        required
                        autoComplete="email"
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="phone">Phone</label>
                    <input
                        id="phone"
                        name="phone"
                        type="number"
                        placeholder="Phone"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        placeholder="Password"
                        required
                        autoComplete="new-password"
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="repeat-password">Repeat password</label>
                    <input
                        id="repeat-password"
                        name="repeat-password"
                        type="password"
                        placeholder="Repeat password"
                        required={option === 2}
                        disabled={option === 1}
                        autoComplete="new-password"
                    />
                </div>
            </div>
            <button className="btn btn-primary" type="submit">
                {option === 1 ? "Sign in" : "Sign up"}
            </button>
        </form>
    );
};

export default Form;
