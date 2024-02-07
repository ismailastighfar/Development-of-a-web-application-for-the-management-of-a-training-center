import { TrainerData  , getTrainersByIsAccept , AcceptTrainer , DeleteTrainer} from "../../hooks/TrainerAPI";
import { useState , useEffect , useRef } from "react";
import { useNavigate} from 'react-router-dom';
import ConfirmationPopup from "../../components/Confirmation";



const TrainersList = () => {

    const navigate = useNavigate();

    const [trainers, setTrainers] = useState<TrainerData[]>([]);
    const [trainersRequest , setTrainersRequest] = useState<TrainerData[]>([]);
    const [selectedTrainer, setSelectedTrainer] = useState<number>(0);
    const [showConfirmation, setShowConfirmation] = useState<boolean>(false);

    const isDataFetched = useRef(false);

    const fetchTrainngByStatus = async (IsActive : boolean) =>{
        try {
            const response = await getTrainersByIsAccept(IsActive);
            IsActive ? setTrainers(response.data) : setTrainersRequest(response.data);
        } catch (error) {
            alert("Error fetching trainers");1
        }
    };

    const setTrainerStatus = (trainerId : number) =>{
        try{

            AcceptTrainer(trainerId);
            fetchTrainngByStatus(true);
            fetchTrainngByStatus(false);
        }
        catch(error){
            alert(error);
        }
    }

    useEffect(() => {
        if (!isDataFetched.current) {
            // Fetch the user data from the API
            fetchTrainngByStatus(true);
            fetchTrainngByStatus(false);
            isDataFetched.current = true;
        }
    }, []);


    const handleAcceptTrainer = (trainerId : number) => {
        // Accept the trainer
        console.log(trainerId);
        setTrainerStatus(trainerId);
    }

    const handleDeleteTrainer = (id: number) => {
        setSelectedTrainer(id);
        setShowConfirmation(true);
    }

    const deleteTrainer= async () => {
        try {
            await DeleteTrainer(selectedTrainer);
            alert("Trainer deleted successfully");
            fetchTrainngByStatus(true);
            fetchTrainngByStatus(false);
        } catch (error) {
            alert("Error deleting trainer");
        }
        setShowConfirmation(false);
    }

    return (
        <div>
            <div className="section-header">
                <h1>Trainers List</h1>
                <button className="btn btn-primary" onClick={() => navigate('/trainerdetail')}>Add Trainer</button>
            </div>
            {trainersRequest.length > 0 && (   
                <>
                    <div>
                        <h3>Trainers Request List</h3>
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
                            {trainersRequest.map((trainer , index) => (
                                <tr key={trainer.id} className={index % 2 === 0 ? '' : 'active-row'}>
                                    <td>{trainer.nom}</td>
                                    <td>{trainer.email}</td>
                                    <td>{trainer.phone}</td>
                                    <td>
                                        <div className="items-to-right">
                                            <button className="btn" onClick={() => handleAcceptTrainer(trainer.id)}>Accept</button>
                                            <button className="btn" onClick={() => navigate('/trainerdetail/'+trainer.id)}>Details</button>
                                            <button className="btn btn-danger" onClick={() => handleDeleteTrainer(trainer.id)}>Delete</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    </div>
                </>
            )}
            <div>
                <h3>Trainers List</h3>
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
                                        <button className="btn btn-danger" onClick={() => handleDeleteTrainer(trainer.id)}>Delete</button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            {showConfirmation && (
                 <ConfirmationPopup
                    IsOpen={showConfirmation}
                    content="Are you sure you want to delete this trainer"
                    onConfirm={() => {
                        deleteTrainer();
                    }}
                    cancelOnClick={() => setShowConfirmation(false)}
               />
            )}
        </div>
    );

}

export default TrainersList;
