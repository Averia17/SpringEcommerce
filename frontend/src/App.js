import React, { Component } from "react"
import axios from "axios"
import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import MainPage from "./components/MainPage";
import NavBar from "./components/Navbar";
import ProductDetail from "./components/ProductDetail";

if (window.location.origin === "http://localhost:3000") {
  axios.defaults.baseURL = "http://127.0.0.1:8080/api/product/";
}

class App extends Component {
  render() {
    return (
        <Router>
          <NavBar/>
          <Switch>
            <Route path="/products/" exact component={MainPage}/>
            <Route path="/products/:id/" exact component={ProductDetail}/>
            <Route component={MainPage}/>
          </Switch>
        </Router>
    );
  }
}

export default App;