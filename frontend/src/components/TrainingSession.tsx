import { ChangeEvent, FormEvent, useState , useRef , useEffect} from "react";
import { TrainingSessionData } from "../hooks/TrainingSessionsAPI"
import { TrainerData , getAllTrainers} from "../hooks/TrainerAPI";
import { TrainingData , getTrainingById} from "../hooks/TraininAPI";
import { DropdownData } from "../Common/CommonInterfaces";
import React from "react";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

const TrainingSession = (

    // trainingSessionId : number | undefined,
    // start : Date | undefined,
    // end : Date | undefined,
    // startTime : string | undefined,
    // endTime : string | undefined,

    {trainingId ,trainingSessionId, start, end, startTime, endTime , onSaveClicked} : {trainingId:number, trainingSessionId : number, start : Date | undefined, end : Date | undefined, startTime : string | undefined, endTime : string | undefined , onSaveClicked : any}

) => {

    const InitialFormData: TrainingSessionData = {
        id: 0 ,
        name: '',
        description: '',
        trainingSessionDate: '',
        StartTime : '',
        EndTime : '',
        IsAllDay : false,
        trainingId: 0,
        trainerId: 0
    };


    const [formData, setFormData] = useState<TrainingSessionData>(InitialFormData);
    const [trainers, setTrainers] = useState<DropdownData[]>([]);
    const [trainings, setTrainings] = useState<DropdownData[]>([]);
    const [open, setOpen] = useState(false);
    const isDataFetched = useRef(false);

    const fetchTrainings = async () => {
        try {
            const trainingsData = await getTrainingById(trainingId);
            setTrainings(trainingsData.data.map((training: TrainingData) => ({
                value: training.id,
                name: training.title,
            })));
        } catch (error) {
            console.log(error);
        }
    }

    const fetchTrainers = async () => {
        try {
            const trainersData = await getAllTrainers();
            setTrainers(trainersData.data.map((trainer: TrainerData) => ({
                value: trainer.id,
                name: trainer.nom,
            })));
        } catch (error) {
            console.log(error);
        }
    }

    const TogglePopup = () => {
        setOpen(!open);
    }


    // Handle form field changes
    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

            setFormData((prevData) => ({
                ...prevData,
                [name]: value,
            }));
    }

    const handleAllDayChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        if(checked){
            formData.StartTime = '06:00';
            formData.EndTime = '08:00';
        }
        setFormData({
            ...formData,
            [name]: checked,
        });    
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior
        onSaveClicked();
    }

    const handleTrainerDropdownChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }
        

    useEffect(() => {

        if (!isDataFetched.current) {

            fetchTrainings();
            fetchTrainers();
            isDataFetched.current = true;
        }
    }, []);


    
    return (
        <form className="" onSubmit={handleSubmit}>
            <div className="section-header">
                <h1>{trainingSessionId !== 0 ?"Training Session Details " : "New Training Session"}</h1>
                <button
                    className="btn btn-primary"
                    type="submit">Save</button>
            </div>
            <div className="two-columns">
                <div className="form-group">
                    <label htmlFor="name">Name</label>
                    <input
                        id="name"
                        name="name"
                        type="text"
                        placeholder="Name"
                        value={formData?.name}
                        onChange={handleFormChange}
                        required={true}
                        disabled={false}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <input
                        id="description"
                        name="description"
                        type="text"
                        placeholder="description"
                        value={formData?.description}
                        onChange={handleFormChange}
                        required={true}
                        disabled={false}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="IsAllDay">Is All Day</label>
                    <input
                        id="IsAllDay"
                        name="IsAllDay"
                        type="checkbox"
                        checked={formData?.IsAllDay}
                        placeholder="Is All Day"
                        onChange={handleAllDayChange}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="StartTime">Start Time</label>
                    <input
                        id="StartTime"
                        name="StartTime"
                        type="time"
                        placeholder="Start Time"
                        value={formData?.StartTime}
                        onChange={handleFormChange}
                        required={!formData?.IsAllDay}
                        disabled={formData?.IsAllDay}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="EndTime">End Time</label>
                    <input
                        id="EndTime"
                        name="EndTime"
                        type="time"
                        value={formData.EndTime}
                        placeholder="End Time"
                        onChange={handleFormChange}
                        required={!formData?.IsAllDay}
                        disabled={formData?.IsAllDay}
                    />
                </div>
                <div>
                    <label htmlFor="trainerId">Trainer</label>
                    <select 
                        id="trainerId"
                        name="trainerId"
                        value={formData.trainerId+'' || ''} 
                        onChange={handleTrainerDropdownChange} 
                        required>
                        <option value="0">  </option>
                        {trainers.map((dropdownItem) => (
                                <option key={dropdownItem.value} value={dropdownItem.value}>
                                    {dropdownItem.name}
                                </option>
                        ))}
                    </select>
                    </div>
                </div> 
                <div>
            </div>
         </form>
    );
}

export default TrainingSession;