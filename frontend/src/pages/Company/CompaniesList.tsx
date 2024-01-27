import { CompanyData , getAllCompanies } from "../../hooks/CompanyAPI";
import { useState,useEffect , useRef } from "react";
import { useParams,useNavigate} from 'react-router-dom';


const CompaniesList = () => {

    const navigate = useNavigate();

    const [companies, setCompanies] = useState<CompanyData[]>([]);

    const isDataFetched = useRef(false);

    useEffect(() => {
        if (!isDataFetched.current) {
            // Fetch the user data from the API
            const fetchTrainer = async () => {
                try {
                    const response = await getAllCompanies();
                    console.log(response.data);
                    setCompanies(response.data);
                } catch (error) {
                    alert("Error fetching companies");1
                }
            };
            fetchTrainer();
            isDataFetched.current = true;
        }
    }, []);
    

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
                                    <button className="btn btn-danger" onClick={() => navigate('/companydetail/'+company.id)}>Delete</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

        </div>
    );
};

export default CompaniesList;


