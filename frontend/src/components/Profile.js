import React, { useEffect, useState} from 'react';
import axios from 'axios';
import '../App.css';
import { Link } from 'react-router-dom'

function Profile() {
    const [profile, setProfile] = useState( [])

    useEffect( () => {
        axios({
            method: "GET",
            headers: {
                'Content-type': 'application/json',
            },
            url: 'http://127.0.0.1:8080/api/user/',
        }).then(response => {
            setProfile(response.data)
        })
    }, [])

    let handleDelete = (id, e) => {
        console.log(id);

        fetch(`http://127.0.0.1:8080/api/order/${id}/`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

    }

    return(
       <div>
           <div className="profile-username">{profile.username}</div>
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