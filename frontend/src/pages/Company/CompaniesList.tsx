import { CompanyData , getAllCompanies , DeleteCompany} from "../../hooks/CompanyAPI";
import { useState,useEffect , useRef } from "react";
import { useParams,useNavigate} from 'react-router-dom';
import ConfirmationPopup from "../../components/Confirmation";



const CompaniesList = () => {

    const navigate = useNavigate();

    const [companies, setCompanies] = useState<CompanyData[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<number>(0);
    const [showConfirmation, setShowConfirmation] = useState<boolean>(false);

    const isDataFetched = useRef(false);

    const fetchCompanies = async () => {
        try {
            const response = await getAllCompanies();
            console.log(response.data);
            setCompanies(response.data);
        } catch (error) {
            alert("Error fetching companies");1
        }
    };

    useEffect(() => {
        if (!isDataFetched.current) {
            // Fetch the user data from the API
            fetchCompanies();
            isDataFetched.current = true;
        }
    }, []);

    const handleDeleteCompany = (id: number) => {
        setSelectedCompany(id);
        setShowConfirmation(true);
    }

    const deleteCompany = async () => {

        try {
            await DeleteCompany(selectedCompany);
            fetchCompanies();
            alert("Company deleted successfully");
        } catch (error) {
            alert("Error deleting Company");
        }
        setShowConfirmation(false);
    }
    

    return (
        <div>
            <div className="section-header">
                <h1>Companies List</h1>
                <button className="btn btn-primary" onClick={() => navigate('/companydetail')}>Add Company</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Adress</th>
                        <th> </th>
                    </tr>
                </thead>
                <tbody>
                    {companies.map((company , index) => (
                        <tr key={company.id} className={index % 2 === 0 ? '' : 'active-row'}>
                            <td>{company.name}</td>
                            <td>{company.email}</td>
                            <td>{company.address}</td>
                            <td>
                                <div className="items-to-right">
                                    <button className="btn" onClick={() => navigate('/companydetail/'+company.id)}>Details</button>
                                    <button className="btn btn-danger" onClick={() => handleDeleteCompany(company.id)}>Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {showConfirmation && (
                 <ConfirmationPopup
                    IsOpen={showConfirmation}
                    content="Are you sure you want to delete this company"
                    onConfirm={() => {
                        deleteCompany();
                    }}
                    cancelOnClick={() => setShowConfirmation(false)}
               />
            )}            
        </div>
    );
};

export default CompaniesList;


