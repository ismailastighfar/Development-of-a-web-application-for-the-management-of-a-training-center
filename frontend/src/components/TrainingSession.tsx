import { ChangeEvent, FormEvent, useState } from "react";
import { TrainingSessionData } from "../hooks/TrainingSessionsAPI"
import React from "react";

export interface TrainingSessionProps {
    trainingSessiosData : TrainingSessionData,
    onSaveClicked : any
}

export const TrainingSession = (

    // trainingSessionId : number | undefined,
    // start : Date | undefined,
    // end : Date | undefined,
    // startTime : string | undefined,
    // endTime : string | undefined,

    {trainingSessiosData , onSaveClicked} : {trainingSessiosData : TrainingSessionData , onSaveClicked : any}

) => {


    const [formData, setFormData] = useState<TrainingSessionData>(trainingSessiosData);

    // Handle form field changes
    const handleFormChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

            setFormData((prevData) => ({
                ...prevData,
                [name]: value,
            }));
    }

    const handleAllDayChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        if(checked){
            formData.StartTime = '06:00';
            formData.EndTime = '08:00';
        }
        setFormData({
            ...formData,
            [name]: checked,
        });    
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior
        onSaveClicked(formData);
    }

    const handleTrainerDropdownChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const { name, value } = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }

    
    return (
        <form className="" onSubmit={handleSubmit}>
            <div className="section-header">
                <h1>{formData.id !== 0 ?"Training Session Details " : "New Training Session"}</h1>
                <button
                    className="btn btn-primary"
                    type="submit">Save</button>
            </div>
            <div className="two-columns">
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
                    <label htmlFor="description">Description</label>
                    <input
                        id="description"
                        name="description"
                        type="text"
                        placeholder="description"
                        value={formData?.description}
                        onChange={handleFormChange}
                        required={true}
                        disabled={false}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="trainingSessionDate">training Session Date</label>
                    <input
                        id="trainingSessionDate"
                        name="trainingSessionDate"
                        type="date"
                        placeholder="Session Date"
                        value={formData.trainingSessionDate}
                        onChange={handleFormChange}
                        required={true}
                        disabled={true}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="IsAllDay">Is All Day</label>
                    <input
                        id="IsAllDay"
                        name="IsAllDay"
                        type="checkbox"
                        checked={formData?.IsAllDay}
                        placeholder="Is All Day"
                        onChange={handleAllDayChange}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="StartTime">Start Time</label>
                    <input
                        id="StartTime"
                        name="StartTime"
                        type="time"
                        placeholder="Start Time"
                        value={formData?.StartTime}
                        onChange={handleFormChange}
                        required={!formData?.IsAllDay}
                        disabled={formData?.IsAllDay}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="EndTime">End Time</label>
                    <input
                        id="EndTime"
                        name="EndTime"
                        type="time"
                        value={formData.EndTime}
                        placeholder="End Time"
                        onChange={handleFormChange}
                        required={!formData?.IsAllDay}
                        disabled={formData?.IsAllDay}
                    />
                </div>
            </div>
         </form>
    );
}
