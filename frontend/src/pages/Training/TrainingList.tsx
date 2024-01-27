import { TrainingData , getTrainingList} from "../../hooks/TraininAPI";
import { PageResponse } from "../../Common/PageResponseAPI";
import { ChangeEvent,FormEvent ,useState, useEffect , useRef } from "react";
import { useParams,useNavigate } from 'react-router-dom';


const TrainingList: React.FC = () => {


    const navigate = useNavigate();
    const [pageResponse, setPageResponse] = useState<PageResponse>();
    const [page, setPage] = useState<number>(0);
    const maxRecords = 10;
    const isDataFetched = useRef(false);

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

    return (
        <div>
            <div className="section-header">
                <h1>Trainings List</h1>
                <button className="btn btn-primary" onClick={() => navigate('/trainingdetail')}>Add Training</button>
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
                                    <button className="btn btn-danger" onClick={() => navigate('/trainingdetail/'+training.id)}>Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );


}

export default TrainingList;


