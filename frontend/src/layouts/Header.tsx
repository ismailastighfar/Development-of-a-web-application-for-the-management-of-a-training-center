import React from 'react'
import Logo from "../assets/logo.svg"
import { NavLink, Link } from 'react-router-dom'

const Header = () => {
    return (
        <header className='header'>
            <Link to='/'>
                <img src={Logo} className='header__logo' alt='logo' />
            </Link>
            <div className='header__links'>
            </div>
        </header>
    )
}

export default Header