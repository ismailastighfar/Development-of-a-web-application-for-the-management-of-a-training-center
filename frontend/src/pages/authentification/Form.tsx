const Form = ({ option }: { option: number }) => {
    return (
        <form className="login-form" onSubmit={(evt) => evt.preventDefault()}>
            <div
                className={
                    "login-form-fields " +
                    (option === 1 ? "sign-in" : "sign-up")
                }
            >
                <div className="form-group sign-up-input">
                    <label htmlFor="name">Name</label>
                    <input
                        id="name"
                        name="name"
                        type="text"
                        placeholder="Name"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="surname">Surname</label>
                    <input
                        id="surname"
                        name="surname"
                        type="text"
                        placeholder="Surname"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="city">City</label>
                    <input
                        id="city"
                        name="city"
                        type="text"
                        placeholder="City"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="E-mail"
                        required
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="phone">Phone</label>
                    <input
                        id="phone"
                        name="phone"
                        type="number"
                        placeholder="Phone"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        placeholder="Password"
                        required
                    />
                </div>
                <div className="form-group sign-up-input">
                    <label htmlFor="repeat-password">Repeat password</label>
                    <input
                        id="repeat-password"
                        name="repeat-password"
                        type="password"
                        placeholder="Repeat password"
                        required={option === 2}
                        disabled={option === 1}
                    />
                </div>
            </div>
            <button className="btn btn-primary" type="submit">
                {option === 1 ? "Sign in" : "Sign up"}
            </button>
        </form>
    );
};

export default Form;
