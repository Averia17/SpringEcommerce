import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";
import AdminServices from "../services/admin.service";
import axios from "axios";


//  /assets/product_images/1.jpg
const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};


const vusername = value => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 3 and 20 characters.
            </div>
        );
    }
};

const vimage = value => {
    if (!value.includes('/assets/product_images/')) {
        return (
            <div className="alert alert-danger" role="alert">
                The image should contains path /assets/product_images/"file".
            </div>
        );
    }
};

export default class Admin extends Component {
    constructor(props) {
        super(props);
        this.handleCreateProduct = this.handleCreateProduct.bind(this);
        this.handleCreateProductVariant = this.handleCreateProductVariant.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.onChangeTitle = this.onChangeTitle.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangeImage = this.onChangeImage.bind(this);
        this.onChangeSize = this.onChangeSize.bind(this);

        this.state = {
            title: "",
            description: "",
            price: "",
            image: "",
            size: "",
            successful: false,
            message: "",
            products: []
        };
    }
    componentDidMount() {
        axios.get(`http://127.0.0.1:8080/api/product/`)
            .then(res => {
                const products = res.data;
                this.setState({ products });
            })
    }
    onChangeTitle(e) {
        this.setState({
            title: e.target.value
        });
    }
    onChangeSize(e) {
        this.setState({
            size: e.target.value
        });
    }
    onChangeDescription(e) {
        this.setState({
            description: e.target.value
        });
    }

    onChangePrice(e) {
        this.setState({
            price: e.target.value
        });
    }

    onChangeImage(e) {
        this.setState({
            image: e.target.value
        });
    }

    handleCreateProduct(e) {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            AdminServices.createProduct(
                this.state.title,
                this.state.description,
                this.state.price,
                this.state.image
            ).then(
                response => {
                    this.setState({
                        message: response.data.message,
                        successful: true
                    });
                },
                error => {
                    let resMessage;
                    if (error.response.status === 400){
                        resMessage = "Не получилось создать продукт";
                    }
                    else {
                        resMessage =
                            (error.response &&
                                error.response.data &&
                                error.response.data.message) ||
                            error.message ||
                            error.toString();
                    }
                    this.setState({
                        successful: false,
                        message: resMessage
                    });
                }
            ).then(window.location.reload());
        }
    }
    handleCreateProductVariant(e, id) {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        AdminServices.createProductVariant(
            this.state.size,
            id
        ).then(
            response => {
                this.setState({
                    message: response.data.message,
                    successful: true
                });
            },
            error => {
                let resMessage;
                if (error.response.status === 400){
                    resMessage = "Не получилось создать размер для продукта";
                }
                else {
                    resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                }
                this.setState({
                    successful: false,
                    message: resMessage
                });
            }
        ).then(window.location.reload());

    }

    handleDelete(productVariantId, e) {

        axios.delete(`http://127.0.0.1:8080/api/admin/product_variant/${productVariantId}/`, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }

        }).then(window.location.reload());

    }

    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <div>Форма добавления нового продукта</div>
                    <Form
                        onSubmit={this.handleCreateProduct}
                        ref={c => {
                            this.form = c;
                        }}
                    >
                        {!this.state.successful && (
                            <div>
                                <div className="form-group">
                                    <label htmlFor="title">Title</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="title"
                                        value={this.state.title}
                                        onChange={this.onChangeTitle}
                                        validations={[required, vusername]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="price">Price</label>
                                    <Input
                                        type="number"
                                        className="form-control"
                                        name="price"
                                        value={this.state.price}
                                        onChange={this.onChangePrice}
                                        validations={[required, vusername]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="description">Description</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="description"
                                        value={this.state.description}
                                        onChange={this.onChangeDescription}
                                        validations={[required, vusername]}
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="image">Image</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="image"
                                        value={this.state.image}
                                        onChange={this.onChangeImage}
                                        validations={[required, vimage]}
                                    />
                                </div>
                                <div className="form-group">
                                    <button className="btn btn-primary btn-block">Создать</button>
                                </div>
                            </div>
                        )}

                        {this.state.message && (
                            <div className="form-group">
                                <div
                                    className={
                                        this.state.successful
                                            ? "alert alert-success"
                                            : "alert alert-danger"
                                    }
                                    role="alert"
                                >
                                    {this.state.message}
                                </div>
                            </div>
                        )}
                        <CheckButton
                            style={{ display: "none" }}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                    </Form>
                </div>
                <div>

                    <div className="admin-list-products-wrapper">
                        {this.state.products.map(product =>
                            <div>
                                <div className="admin-list-products">
                                    <div>
                                        {product.title}
                                    </div>
                                    <div>
                                        {product.price}
                                    </div>
                                    <div>
                                        {product.description}
                                    </div>
                                </div>
                                <div>

                                    <div >
                                        <Form
                                            onSubmit={(e) => this.handleCreateProductVariant(e, product.id)}
                                            ref={c => {
                                                this.form = c;
                                            }}
                                        >
                                            {!this.state.successful && (
                                                <div className="admin-create-product-variant">
                                                    <div className="admin-create-product-variant-label">
                                                        <label htmlFor="size"  className="admin-create-product-variant-label">Size</label>
                                                        <Input
                                                            type="text"
                                                            className="add-size-control"
                                                            name="size"
                                                            value={this.state.size}
                                                            onChange={this.onChangeSize}
                                                            validations={[required]}
                                                        />
                                                    </div>
                                                    <div className="form-group">
                                                        <button className="btn btn-primary btn-block">Создать</button>
                                                    </div>
                                                </div>
                                            )}

                                        </Form>
                                    </div>
                                </div>
                                <div>
                                    {product.product_variants?.map(productVariant =>
                                        <div className="admin-list-product-variants">
                                            <div className="admin-list-product-variants-size">
                                                {productVariant.size}
                                            </div>
                                            <div className="product-variants-delete-button-wrapper">
                                                <button type="button" onClick={
                                                    (e) =>
                                                        this.handleDelete(productVariant.id, e)
                                                }> Удалить</button>
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>

                        )}
                    </div>
                </div>
            </div>
        );
    }
}