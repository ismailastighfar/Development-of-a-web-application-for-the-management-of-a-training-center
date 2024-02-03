import { useState,useEffect , useRef } from "react";
import { useNavigate} from 'react-router-dom';
import {  GetIndividualTrainings, CancelEnrollToTraining} from "../../hooks/FO_TrainingList";
import { useAuth } from "../../context/UserContext";
import Masonry, { ResponsiveMasonry } from "react-responsive-masonry"
import Card , {CardData} from "../../components/Card";
import Popup from "../../components/Popup";
import CalendarPlanification from "../../pages/Planification/CalendarPlanification";



const UserTrainingsList = () => {


    const {user} = useAuth();

    console.log(user);

    const navigate = useNavigate();

    const [userEnrolledTraining , setUserEnrolledTraining] = useState<any[]>([]);  
    const [showCalendarPopup, setShowCalendarPopup] = useState(false);
    const [selectedTrainingId, setSelectedTrainingId] = useState<number>(0);
    const isDataFetched = useRef(false);


    const fetchUserEnrolledTraining = async () => {
        if(user){
            try {
                const response = await GetIndividualTrainings(user.id);
                setUserEnrolledTraining(response);
            } catch (error) {
                alert("Error fetching training list");
            }
        }
    }


    useEffect(() => {
        if(!isDataFetched.current){
            fetchUserEnrolledTraining();
            isDataFetched.current = true;
        }
        
    }, []);

   const handleCancelEnroll = async (trainingId: number) => {
        if(user){
            try {
                await CancelEnrollToTraining(trainingId, user.id);
                fetchUserEnrolledTraining();
            } catch (error) {
                alert("Error canceling enroll");
            }
        }
    }

    const handlePlanification = (trainingId: number) => {
        setSelectedTrainingId(trainingId);
        setShowCalendarPopup(true);
    }


    return (
        <>
            <ResponsiveMasonry columnsCountBreakPoints={{ 400: 1, 900: 2, 1350: 3 }} >
                    <Masonry gutter='40px'>
                        {userEnrolledTraining && userEnrolledTraining.map((Training, index) => {
                            const cardsData : CardData = {
                                id: Training.id,
                                title: Training.title,
                                content: Training.detailed_program,
                                const : Training.cost.toString(),
                                hours : Training.hours.toString(),
                                category : Training.category,
                                city : Training.city,
                                endEntrollData : Training.endEnrollDate,
                                actions : (
                                    <>
                                        <button className='btn' onClick={() => handleCancelEnroll(Training.id)}>Cancel</button>
                                        <button className='btn btn-primary' onClick={() => handlePlanification(Training.id)}>Planification</button>
                                    </>
                                )
                            }
                            return (
                                <Card {...cardsData} key={index} />
                            )
                        }
                        )}
                    </Masonry>
                </ResponsiveMasonry>
                {showCalendarPopup && 
                    <Popup 
                        Content={<CalendarPlanification IsEditMode={false} training_Id={selectedTrainingId}/>}
                        IsOpen={true}
                        IsBigPopup={true}
                        OnClose={() => setShowCalendarPopup(false)}
                    />
                }
        </>
    );
};

export default UserTrainingsList;
