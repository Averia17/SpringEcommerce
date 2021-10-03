import React, { useEffect, useState} from 'react';
import axios from 'axios';
import '../App.css';
import { Link } from 'react-router-dom'

function MainPage() {
    const [products, setProducts] = useState( [])

    useEffect( () => {
        axios({
            method: "GET",
            mode: 'no-cors',
            headers: {
                'Content-type': 'application/json',
            },
            url: 'http://127.0.0.1:8080/api/product/',
        }).then(response => {
            setProducts(response.data)
        })
    }, [])
    console.log(products);
    return(
        <div className="main-page font-style">
            <div className="products-wrapper">
                <div className="products">
                    {products.map((product) => {
                        return(

                            <div className="products-item" key={product.id}>
                                <div className="product-image-wrapper">
                                    <img className="product-image" src={product.image} alt=""/>
                                </div>
                                <Link to={{ pathname: `/products/${product.id}/`}}>

                                    <div className="product-description">
                                        <div className="product-title">
                                            <p className="title-header">{product.title}</p>
                                        </div>
                                        <div className="product-price">
                                            <p className="title-header">{product.price}</p>
                                            <p className="title-header">$</p>
                                        </div>
                                    </div>
                                </Link>
                            </div>
                        )}
                    )}
                </div>
            </div>

        </div>
    )
}

export default MainPage;