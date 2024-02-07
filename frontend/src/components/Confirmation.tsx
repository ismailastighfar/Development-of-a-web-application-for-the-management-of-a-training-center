import React , { useState } from "react";
import Popup from "./Popup";


interface ConfirmationPopupProps {
    IsOpen? : boolean;
    content? : string;
    saveButtonLabel? : string;
    onConfirm? : () => void;
    cancelOnClick : () => void;
}

const ConfirmationPopup : React.FC<ConfirmationPopupProps>= ({IsOpen=false, content = "Are you sure" , saveButtonLabel="Confirm" , onConfirm , cancelOnClick}) => {

    const confirmOnclick = () => {
        if(onConfirm){
            onConfirm();
        }
    }

    return (
        <Popup 
            IsOpen={IsOpen} 
            OnClose={() => cancelOnClick()} 
            Content={content} 
            Actions={
                <div>
                    <button className="btn" onClick={() => cancelOnClick()}>Cancel</button>
                    <button className="btn btn-primary" onClick={confirmOnclick}>{saveButtonLabel}</button>
                </div>
            }
            />

    );
    }

export default ConfirmationPopup;