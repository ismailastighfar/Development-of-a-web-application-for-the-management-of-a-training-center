import { TrainingData , CreateTraining , UpdateTraining , getTrainingById , AssignTrainingToCompany , getCategories} from "../../hooks/TraininAPI";
import { CompanyData , getAllCompanies } from "../../hooks/CompanyAPI";
import { TrainerData , getAllTrainers} from "../../hooks/TrainerAPI";
import { ChangeEvent,FormEvent ,useState,useEffect , useRef } from "react";
import { useParams,useNavigate } from 'react-router-dom';


const TrainingForm: React.FC = () => {

    const navigate = useNavigate();

    const { id } = useParams<{ id?: string }>();

    interface DropdownData {
        id: number;
        name: string;
    }

    const initialTrainingData: TrainingData = {
        id: 0, 
        title: '',
        city: '',
        hours: null,
        cost: null,
        availableSeats: null,
        minSeats: null,
        endEnrollDate: null,
        maxSessions: null,
        objectives: '',
        detailed_program: '',
        category: '',
        forCompany: false,
        trainerId: 0,
        companyId: 0,
      };
    

    const [trainingId, setTrainingId] = useState<number>(parseInt(id || '0'));
    const [CompaniesList , setCompaniesList] = useState<DropdownData[]>([]);
    const [TrainersList , setTrainersList] = useState<DropdownData[]>([]);
    const [CategoriesList , setCategoriesList] = useState<DropdownData[]>([]);

    const [formData, setFormData] = useState<TrainingData>(initialTrainingData)


    const isDataFetched = useRef(false);
    useEffect(() => {
        if (!isDataFetched.current) {
             // Fetch the user data from the API
             const fetchData = async () => {
                try {
                    // const response = await getCompanyById(trainingId);
                    // setFormData(response);

                    if(trainingId !== 0){
                        const response = await getTrainingById(trainingId);                        
                        setFormData(response);
                    }

                    const Companiesresponse = await getAllCompanies();
                    const dropdownData = Companiesresponse.data.map((company: CompanyData) => ({
                        id: company.id,
                        name: company.name || '', 
                    }));

                    setCompaniesList(dropdownData);

                    const Trainersresponse = await getAllTrainers();
                    const dropdownDataTrainers = Trainersresponse.data.map((trainer: TrainerData) => ({
                        id: trainer.id,
                        name: trainer.nom || '', 
                    }));
                    setTrainersList(dropdownDataTrainers);

                    const Categoriesresponce = await getCategories();
                    console.log("categories : ",Categoriesresponce)
                    const dropdownDataCategories = Categoriesresponce.map((category: any) => ({
                        id: 1,
                        name: category || '', 
                    }));

                    setCategoriesList(dropdownDataCategories);


                } catch (error) {
                    // setCompanyId(0);
                }
            };
            fetchData();
          }
            isDataFetched.current = true;
        }
, []);


// Handle form field changes
const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
};

const handleCompanyDropdownChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedCompanyId = parseInt(e.target.value, 10);
    setFormData({
      ...formData,
      companyId: selectedCompanyId,
    });
  };

  const handleTrainerDropdownChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedTrainerId = parseInt(e.target.value, 10);
    setFormData({
      ...formData,
      trainerId: selectedTrainerId,
    });
  };


const handleIsForCompanyChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = event.target;
    formData.companyId = null;
    formData.trainerId = null;
    formData.availableSeats = 0;
    formData.minSeats = 0;
    setFormData({
        ...formData,
        [name]: checked,
    });    
};

const handleStartDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData({
        ...formData,
        [name]: value,
    });    
}

const hadleCategoryDropdownChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedCategory = e.target.value;
    setFormData({
      ...formData,
      category: selectedCategory,
    });
  };

