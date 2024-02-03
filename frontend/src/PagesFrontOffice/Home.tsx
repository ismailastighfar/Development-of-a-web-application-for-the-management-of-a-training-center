import { useState,useEffect , useRef , ChangeEvent} from "react";
import { useParams,useNavigate} from 'react-router-dom';
import { TrainingPage, GetTrainingList } from "../hooks/FO_TrainingList";
import { useAuth } from "../context/UserContext";
import { PageResponse } from "../Common/PageResponseAPI";
import Masonry, { ResponsiveMasonry } from "react-responsive-masonry"
import Card , {CardData} from "../components/Card";
import Popup from "../components/Popup";


const Home = () => {

    const {user} = useAuth();

    const navigate = useNavigate();
    const [trainingPage, settrainingPage] = useState<TrainingPage>({
        category: "",
        city: "",
        startDate: "",
        page: 0,
        size: 10,
        sort: ""
    });
    const [trainingPageData , setTrainingPageData] = useState<PageResponse>();
    const [showLoginPopup, setShowLoginPopup] = useState(false);
    const isDataFetched = useRef(false);

    const fetchTrainingList = async () => {
        try {
            const response = await GetTrainingList(trainingPage);
            console.log(response);
            setTrainingPageData(response);
        } catch (error) {
            alert("Error fetching training list");
        }
    }

    useEffect(() => {
        if (!isDataFetched.current) {
            fetchTrainingList();
            isDataFetched.current = true;
        }
    }, []);

    const handleEnrollToTraining = (index: number) => {
        if(!user){
           setShowLoginPopup(true);
        }
    }

    // Handle form field changes
    const handleSearchOnChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

            settrainingPage((prevData) => ({
                ...prevData,
                [name]: value,
            }));
    };

    const handleSearchOnClick = () => {

        settrainingPage((prevData) => ({
            ...prevData,
            page: 0
        }));
        fetchTrainingList();

    }

    return (
        <>
        <div className='search'>
                <div>
                    <label htmlFor='search'>Category</label>
                    <input type='text' value={trainingPage.category} name='category' placeholder='category' id='category' className='form-control' 
                           onChange={handleSearchOnChange}/>
                </div>
                <div>
                    <label htmlFor='search'>City</label>
                    <input type='text' name='city' value={trainingPage.city} placeholder='city' id='city' className='form-control'
                            onChange={handleSearchOnChange} />
                </div>
                <div>
                    <label htmlFor='search'>Start Date</label>
                    <input type='date' name='startDate' value={trainingPage.startDate} placeholder='start date' id='startDate' className='form-control' 
                            onChange={handleSearchOnChange}/>
                </div>
                <div>
                    <button className='btn' onClick={handleSearchOnClick}>Search</button>
                </div>
            </div>
            <ResponsiveMasonry columnsCountBreakPoints={{ 400: 1, 900: 2, 1350: 3 }} >
                    <Masonry gutter='40px'>
                        {trainingPageData && trainingPageData.content.map((Training, index) => {
                            const cardsData : CardData = {
                                id: Training.id,
                                title: Training.title,
                                content: Training.detailed_program,
                                const : Training.cost.toString(),
                                hours : Training.hours.toString(),
                                category : Training.category,
                                city : Training.city,
                                endEntrollData : Training.endEnrollDate,
                                SubmitOnClick : () => {handleEnrollToTraining(index)}
                            }
                            return (
                                <Card {...cardsData} key={index} />
                            )
                        }
                        )}
                    </Masonry>
                </ResponsiveMasonry>
                {showLoginPopup && 
                    <Popup 
                        Header="Login" 
                        Content="Please Loging or create an account to enroll to a training"
                        Actions={
                            <>
                                <button className="btn" onClick={() => setShowLoginPopup(false)}>Cancel</button>
                                <button className="btn btn-primary" onClick={() => navigate('/auth')}>Login</button>
                            </>
                        }
                        IsOpen={true}
                        OnClose={() => setShowLoginPopup(false)}
                    />
                }
        </>
    );
};

export default Home;
