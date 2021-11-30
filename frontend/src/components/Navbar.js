import React, {Component, useEffect, useState} from 'react';
import '../App.css';
import { Link } from 'react-router-dom'
import auth from "../services/auth.service";
import axios from "axios";
import authHeader from "../services/auth-header";


function NavBar() {

    let user = JSON.parse(auth.getCurrentUser());

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
                        <Link className="link" to={{ pathname: `/products/man/`}}>Мужское</Link>
                    </li>
                    <li className="navbar-link-wrapper">
                        <Link className="link" to={{ pathname: `/products/woman/`}}>Женское</Link>
                    </li>
                    { user?.role === "ROLE_ADMIN" ?
                        <li className="navbar-link-wrapper">
                            <Link className="link" to={{ pathname: `/admin/`}}>Админ панель</Link>
                        </li> :
                        <div>
                        </div>

                    }
                </ul>
                <div className="link-to-cart">
                    <Link className ="link" to={{ pathname: `/cart/`}}>Корзина</Link>
                </div>
                {   user ?
                    <div className="link-to-profile">
                        <Link className="link" to={{pathname: `/profile/`}}>Профиль</Link>
                    </div> :
                    <div className="link-to-profile">
                        <Link className="link" to={{pathname: `/login/`}}>Логин</Link>
                    </div>
                }
                <div className="search-box">

                        <input className="search-txt" type="search" name="" placeholder="Нажмите для поиска"/>
                        {/*<a className="search-btn" href="#"></a>*/}
                </div>
            </div>
                {/*<li className="search-box"></li>*/}
        </div>
    )
}

export default NavBar;
