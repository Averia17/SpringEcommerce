import React, { useEffect, useState, Component} from 'react';
import axios from 'axios';
import '../App.css';
import { Link } from 'react-router-dom'
import authHeader from "../services/auth-header";
import AuthService from "../services/auth.service";


function ProductDetail({ match }) {

    const[product, setProduct] = useState( []);
    const id = match.params.id;

    const currentUser = authHeader();

    useEffect( () => {
        axios({
            method: "GET",
            url: `http://127.0.0.1:8080/api/product/${id}/`,
            headers: {
                'Content-type': 'application/json',
            },
        }).then(response => {
            setProduct(response.data)
        })
    }, [id])

    let handleSubmit = (e) => {
        e.preventDefault();
        console.log(e.target.size.value);
        let size = e.target.size.value;
        fetch(`http://127.0.0.1:8080/api/product/${id}/?size=${size}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': authHeader()
            }
        }).then(window.location.reload());

    }

    return (
        <div className="product-detail-page">
            <div className="product-detail-wrapper">
                <div className="product-detail-image">
                    <img src={product.image} alt=""/>
                </div>
                <div className="product-detail-information">
                    <form className="product-detail-information-wrapper" onSubmit={handleSubmit}>
                        <div className="product-detail-main-information">
                            <div className="product-detail-title">
                                <h2>{product.title}</h2>
                            </div>
                            <div>
                                <div className="product-detail-price">
                                    <p>{product.price} $</p>
                                </div>
                            </div>
                            <div className="product-detail-description">
                                <p>{product.description}</p>
                            </div>
                            <div className="product-sizes">
                                <div className="product-sizes-wrapper">
                                    <p className="available-sizes">Доступные размеры: </p>
                                    {product.product_variants?.map((size) => {
                                        return(
                                            <div className="product-size-item">
                                                <input required="required"
                                                       type="radio"
                                                       name="size"
                                                       value={size.size}
                                                       id={size.size}
                                                       className="radio-input"/>
                                                <label htmlFor={size.size} className="radio-label">
                                                    {size.size}
                                                </label>
                                            </div>
                                        )}
                                    )}
                                </div>
                            </div>
                        </div>
                        { currentUser ?
                            <div className="product-detail-submit">
                                <button type="submit">Добавить в корзину</button>
                            </div> :
                            <div>

                            </div>
                        }
                    </form>
                </div>
            </div>
        </div>

    );
}

export default ProductDetail;