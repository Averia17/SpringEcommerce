import axios from "axios";
import authHeader from "./auth-header";



const API_URL = "http://localhost:8080/api/";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + "auth", {
                username,
                password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username, email, password) {
        return axios.post(API_URL + "register", {
            username,
            email,
            password
        });
    }

    getCurrentUser() {
        return localStorage.getItem("user");
    }
}

export default new AuthService();