import "./App.css";

import React from "react";
import { Layout } from "antd";
import { BrowserRouter } from "react-router-dom";
import AppRouter from "./AppRouter";
import Navbar from "./components/Common/Navbar";
import MyFooter from "./components/Common/Footer";

const { Header, Footer, Sider, Content } = Layout;

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Layout
          style={{
            minHeight: "100vh",
          }}
        >
          <Header className="App-header">
            <Navbar />
          </Header>
          <Layout
            style={{
              backgroundColor: "white",
            }}
          >
            <AppRouter />
          </Layout>
          <Footer
            style={{
              backgroundColor: "white",
            }}
          >
            <MyFooter />
          </Footer>
        </Layout>
      </div>
    </BrowserRouter>
  );
}

export default App;