const handleSubmit = async (event: FormEvent) => {
    event.preventDefault(); // Prevent the default form submission behavior
    try{
        const response =  (trainingId == 0 ? await CreateTraining(formData) : await UpdateTraining(formData));
        console.log(response);
        if (response.status === 201 || response.status === 200) {
            // Add a message bofore doing something else
            setTrainingId(response.data.id);
            if(formData.forCompany && formData.companyId !== null && formData.trainerId !== null){
                const responseAssign = await AssignTrainingToCompany(trainingId , formData.trainerId , formData.companyId);
                console.log(responseAssign);
            }
            formData.id = trainingId;
            setFormData(formData);
            alert("Training saved successfully"); 
        }else { 
            alert("Failed to save the trainer. Please try again."); 
        }
    }
    catch (error) {
        alert(error);
    }
};


    return (
        
            <form className="" onSubmit={handleSubmit}>
                <div className="section-header">
                    <h1>{trainingId !== 0 ?"Training Details " : "New Training"}</h1>
                    {trainingId !== 0 &&(
                        <div>
                            <button 
                                className="btn"
                                type="button"
                                onClick={() => {navigate(`/planification/${trainingId}`)}}> Planification</button>
                            
                        </div>
                    )}
                </div>
                <div className="two-columns">
                    <div className="form-group">
                        <label htmlFor="title">Title</label>
                        <input
                            id="title"
                            name="title"
                            type="text"
                            placeholder="Name"
                            value={formData?.title}
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="city">City</label>
                        <input
                            id="city"
                            name="city"
                            type="text"
                            placeholder="city"
                            value={formData?.city}
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="hours">Hours</label>
                        <input
                            id="hours"
                            name="hours"
                            type="number"
                            placeholder="hours"
                            value={formData?.hours===null?'':formData.hours }
                            onChange={handleFormChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="cost">cost (DH)</label>
                        <input
                            id="cost"
                            name="cost"
                            type="number"
                            value={formData.cost===null?'':formData.cost}
                            placeholder="cost"
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="endEnrollDate">End Enroll Date</label>
                        <input
                            id="endEnrollDate"
                            name="endEnrollDate"
                            type="date"
                            value={formData.endEnrollDate ? formData.endEnrollDate+'': ''}
                            placeholder="start date"
                            onChange={handleStartDateChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="maxSessions">Max Sessions</label>
                        <input
                            id="maxSessions"
                            name="maxSessions"
                            type="number"
                            value={formData?.maxSessions===null?'':formData.maxSessions}
                            placeholder="max Sessions"
                            onChange={handleFormChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="objectives">Objective</label>
                        <input
                            id="objectives"
                            name="objectives"
                            type="text"
                            value={formData?.objectives}
                            placeholder="objective"
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="detailed_program">Detailed program</label>
                        <input
                            id="detailed_program"
                            name="detailed_program"
                            type="text"
                            value={formData?.detailed_program}
                            placeholder="detailed program"
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="category">Category</label>
                        <select 
                            id="category"
                            name="category"
                            value={formData?.category} 
                            onChange={hadleCategoryDropdownChange} 
                            required>
                            <option value="0">  </option>
                            {CategoriesList.map((dropdownItem) => (
                                    <option key={dropdownItem.id} value={dropdownItem.name}>
                                        {dropdownItem.name}
                                    </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="forCompany">Is for company</label>
                        <input
                            id="forCompany"
                            name="forCompany"
                            type="checkbox"
                            checked={formData?.forCompany}
                            placeholder="is For Company"
                            onChange={handleIsForCompanyChange}
                        />
                    </div>
                    {!formData.forCompany && (
                        <>
                             <div className="form-group">
                                <label htmlFor="availableSeats">Available seats</label>
                                <input
                                    id="availableSeats"
                                    name="availableSeats"
                                    type="number"
                                    value={formData.availableSeats===null || formData.availableSeats==0?'':formData.availableSeats}
                                    placeholder="available seats"
                                    onChange={handleFormChange}
                                    required={!formData.forCompany}
                                    disabled={false}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="minSeats">Minimum seats</label>
                                <input
                                    id="minSeats"
                                    name="minSeats"
                                    type="number"
                                    value={formData.minSeats===null || formData.minSeats==0?'':formData.minSeats}
                                    placeholder="minimum seats to start"
                                    onChange={handleFormChange}
                                    required={!formData.forCompany}
                                    disabled={false}
                                />
                            </div>
                        </>
                        )}
                    {formData.forCompany && (
                        <>
                            <div className="form-group">
                                <label htmlFor="companyId">Company</label>
                                <select 
                                    id="companyId"
                                    name="companyId"
                                    value={formData?.companyId+'' || ''} 
                                    onChange={handleCompanyDropdownChange} 
                                    required = {true}>
                                    <option value="0">  </option>
                                    {CompaniesList.map((dropdownItem) => (
                                            <option key={dropdownItem.id} value={dropdownItem.id}>
                                                {dropdownItem.name}
                                            </option>
                                    ))}
                                </select>
                            </div>
                            <div>
                                <label htmlFor="trainerId">Trainer</label>
                                <select 
                                    id="trainerId"
                                    name="trainerId"
                                    value={formData.trainerId+'' || ''} 
                                    onChange={handleTrainerDropdownChange} 
                                    required = {true}>
                                    <option value="0">  </option>
                                    {TrainersList.map((dropdownItem) => (
                                            <option key={dropdownItem.id} value={dropdownItem.id}>
                                                {dropdownItem.name}
                                            </option>
                                    ))}
                                </select>
                            </div>
                        </>
                    )}
                    <div>
                        <button
                                className="btn btn-primary"
                                type="submit">Save</button>
                        <button
                                className="btn"
                                type="button"
                                onClick={() => {window.history.back()}}>Cancel</button>
                    </div>
                </div>  
            </form>
    );

}

export default TrainingForm;
