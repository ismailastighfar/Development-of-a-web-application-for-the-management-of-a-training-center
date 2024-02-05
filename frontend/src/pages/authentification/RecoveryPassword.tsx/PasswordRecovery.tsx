import ResetPasswordForm from './ResetPasswordForm';

const PasswordRecovery = () => {

    return (
        <div className="login">
            <div className="login-card">
                <ul className="options">
                    <li className="active" >
                        Password Recovery
                    </li>
                </ul>
                <ResetPasswordForm />
            </div>
        </div>
    );
}

export default PasswordRecovery;