import { TrainerData , SaveTrainer , UpdateTrainer, getTrainerById } from "../../hooks/TrainerAPI";
import { ChangeEvent,FormEvent ,useState,useEffect } from "react";
import { useParams,useNavigate} from 'react-router-dom';

  
const TrainerForm: React.FC = () => {

    const { id } = useParams<{ id?: string }>();
    const [trainerId, setTrainerId] = useState<number>(parseInt(id || '0'));
    const navigate = useNavigate();

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

    useEffect(() => {
        if (trainerId !== 0) {
            // Fetch the user data from the API
            const fetchTrainer = async () => {
                try {
                    const response = await getTrainerById(trainerId);
                    setFormData(response);
                } catch (error) {
                    setTrainerId(0);
                }
            };
            fetchTrainer();
        }
    }, [trainerId]);
    
    // Handle form field changes
    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
       
    };

  
      const handleSubmit = async (event: FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior
        try{
            const response = (trainerId == 0 ? await SaveTrainer(formData) : await UpdateTrainer(formData));
            if (response.status === 201 || response.status === 200) {
                // Add a message bofore doing something else
                 alert("Trainer saved successfully"); 
                 window.history.back();
            } 
            else { 
                alert("Failed to save the trainer. Please try again."); 
            }
        }
        catch (error) {
            alert(error);
        }
    };

    const handleSearchonBlur = async (event: ChangeEvent<HTMLInputElement>) => {
        console.log("start Searching from enblue");
    }

    return (
        <div>
            <h1>{trainerId !== 0 ?"Trainer Details " : "New Trainer"}</h1>
            <form className="" onSubmit={handleSubmit}>
                <div className="">
                    <div className="form-group sign-up-input">
                        <label htmlFor="name">Name</label>
                        <input
                            id="name"
                            name="nom"
                            type="text"
                            placeholder="Name"
                            value={formData?.nom}
                            onChange={handleFormChange}
                            onBlur={handleSearchonBlur}
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
                            placeholder="description"
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                </div>  
                <br />
                <div>
                <   button className="btn btn-primary" type="submit">
                        Save
                    </button>
                    <button className="btn" type="button" onClick={() => window.history.back()}>
                        Cancel
                    </button>
                
                </div>
                
            </form>
        </div>
    );

}

export default TrainerForm;
