import { TrainerData , ApplyToTrainer } from "../../hooks/TrainerAPI";
import { ChangeEvent,FormEvent ,useState } from "react";


const SignupTrainer = ({onOptionChange} : {onOptionChange: (newOption: number) => void}) => {


    const initialFormData: TrainerData = {
        id: 0,
        nom: '',
        surname: '',
        phone: '',
        email: '',
        password: '',
        keywords: '',
        description:''
    };

    const [formData, setFormData] = useState<TrainerData>(initialFormData)

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
        try{
            const response = await ApplyToTrainer(formData);
            if (response.status === 201 || response.status === 200) {
                // Add a message bofore doing something else
                 alert("Your Request is Created Successfully , you will receive an email of the response"); 
                // Clear the form data after successful registration
                setFormData(initialFormData);
                onOptionChange(1);
            } 
            else { 
                alert("Failed to create your request. Please try again."); 
            }
        }
        catch (error) {
            alert(error);
        }
    };

    return (
        <form className="login-form" onSubmit={handleSubmit}>
            <div
                className={
                    "login-form-fields " +
                    ("sign-up")
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
                        required={true}
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
                        required={true}
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
                        required={true}
                        disabled={false}
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="keywords">Keywords</label>
                    <input
                        id="keywords"
                        name="keywords"
                        type="text"
                        value={formData?.keywords}
                        placeholder="keywords"
                        onChange={handleFormChange}
                        required={true}
                        disabled={false}
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="description">Description</label>
                    <input
                        id="description"
                        name="description"
                        type="text"
                        value={formData?.description}
                        placeholder="keywords"
                        onChange={handleFormChange}
                        required={true}
                        disabled={false}
                    />
                </div>
            </div>  
            <button className="btn btn-primary" type="submit">
                Sign up
            </button>
        </form>
    );

}

export default SignupTrainer;
