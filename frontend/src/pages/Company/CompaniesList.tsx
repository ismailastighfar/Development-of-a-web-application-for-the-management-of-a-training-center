import { CompanyData  } from "../../hooks/CompanyAPI";
import { ChangeEvent,FormEvent ,useState,useEffect } from "react";
import { useParams,useNavigate} from 'react-router-dom';


const CompaniesList = () => {
    return (
        <div>
            <h1>Companies List</h1>
            <table className="styled-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Points</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Dom</td>
                        <td>6000</td>
                    </tr>
                    <tr className="active-row">
                        <td>Melissa</td>
                        <td>5150</td>
                    </tr>
                    <tr>
                        <td>Dom</td>
                        <td>6000</td>
                    </tr>
                    <tr className="active-row">
                        <td>Melissa</td>
                        <td>5150</td>
                    </tr>
                    <tr>
                        <td>Dom</td>
                        <td>6000</td>
                    </tr>
                    <tr className="active-row">
                        <td>Melissa</td>
                        <td>5150</td>
                    </tr>
                </tbody>
            </table>

        </div>
    );
};

export default CompaniesList;


