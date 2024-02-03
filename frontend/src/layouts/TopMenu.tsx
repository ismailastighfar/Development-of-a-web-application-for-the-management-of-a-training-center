import React from 'react'
import logo from "../assets/Logo.png";
import { NavLink, Link } from 'react-router-dom'
import { FrontOfficeRout } from "../pages/FrontOfficeRoutes";
import {useAuth} from "../context/UserContext";


const Header = () => {
    const { user, logout } = useAuth(); // Call useAuth() once at the top level
        return (
        <header className='header'>
            <Link to='/frontoffice'>
                <img src={logo} className='header__logo' alt='logo' />
            </Link>
            <div className='header__links'>
                {FrontOfficeRout.map((route, index) => {
                        return (
                            <>
                                {route.showInNav && (
                                            <NavLink
                                                to={route.path}
                                                className={({ isActive }) =>
                                                    isActive ? "header__link active" : "header__link"
                                                }
                                                key={index}
                                            >
                                                {route.label}
                                            </NavLink>
                                        )
                                }
                                {!user ? (
                                    <NavLink
                                    to={'/auth'}
                                    className={({ isActive }) =>
                                                    isActive ? "header__link active" : "header__link"
                                                }>
                                    Login
                                </NavLink>

                                ) :
                                (
                                    <NavLink
                                        onClick={() => logout()}
                                        to={window.location.pathname}
                                        className={({ isActive }) =>
                                                        isActive ? "header__link active" : "header__link"
                                                    }>
                                    Logout

                                </NavLink>)
                                }
                            </> 
                        );
                    })}
            </div>
        </header>
    )
}

export default Header
