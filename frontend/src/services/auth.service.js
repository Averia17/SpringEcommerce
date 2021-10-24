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
                    console.log(localStorage.getItem("user"));
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
        return axios
            .get('http://127.0.0.1:8080/api/user/', {headers: {'Authorization': authHeader()}} )
            .then(response => {
                if (response.data.token) {
                   return response.data
                }

            });
    }
}

export default new AuthService();