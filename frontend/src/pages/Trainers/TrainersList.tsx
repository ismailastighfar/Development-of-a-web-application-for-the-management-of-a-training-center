import { TrainerData , getAllTrainers } from "../../hooks/TrainerAPI";
import { useState , useEffect , useRef } from "react";
import { useParams,useNavigate} from 'react-router-dom';


const TrainersList = () => {

    const navigate = useNavigate();

    const [trainers, setTrainers] = useState<TrainerData[]>([]);

    const isDataFetched = useRef(false);

    useEffect(() => {
        if (!isDataFetched.current) {
            // Fetch the user data from the API
            const fetchTrainer = async () => {
                try {
                    const response = await getAllTrainers();
                    console.log(response.data);
                    setTrainers(response.data);
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
                <h1>Trainers List</h1>
                <button className="btn btn-primary" onClick={() => navigate('/trainerdetail')}>Add Trainer</button>
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
                    {trainers.map((trainer , index) => (
                        <tr key={trainer.id} className={index % 2 === 0 ? '' : 'active-row'}>
                            <td>{trainer.nom}</td>
                            <td>{trainer.email}</td>
                            <td>{trainer.phone}</td>
                            <td>
                                <div className="items-to-right">
                                    <button className="btn" onClick={() => navigate('/trainerdetail/'+trainer.id)}>Details</button>
                                    <button className="btn btn-danger" onClick={() => navigate('/trainerdetail/'+trainer.id)}>Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

}

export default TrainersList;
