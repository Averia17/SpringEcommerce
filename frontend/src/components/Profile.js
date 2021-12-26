import React, { useEffect, useState} from 'react';
import axios from 'axios';
import '../App.css';
import { Link } from 'react-router-dom'
import AuthService from "../services/auth.service";
import authHeader from '../services/auth-header';

function Profile() {
    const [profile, setProfile] = useState( [])
    console.log(profile)
    useEffect( () => {
        axios({
            method: "GET",
            headers: {
                'Content-type': 'application/json',
                'Authorization': authHeader()
            },
            url: 'http://127.0.0.1:8080/api/user/',
        }).then(response => {
            setProfile(response.data)
        })
    }, [])
    let logout = () => {
        AuthService.logout();
        window.location.replace('http://127.0.0.1:3000');
    }

    let handleDelete = (id, e) => {
        console.log(id);

        fetch(`http://127.0.0.1:8080/api/order/${id}/`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': authHeader()
            }
        }).then(window.location.reload());

    }

    return(
       <div>
           <div className="profile-title">
               <div className="profile-username">{profile.username}</div>
               <button className="button-logout" type="button" onClick={(e) => logout()}> Выйти</button>
           </div>
           <div className="profile-title profile-username">{profile?.email}</div>

           <div className="orders-wrapper">
               <div className="order-title">
                   Мои заказы
               </div>
               <div className="order-items">
                   {profile.orders?.map((order) => {
                       return(
                           <div className="order-item" key={order.id}>
                                   <div className="order-header">
                                       <div className="order-titles">{order.id}</div>
                                       <div className="order-titles">{order?.status?.title}</div>

                                       <div>
                                           <div className="cart-product-item-delete">
                                               <button type="button" onClick={(e) => handleDelete(order.id, e)}> Удалить</button>
                                           </div>
                                       </div>
                                   </div>
                                   {order.productVariants?.map((product, i) => {
                                       return(
                                           <div className="cart-product-item-wrapper" key={i}>
                                               <div className="cart-product-item">
                                                   <div className="cart-product-item-image">
                                                       <img src={product.product.image} alt=""/>

                                                   </div>
                                                   <div className="cart-product-item-title">
                                                       {product.product.title}
                                                   </div>
                                                   <div className="cart-product-item-size">
                                                       {product.size}
                                                   </div>
                                                   <div className="cart-product-item-price">
                                                       {product.product.price} $

                                                   </div>

                                               </div>
                                           </div>

                                       )}
                                   )}

                               </div>
                           )}
                   )}
               </div>
           </div>
       </div>
    );
}

export default Profile;