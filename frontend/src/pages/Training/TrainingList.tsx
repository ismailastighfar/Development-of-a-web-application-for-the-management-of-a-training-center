import { getTrainingList , DeleteTraining} from "../../hooks/TraininAPI";
import { PageResponse } from "../../Common/PageResponseAPI";
import { useState, useEffect , useRef } from "react";
import { useNavigate } from 'react-router-dom';
import { useAuth , Roles} from "../../context/UserContext";
import ConfirmationPopup from "../../components/Confirmation";


const TrainingList: React.FC = () => {


    const navigate = useNavigate();
    const [pageResponse, setPageResponse] = useState<PageResponse>();
    const [page, setPage] = useState<number>(0);
    const [selectedTraining, setSelectedTraining] = useState<number>(0);
    const [showConfirmation, setShowConfirmation] = useState<boolean>(false);
    const maxRecords = 10;
    const isDataFetched = useRef(false);

    const { userHasRole } = useAuth();

     // Fetch the user data from the API
     const fetchTraining = async () => {
        try {
            const response = await getTrainingList(page,maxRecords);
            const Response:PageResponse = response.data;
            setPageResponse(Response);
        } catch (error) {
            alert("Error fetching trainings");1
        }
    };

    useEffect(() => {
        if (!isDataFetched.current) {
            fetchTraining();
            isDataFetched.current = true;
        }
    }, []);

    const handleDeleteTraining = (id: number) => {
        setSelectedTraining(id);
        setShowConfirmation(true);
    }

    const deleteTraining = async () => {
        try {
            await DeleteTraining(selectedTraining);
            alert("Training deleted successfully");
            fetchTraining();
        } catch (error) {
            alert("Error deleting training");
        }
        setShowConfirmation(false);
    }

    return (
        <div>
            <div className="section-header">
                <h1>Trainings List</h1>
                {userHasRole([Roles.Admin]) && (
                    <button className="btn btn-primary" onClick={() => navigate('/trainingdetail')}>Add Training</button>
                )}
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>City</th>
                        <th>Category</th>
                        <th>Hours</th>
                        <th>Price</th>
                        <th> </th>
                    </tr>
                </thead>
                <tbody>
                    {pageResponse?.content.map((training , index) => (
                        <tr key={training.id} className={index % 2 === 0 ? '' : 'active-row'}>
                            <td>{training.title}</td>
                            <td>{training.city}</td>
                            <td>{training.category}</td>
                            <td>{training.hours}</td>
                            <td>{training.cost} DH</td>
                            <td>
                                <div className="items-to-right">
                                    <button className="btn" onClick={() => navigate('/trainingdetail/'+training.id)}>Details</button>
                                    {userHasRole([Roles.Admin]) && (
                                    <button className="btn btn-danger" onClick={() => handleDeleteTraining(training.id)}>Delete</button>
                                    )}
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {showConfirmation && (
                 <ConfirmationPopup
                    IsOpen={showConfirmation}
                    content="Are you sure you want to delete this training"
                    onConfirm={() => {
                        deleteTraining();
                    }}
                    cancelOnClick={() => setShowConfirmation(false)}
               />
            )}
        </div>
    );


}

export default TrainingList;


