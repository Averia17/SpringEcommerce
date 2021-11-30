import axios from "axios";

const API_URL = "http://localhost:8080/api/admin/";

class AdminService {

    createProduct(title, description, price, image) {
        return axios
            .post(API_URL + "product/", {
                title,
                description,
                price,
                image
            })
    }
    createProductVariant(size, id) {
        console.log("create product variant")
        return axios
            .post(API_URL + `product/${id}/`, {
                size,
            })
    }
}

export default new AdminService();