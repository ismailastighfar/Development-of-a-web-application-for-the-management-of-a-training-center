import { ChangeEvent,FormEvent ,useState } from "react";
import { UserData,createUser,logIn } from "../../hooks/UseAPI";
import { useNavigate } from "react-router-dom";


const Form = ({ option }: { option: number }) => {

    const navigate = useNavigate();

    const [formData, setFormData] = useState<UserData>({
        nom: '',
        surname: '',
        phone: '',
        password: '',
        email: '',
        dateOfBirth:'',
    })

    const [repeatPassword, setRepeatPassword] = useState<string>('');
    
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

        if(option === 2){
            try {
                const response = await createUser(formData);          
                if (response.status === 201 || response.status === 200) {
                  alert("User created successfully!");
                } 
                else { 
                    alert("Failed to create user. Please try again."); 
                }
              } 
            catch (error) {
                alert(error);
              }
        }
        else{
            try {
                const response = await logIn(formData);          
                if (response.status === 201 || response.status === 200) {
                    navigate("/")
                }
              } 
            catch (error) {
                alert(error);
              }
        }
    }
       


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
                        disabled={option === 1}
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
                        disabled={option === 1}
                        autoComplete="new-password"
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="dob">Date of birth</label>
                    <input
                        id="dob"
                        name="dob"
                        type="date"
                        placeholder="Date of birth"
                        value={formData?.dateOfBirth}
                        onChange={handleFormChange}
                        required={option === 2}
                        disabled={option === 1}
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
