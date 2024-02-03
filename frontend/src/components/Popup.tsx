import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import React , { ReactNode } from 'react';


interface PopupPlaceHolders {
    Header: ReactNode;
    Content: ReactNode;
    Actions: ReactNode;
    IsOpen: boolean;
    IsBigPopup?: boolean | true;
    OnClose: any;
  }

const Popup : React.FC<PopupPlaceHolders> = ({ Header , Content , Actions , IsOpen , IsBigPopup = false , OnClose}) => {

    return (
        <Dialog open={IsOpen} onClose={OnClose} fullWidth maxWidth={IsBigPopup?'xl':'sm'}  >
            <DialogTitle>{Header}</DialogTitle>
            <DialogContent className='popup-content'>
                {Content}
            </DialogContent>
            <DialogActions>
                {Actions}
            </DialogActions>
        </Dialog>
    );

}

export default Popup;