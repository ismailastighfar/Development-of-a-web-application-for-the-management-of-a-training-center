import { AssistantData , GetAssistantById , CreateAssistant , UpdateAssistant} from "../../hooks/AssistanteAPI";
import { ChangeEvent, FormEvent , useState , useEffect , useRef } from "react";
import { useParams} from 'react-router-dom';


const AssistantForm: React.FC = () => {

    const { id } = useParams<{ id?: string }>();
    const [assistantId, setAssistantId] = useState<number>(parseInt(id || '0'));
    const InitialAssistantData : AssistantData = {
        id: 0,
        nom: '',
        surname: '',
        email: '',
        password: '',
        phone: '',
    }

    const [formData , setFormData] = useState<AssistantData>(InitialAssistantData);

    const fetchAssistantData = async () => {
        try {
            const response = await GetAssistantById(assistantId);
            setFormData(response.data);
        }
        catch (error) {
            setAssistantId(0);
        }
    }

    const isDataFetched = useRef(false);

    useEffect(() => {
        if (!isDataFetched.current) {
            if (assistantId !== 0)
                fetchAssistantData();
            isDataFetched.current = true;
        }
    }, []);

    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior
        try{
            const response = (assistantId == 0 ? await CreateAssistant(formData) : await UpdateAssistant(formData));
            if (response.status === 201 || response.status === 200) {
                // Add a message bofore doing something else
                 alert("Assistance saved successfully"); 
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

    return (
        <div>
        <h1>{assistantId !== 0 ?"Assistant Details " : "New Assistant"}</h1>
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
                    {assistantId == 0 &&
                        <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            value={formData?.password}
                            placeholder="Password"
                            onChange={handleFormChange}
                            required={true}
                            autoComplete="new-password"
                        />
                    </div>
                    }
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
    )

}

export default AssistantForm;