import { IndividualData , getIndividualsbyTraning } from "../../hooks/IndividualAPI";
import { TrainingData , getTrainingById , AssignTrainerToTraining} from "../../hooks/TraininAPI";
import { TrainerData , getAllTrainers} from "../../hooks/TrainerAPI";
import { useState , useEffect , useRef } from "react";
import { useParams,useNavigate} from 'react-router-dom';
import { alignProperty } from "@mui/material/styles/cssUtils";


const IndivualdTraningList = () => {

    const { id } = useParams<{ id?: string}>();
    console.log(id);
    const navigate = useNavigate();

    interface DropdownData {
        id: number;
        name: string;
    }


    const [traningId , setTraningId] = useState<number>(parseInt(id || '0'));
    const [individuals , setIndividuals] = useState<IndividualData[]>([]);
    const [trainerId , setTrainerId] = useState<number>(0);
    const [trainersList , setTrainersList] = useState<DropdownData[]>([]);
    const [trainingData , settrainingData] = useState<TrainingData>();

    const isDataFetched = useRef(false);
    trainingData
    const fetchIndividuals = async () => {
        try {
            const response = await getIndividualsbyTraning(traningId);
            console.log(response.data);
            setIndividuals(response);
        } catch (error) {
            alert("Error fetching Indivuals List");
        }
    }

    const fetchTraining = async () => {
        if(traningId !== 0){
            const response = await getTrainingById(traningId);                        
            settrainingData(response);
            setTrainerId(response.trainerId || 0);
        }
    }

    const fetchTrainers = async () => {
        const Trainersresponse = await getAllTrainers();
        const dropdownDataTrainers = Trainersresponse.data.map((trainer: TrainerData) => ({
            id: trainer.id,
            name: trainer.nom || '', 
        }));
        setTrainersList(dropdownDataTrainers);
    }



    useEffect(() => {
        if (!isDataFetched.current) {
            fetchTrainers();
            fetchIndividuals();
            fetchTraining();
            isDataFetched.current = true;
        }
    }, []);

    const handleTrainerDropdownChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const value = event.target.value;
        setTrainerId(parseInt(value));
    }   

    const handleSaveTrainerOnClick = () => {

        if(trainerId === 0){
            alert("Please select a trainer");
            return;
        }
        try{
            AssignTrainerToTraining(traningId , trainerId);
            alert("Trainer assigned to training");
        }
        catch(error){
            alert("Error assigning trainer to training");
        }        
        
    }

    return (
        <div>
            <div className="section-header">
                <h1>Individuals List</h1>
                <div>
                    <button className="btn" onClick={() => window.history.back()}>Back</button>
                    {trainingData?.minSeats && trainingData.minSeats <= individuals.length && 

                        <button className="btn btn-primary" onClick={handleSaveTrainerOnClick }>Save</button>
                    }
                </div>
            </div>
            {trainingData?.minSeats && trainingData.minSeats <= individuals.length && 
                <div className="form-group">
                    <label htmlFor="trainerId">Trainer</label>
                    <select 
                        id="trainerId"
                        name="trainerId"
                        value={trainerId} 
                        onChange={handleTrainerDropdownChange} 
                        required = {true}>
                        <option value="0">  </option>
                        {trainersList.map((dropdownItem) => (
                                <option key={dropdownItem.id} value={dropdownItem.id}>
                                    {dropdownItem.name}
                                </option>
                        ))}
                    </select>
                </div>
            }
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>SurName</th>
                        <th>Email</th>
                        <th>Phone</th>
                    </tr>
                </thead>
                <tbody>
                    {individuals.map((Individual , index) => (
                        <tr key={Individual.nom} className={index % 2 === 0 ? '' : 'active-row'}>
                            <td>{Individual.nom}</td>
                            <td>{Individual.email}</td>
                            <td>{Individual.phone}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

}

export default IndivualdTraningList;
