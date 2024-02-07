import React from 'react';
import { sendEmail , resetPassword} from '../../../hooks/PasswordRecoveryAPI';
import { useNavigate } from 'react-router-dom';


interface PasswordData {
    email : string;
    token : string;
    newPassword: string;
}


const ResetPasswordForm : React.FC<{BackOnclick? : any}> = ({BackOnclick}) => {

    const navigate = useNavigate();

    const [passwordData, setPasswordData] = React.useState<PasswordData>({
        email : '',
        token : '',
        newPassword: ''
    });

    const [PagePosition, setPagePosition] = React.useState<number>(0 );

    const handleFormChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

        setPasswordData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSendEmail = () => {
        // Send email
        if(passwordData.email === '') {
            alert('Please enter your email');
            return;
        }
        try {
            sendEmail(passwordData.email);
            setPagePosition(1);
        }
        catch(error) {
            alert("Please check your email.!!!")
        }
    }

    const ResetRequest = async (token: string, newPassword: string) => {
        try {
            const result = await resetPassword(token, newPassword);
            if (result.status === 200) {
                alert('Password reset successfully');
                navigate('/auth');
                return
            }
            alert('Password reset failed');

        }
        catch(error) {
            alert("Please check your token.!!!")
        }
    }

        

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault(); // Prevent the default form submission behavior
        // Reset password
        if(passwordData.token === '') {
            alert('Please enter your token');
            return;
        }
        if(passwordData.newPassword === '') {
            alert('Please enter your new password');
            return;
        }
        ResetRequest(passwordData.token, passwordData.newPassword);
    }

    return (
            <form className='login-form'>
                <div className="login-form-fields ">
                    {PagePosition === 0 && (
                        <>
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input
                                    id="email"
                                    name="email"
                                    type="email"
                                    placeholder="email"
                                    value={passwordData.email}
                                    onChange={handleFormChange}
                                    required={true}
                                    disabled={false}
                                />
                            </div>
                            <div className='form-actions'>
                                {BackOnclick && (<button className='btn' onClick={() => BackOnclick()}>Back</button>)}
                                {!BackOnclick && (<button className='btn' onClick={() => navigate('/auth')}>Login</button>)}
                                <button className='btn btn-primary' onClick={handleSendEmail}>Send Email</button>
                            </div>
                        </>
                    )}
                    {PagePosition === 1 && (
                        <>
                            <div className="form-group">
                                <label htmlFor="token">Token</label>
                                <input
                                    id="token"
                                    name="token"
                                    type="text"
                                    placeholder="token"
                                    value={passwordData.token}
                                    onChange={handleFormChange}
                                    required={true}
                                    disabled={false}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="newPassword">New password</label>
                                <input
                                    id="newPassword"
                                    name="newPassword"
                                    type="password"
                                    placeholder="new password"
                                    value={passwordData.newPassword}
                                    onChange={handleFormChange}
                                    required={true}
                                />
                            </div>

                            <div className='form-actions'>
                                <button className='btn' onClick={() => setPagePosition(0)}>Back</button>
                                <button className='btn btn-primary' onClick={handleSubmit}>Reset Password</button>
                            </div>
                        </>
                    )}
                </div>
        </form>
    );
}

export default ResetPasswordForm;