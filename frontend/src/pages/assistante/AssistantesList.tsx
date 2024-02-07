import { AssistantData , GetAllAssistants , DeleteAssistant} from "../../hooks/AssistanteAPI";
import { useState , useEffect , useRef } from "react";
import { useNavigate} from 'react-router-dom';
import ConfirmationPopup from "../../components/Confirmation";



const AssistantsList = () => {

    const navigate = useNavigate();

    const [Assistant, setAssstant] = useState<AssistantData[]>([]);
    const [selectedAssistant, setSelectedAssistant] = useState<number>(0);
    const [showConfirmation, setShowConfirmation] = useState<boolean>(false);

    const isDataFetched = useRef(false);


    const fetchAssistant = async () => {
        try {
            const response = await GetAllAssistants();
            console.log(response.data);
            setAssstant(response.data);
        } catch (error) {
            alert("Error fetching Assistants");1
        }
    };


    useEffect(() => {
        if (!isDataFetched.current) {
            // Fetch the user data from the API
            fetchAssistant();
            isDataFetched.current = true;
        }
    }, []);

    const handleDeleteAssistant = (id: number) => {
        setSelectedAssistant(id);
        setShowConfirmation(true);
    }

    const deleteAssistant= async () => {
        try {
            await DeleteAssistant(selectedAssistant);
            fetchAssistant();
            alert("Assistant deleted successfully");
        } catch (error) {
            alert("Error deleting Assistant");
        }
        setShowConfirmation(false);
    }

    return (
        <div>
            <div className="section-header">
                <h1>Assistant List</h1>
                <button className="btn btn-primary" onClick={() => navigate('/assistantform')}>Add Assistant</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th> </th>
                    </tr>
                </thead>
                <tbody>
                    {Assistant.map((assistant , index) => (
                        <tr key={assistant.id} className={index % 2 === 0 ? '' : 'active-row'}>
                            <td>{assistant.nom}</td>
                            <td>{assistant.email}</td>
                            <td>{assistant.phone}</td>
                            <td>
                                <div className="items-to-right">
                                    <button className="btn" onClick={() => navigate('/assistantform/'+assistant.id)}>Details</button>
                                    <button className="btn btn-danger" onClick={() => handleDeleteAssistant(assistant.id)}>Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {showConfirmation && (
                 <ConfirmationPopup
                    IsOpen={showConfirmation}
                    content="Are you sure you want to delete this assistant"
                    onConfirm={() => {
                        deleteAssistant();
                    }}
                    cancelOnClick={() => setShowConfirmation(false)}
               />
            )}
        </div>
    );

}

export default AssistantsList;
