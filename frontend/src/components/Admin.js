import React, {Component} from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import {isEmail} from "validator";
import AdminServices from "../services/admin.service";
import axios from "axios";
import authHeader from "../services/auth-header";


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
        this.onChangeStatus = this.onChangeStatus.bind(this);

        this.state = {
            title: "",
            description: "",
            price: "",
            image: "",
            size: "",
            status_id: "",
            successful: false,
            message: "",
            products: [],
            orders: [],
            statuses: []
        };
    }

    componentDidMount() {
        axios.get(`http://127.0.0.1:8080/api/product/`)
            .then(res => {
                const products = res.data;
                this.setState({products});
            })
        axios.get(`http://127.0.0.1:8080/api/statuses/`)
            .then(res => {
                const statuses = res.data;
                this.setState({statuses: statuses});
            })
        axios.get(`http://127.0.0.1:8080/api/order/`, {
            headers: {
                'Content-type': 'application/json',
                'Authorization': authHeader()
            }
        })
            .then(res => {
                const orders = res.data;
                this.setState({orders: orders});
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

    onChangeStatus(e) {
        console.log(e.target)
        this.setState({
            status_id: e.target.value
        });
    }

    handleDeleteProduct(e, id) {
        axios.delete(`http://127.0.0.1:8080/api/product/${id}/`, {
            headers: {
                'Content-type': 'application/json',
                'Authorization': authHeader()
            }
        })
            .then(res => {
                console.log(res.data)
                window.location.reload()
            }).catch(err => {
            console.log(err)
            window.location.reload()
        })
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
                    if (error.response.status === 400) {
                        resMessage = "Не получилось создать продукт";
                    } else {
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

    handleChangeStatus(e, id) {
        e.preventDefault();
        axios.patch(`http://127.0.0.1:8080/api/order/${id}/`, {
                status_id: this.state.status_id
            },
            {
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': authHeader()

                }
            })
            .then(res => {
                console.log(res.data)
                window.location.reload()
            }).catch(err => {
            console.log(err)
            window.location.reload()
        })
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
                if (error.response.status === 400) {
                    resMessage = "Не получилось создать размер для продукта";
                } else {
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
                'Content-type': 'application/json',
                'Authorization': authHeader()

            }

        }).then(window.location.reload());

    }

    render() {
        console.log(this.state.orders)

        return (
            <div className="col-md-12">
                <h2 className="admin-title">Форма добавления нового продукта</h2>

                <div className="card ">
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
                                        validations={[required]}
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
                                        validations={[required]}
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
                                        validations={[required]}
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
                            <div className="">
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
                            style={{display: "none"}}
                            ref={c => {
                                this.checkBtn = c;
                            }}
                        />
                    </Form>
                </div>
                <div>
                    <h2 className="admin-title">Товары</h2>
                    <div className="admin-list-products-wrapper container">
                        {this.state.products.map(product =>
                            <div>
                                <div className="admin-list-products">
                                    <div>
                                        id {product.id}
                                    </div>
                                    <div>
                                        {product.title}
                                    </div>
                                    <div>
                                        {product.price} $
                                    </div>
                                    <div>
                                        <button onClick={(e) => this.handleDeleteProduct(e, product.id)}>
                                            Удалить
                                        </button>
                                    </div>
                                </div>
                                <div>

                                    <div>
                                        <Form
                                            onSubmit={(e) => this.handleCreateProductVariant(e, product.id)}
                                            ref={c => {
                                                this.form = c;
                                            }}
                                        >
                                            {!this.state.successful && (
                                                <div className="admin-create-product-variant">
                                                    <div className="admin-create-product-variant-label">
                                                        <label htmlFor="size"
                                                               className="admin-create-product-variant-label">Размер</label>
                                                        <Input
                                                            type="text"
                                                            className="add-size-control"
                                                            name="size"
                                                            value={this.state.size}
                                                            onChange={this.onChangeSize}
                                                            validations={[required]}
                                                        />
                                                    </div>
                                                    <div className="">
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
                                                }> Удалить
                                                </button>
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>
                        )}
                    </div>
                </div>
                <h2 className="admin-title">Заказы</h2>
                <div className="admin-list-products-wrapper container">
                    {this.state.orders.map(order =>
                        <div className="flex-column">
                            <div className="products">
                                <div className="navbar-link-wrapper">id {order?.id}</div>
                                <form className="" onSubmit={(e) => this.handleChangeStatus(e, order.id)}>
                                    <select name="status" onChange={this.onChangeStatus}
                                            className="filter-form-fields-select">
                                        <option selected="selected"/>
                                        {this.state.statuses?.map(status => {
                                                return (
                                                    <option value={status.id}
                                                            selected=
                                                                {order?.status?.id === status.id ?
                                                                    "selected" : ""
                                                                }
                                                    >{status.title}</option>
                                                )
                                            }
                                        )}
                                    </select>
                                    <button type="submit">Изменить</button>
                                </form>
                            </div>
                            {order?.productVariants?.map(productVariant =>
                                <div className="admin-list-product-variants">
                                    <div className="admin-list-product-variants-size">
                                        {productVariant?.size}
                                    </div>
                                    <div className="">
                                        {productVariant?.product?.title}
                                    </div>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        );
    }
}