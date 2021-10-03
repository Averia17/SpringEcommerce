import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom'

class NavBar extends Component {
    render() {
        return (
            <div className="navbar">
                <div className="navbar-wrapper">
                    <ul className="navbar-links">
                        {/*<li>*/}
                        {/*    <Link to={{ pathname: `/films/`}}>*/}
                        {/*        <div className="logo">*/}
                        {/*            <img src="/assets/logo.svg"></img>*/}
                        {/*        </div>*/}
                        {/*    </Link>*/}
                        {/*</li>*/}
                        <li className="navbar-link-wrapper">
                            <Link className="link" to={{ pathname: `/products/`}}>Все товары</Link>
                        </li>
                        <li className="navbar-link-wrapper">
                            <Link className="link" to={{ pathname: `/products/`}}>Мужское</Link>
                        </li>
                        <li className="navbar-link-wrapper">
                            <Link className="link" to={{ pathname: `/products/`}}>Женское</Link>
                        </li>
                    </ul>

                    <div className="search-box">
                            <input className="search-txt" type="search" name="" placeholder="Нажмите для поиска"/>
                            {/*<a className="search-btn" href="#"></a>*/}
                    </div>
                </div>
                    {/*<li className="search-box"></li>*/}
            </div>
        )
    }
}

export default NavBar;
