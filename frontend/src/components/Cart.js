import React, { useEffect, useState} from 'react';
import axios from 'axios';
import '../App.css';
import {Link, withRouter} from 'react-router-dom'
import authHeader from "../services/auth-header";

function Cart(props) {
    const [cart, setCart] = useState( [])
    useEffect( () => {
        axios({
            method: "GET",
            headers: {
                'Content-type': 'application/json',
                'Authorization': authHeader()
            },
            url: 'http://127.0.0.1:8080/api/cart/',
        }).then(response => {
            setCart(response.data)
        }).catch(err => props.history.push("/login"))
    }, [])

    let handleDelete = (id, e) => {
        console.log(id);

        fetch(`http://127.0.0.1:8080/api/cart/${id}/`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': authHeader()
            }
        }).then(window.location.reload())

    }
    let handleAddToOrder = (e) => {

        fetch(`http://127.0.0.1:8080/api/cart`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': authHeader()
            }
        });
    }

    return(
        <div className="cart-page">
            <div className="cart-product-list">
               {cart.productVariants?.map((product, i) => {
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
                               <div className="cart-product-item-delete">

                                   <button type="button" onClick={(e) => handleDelete(product.id, e)}> Удалить</button>

                               </div>
                           </div>
                       </div>

                   )}
               )}
            </div>
            <div className="cart-information">
                <div>SOME INFO</div>
                <div className="cart-product-item-delete">
                    <button type="button" onClick={handleAddToOrder}>Оформить заказ</button>
                </div>
            </div>
        </div>
    );
}

export default withRouter(Cart);