import "./App.css";

import React from "react";
import { Layout } from "antd";

import AppRouter from "./AppRouter";
import Navbar from "./components/Common/Navbar";
import MyFooter from "./components/Common/Footer";

const { Header, Footer, Sider, Content } = Layout;

function App() {
  return (
    <div className="App">
      <Layout
        style={{
          minHeight: "100vh",
        }}
      >
        <Header>
          <Navbar />
        </Header>
        <Layout>
          <AppRouter />
        </Layout>
        <Footer>
          <MyFooter />
        </Footer>
      </Layout>
    </div>
  );
}

export default App;
