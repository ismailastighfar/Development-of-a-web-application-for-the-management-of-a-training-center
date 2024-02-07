import { ChangeEvent, FormEvent, useState } from "react";
import { UserData, createUser, logIn , getUserRoleRequest} from "../../hooks/UserAPI";
import { useNavigate } from "react-router-dom";
import { useAuth , Roles} from "../../context/UserContext";

const Form = ({
    option,
    onOptionChange,
}: {
    option: number;
    onOptionChange: (newOption: number) => void;
}) => {
    const navigate = useNavigate();
    const { login } = useAuth();

    const initialFormData: UserData = {
        id: 0,
        nom: "",
        surname: "",
        phone: "",
        password: "",
        email: "",
        dateOfBirth: "",
    };

    const [formData, setFormData] = useState<UserData>(initialFormData);

    const [repeatPassword, setRepeatPassword] = useState<string>("");

    // Handle form field changes
    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior

        if (option === 2) {
            try {
                //validate the password before the saving
                if (formData.password != repeatPassword) {
                    alert("Please enter the same password");
                    return;
                }
                const response = await createUser(formData);
                if (response.status === 201 || response.status === 200) {
                    // Add a message bofore doing something else
                    alert(
                        "User created successfully!, please sign in to your account"
                    );
                    // Clear the form data after successful registration
                    setFormData(initialFormData);
                    onOptionChange(1);
                } else {
                    alert("Failed to create user. Please try again.");
                }
            } catch (error) {
                alert(error);
            }
        } else {
            try {
                const response = await logIn(formData);
                console.log("Status is " + response.status);
                if (response.status === 200) {
                    const userData = formData;
                    userData.id = parseInt(response.data.id);
                    const userRoles = await getUserRoleRequest(userData.id);
                    login(userData);
                    //Show some message before moving to other page
                    console.log(userRoles);
                    switch (userRoles) {
                        case Roles.Admin:
                        case Roles.Assistance:
                        case Roles.Trainer:
                            navigate("/");
                            break;
                        default:
                            navigate("/frontoffice/home");
                            break;
                    }
                }
            } catch (error) {
                alert(error);
            }
        }
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
                        name="nom"
                        type="text"
                        placeholder="Name"
                        value={formData?.nom}
                        onChange={handleFormChange}
                        required={option === 2}
                        disabled={false}
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
                        disabled={false}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="E-mail"
                        value={formData?.email}
                        onChange={handleFormChange}
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
                        value={formData?.phone}
                        placeholder="Phone"
                        onChange={handleFormChange}
                        required={option === 2}
                        disabled={false}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        value={formData?.password}
                        placeholder="Password"
                        onChange={handleFormChange}
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
                        value={repeatPassword}
                        onChange={(e) => setRepeatPassword(e.target.value)}
                        placeholder="Repeat password"
                        required={option === 2}
                        disabled={false}
                        autoComplete="new-password"
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="dob">Date of birth</label>
                    <input
                        id="dob"
                        name="dateOfBirth"
                        type="date"
                        placeholder="Date of birth"
                        value={formData?.dateOfBirth}
                        onChange={handleFormChange}
                        required={option === 2}
                        disabled={false}
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
