import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/admin/";

class AdminService {

    createProduct(title, description, price, image) {
        return axios
            .post(API_URL + "product/", {
                title,
                description,
                price,
                image
            }, {
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': authHeader()

                }
            })
    }
    createProductVariant(size, id) {
        console.log("create product variant")
        return axios
            .post(API_URL + `product/${id}/`, {
                size,
            }, {
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': authHeader()

                }
            })
    }
}

export default new AdminService();