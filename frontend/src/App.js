import React, { Component } from "react"
import axios from "axios"
import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import MainPage from "./components/MainPage";
import NavBar from "./components/Navbar";
import ProductDetail from "./components/ProductDetail";
import Cart from "./components/Cart";
import Profile from "./components/Profile";
import Admin from "./components/Admin";
import Login from "./components/Login";
import Register from "./components/Register";
import auth from "./services/auth.service";
import ManProducts from "./components/ManProducts";
import WomanProducts from "./components/WomanProducts";

if (window.location.origin === "http://localhost:3000") {
  axios.defaults.baseURL = "http://127.0.0.1:8080/api/product/";
}


function requireAuth(nextState, replace) {
    let user = JSON.parse(auth.getCurrentUser());
    console.log(user);
    if (user ==null) {
        replace({
            pathname: '/login'
        })
    }
}


function privateRoute(Component) {
    let user = JSON.parse(auth.getCurrentUser());
    let hasUserAccess = false;
    if(user?.role === 'ROLE_ADMIN')
    {
        hasUserAccess = true;
    }
    return function(props) {
        if (!hasUserAccess) {
            return <MainPage />;
        }
        return <Component {...props} />;
    };
}

class App extends Component {
  render() {
    return (
        <Router>
          <NavBar/>
          <Switch>
            <Route path="/products/" exact component={MainPage}/>
            <Route path="/products/man/" exact component={ManProducts}/>
            <Route path="/products/woman/" exact component={WomanProducts}/>
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route path="/cart/" exact component={Cart}/>
            <Route path="/profile/" exact  component={Profile}/>
            <Route path="/admin/" exact component={privateRoute(Admin)}/>
            <Route path="/products/:id/" exact component={ProductDetail}/>
            <Route component={MainPage}/>
          </Switch>
        </Router>
    );
  }
}

export default App;