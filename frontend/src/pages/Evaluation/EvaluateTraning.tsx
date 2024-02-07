import { useParams , useNavigate} from "react-router-dom";
import {  useState , ChangeEvent , FormEvent} from "react";
import { EvaluationData , SaveEvaluation } from "../../hooks/EvaluationAPI";
import { useAuth } from "../../context/UserContext";

const EvaluateTraining = () => {


    const { id } = useParams<{ id: string }>();
    const [TrainingEvaluation, setTrainingEvaluation] = useState<number>(parseInt(id || '0'));
    
    const { user } = useAuth();
    const navigate = useNavigate();

    const initialFormData: EvaluationData = {

        id: 0,
        pedagogicalQuality: "",
        pace: "",
        courseSupport: "",
        subjectMastery: "",
        code: "",
        trainerId: 0,
        individualId: 0
    };
   
    const [formData, setFormData] = useState<EvaluationData>(initialFormData);




    // Handle form field changes
    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior
        formData.trainerId = TrainingEvaluation;
        formData.individualId = user?.id || 0;
        try {
            await SaveEvaluation(formData);
            alert("Evaluation saved successfully!");
            navigate("/frontoffice/home");
        }
        catch (error) {
            alert("Failed to save evaluation. Please try again.");
        }
    }

    return ( 
            <div className="login">
                <div className="login-card">
                <ul className="options">
                    <li className="active" >
                        Evaluate Training
                    </li>
                </ul>
                <form className="login-form" onSubmit={handleSubmit}>

                    <div className="login-form-fields ">
                            <div className="form-group">
                            <label htmlFor="code">Code</label>
                            <input
                                id="code"
                                name="code"
                                type="number"
                                value={formData?.code}
                                placeholder="code"
                                onChange={handleFormChange}
                                required={true}
                                />
                        </div>
                        <div className="form-group sign-up-input">
                            <label htmlFor="name">Pedagogical Quality</label>
                            <input
                                id="pedagogicalQuality"
                                name="pedagogicalQuality"
                                type="text"
                                placeholder="pedagogical quality"
                                value={formData?.pedagogicalQuality}
                                onChange={handleFormChange}
                                required={true}
                                disabled={false}
                            />
                        </div>
                        <div className="form-group sign-up-input">
                            <label htmlFor="pace">Pace</label>
                            <input
                                id="pace"
                                name="pace"
                                type="text"
                                placeholder="pace"
                                value={formData?.pace}
                                onChange={handleFormChange}
                                required={true}
                                disabled={false}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="courseSupport">Course Support</label>
                            <input
                                id="courseSupport"
                                name="courseSupport"
                                type="text"
                                placeholder="course support"
                                value={formData?.courseSupport}
                                onChange={handleFormChange}
                                required={true}
                            />
                        </div>
                        <div className="form-group sign-up-input">
                            <label htmlFor="subjectMastery">Subject Mastery</label>
                            <input
                                id="subjectMastery"
                                name="subjectMastery"
                                type="text"
                                value={formData?.subjectMastery}
                                placeholder="subject mastery"
                                onChange={handleFormChange}
                                required={true}
                                disabled={false}
                            />
                        </div>
                    </div>
                    <button className="btn btn-primary" type="submit">
                        Submit
                    </button>
                </form>
            </div>
        </div>
    );
};

export default EvaluateTraining;
