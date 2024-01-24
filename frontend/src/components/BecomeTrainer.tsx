import React from "react";

const BecomeTrainer = () => {
    const handleBecomeTrainerClick = () => {
        console.log("become clicked :)");
    };
    return (
        <button
            className="btn btn-primary"
            type="button"
            onClick={handleBecomeTrainerClick}
        >
            Become a trainer
        </button>
    );
};

export default BecomeTrainer;
