import { useNavigate } from "react-router-dom";

function IndividualHome() {

    const handleBecomeTrainerClick = () => { 
        console.log('become clicked :)')
    }
    
    return (
        <div>
            <button className="btn btn-primary" type="button" onClick={handleBecomeTrainerClick}>Become a trainer</button>    
        </div>
    );
}

export default IndividualHome;
