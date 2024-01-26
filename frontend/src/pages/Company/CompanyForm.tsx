import { CompanyData , getCompanyById , SaveCompany , UpdateCompany} from "../../hooks/CompanyAPI";
import { ChangeEvent,FormEvent ,useState,useEffect } from "react";
import { useParams,useNavigate} from 'react-router-dom';



  
const CompanyForm: React.FC = () => {

    const { id } = useParams<{ id?: '' }>();
    const [companyId, setCompanyId] = useState<number>(parseInt(id || '0'));
    const navigate = useNavigate();

    const initialFormData: CompanyData = {
        id: 0,
        name: '',
        address: '',
        phone: '',
        email: '',
        url: ''
    };

    const [formData, setFormData] = useState<CompanyData>(initialFormData)

    useEffect(() => {
        if (companyId !== 0) {
            // Fetch the user data from the API
            const fetchTrainer = async () => {
                try {
                    const response = await getCompanyById(companyId);
                    setFormData(response);
                } catch (error) {
                    setCompanyId(0);
                }
            };
            fetchTrainer();
        }
    }, [companyId]);
    
    

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
        try{
            const response = (companyId == 0 ? await SaveCompany(formData) : await UpdateCompany(formData));
            if (response.status === 201 || response.status === 200) {
                // Add a message bofore doing something else
                 alert("Company saved successfully"); 
            } 
            else { 
                alert("Failed to save the trainer. Please try again."); 
            }
        }
        catch (error) {
            alert(error);
        }
    };

    const cancelOnClic = () =>{
        navigate("/");
    }

    return (
        <div>
            <h1>{companyId !== 0 ?"Company Details " : "New Company"}</h1>
            <form className="" onSubmit={handleSubmit}>
                <div className="">
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
                        <label htmlFor="address">address</label>
                        <input
                            id="address"
                            name="address"
                            type="text"
                            placeholder="address"
                            value={formData?.address}
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            id="email"
                            name="email"
                            type="email"
                            placeholder="E-mail"
                            value={formData?.email}
                            onChange={handleFormChange}
                            required
                            autoComplete="email"
                        />
                    </div>
                    <div className="form-group ">
                        <label htmlFor="phone">Phone</label>
                        <input
                            id="phone"
                            name="phone"
                            type="number"
                            value={formData?.phone}
                            placeholder="Phone"
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="url">url</label>
                        <input
                            id="url"
                            name="url"
                            type="text"
                            value={formData?.url}
                            placeholder="url"
                            onChange={handleFormChange}
                            required={true}
                            disabled={false}
                        />
                    </div>
                </div>  
                <br />
                <div>
                    <button className="btn btn-primary" type="submit">
                        Save
                    </button>
                    <button className="btn" type="button" onClick={cancelOnClic}>
                        Cancel
                    </button>
                
                </div>
                
            </form>
        </div>
    );

}

export default CompanyForm;
