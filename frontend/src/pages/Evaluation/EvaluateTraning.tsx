import { useParams } from "react-router-dom"; 

const EvaluateTraining = () => {
    const { id } = useParams<{ id: string }>();
    return (
        <div>
            <h1>Evaluate Training</h1>
            <p>Training ID: {id}</p>
        </div>
    );
};

export default EvaluateTraining;