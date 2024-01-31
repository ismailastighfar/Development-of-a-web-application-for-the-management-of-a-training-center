import { AssistantData , GetAllAssistants } from "../../hooks/AssistanteAPI";
import { useState , useEffect , useRef } from "react";
import { useNavigate} from 'react-router-dom';


const AssistantsList = () => {

    const navigate = useNavigate();

    const [Assistant, setAssstant] = useState<AssistantData[]>([]);

    const isDataFetched = useRef(false);

    useEffect(() => {
        if (!isDataFetched.current) {
            // Fetch the user data from the API
            const fetchTrainer = async () => {
                try {
                    const response = await GetAllAssistants();
                    console.log(response.data);
                    setAssstant(response.data);
                } catch (error) {
                    alert("Error fetching trainers");1
                }
            };
            fetchTrainer();
            isDataFetched.current = true;
        }
    }, []);

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
                                    <button className="btn btn-danger" onClick={() => navigate('/assistantform/'+assistant.id)}>Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

}

export default AssistantsList;
