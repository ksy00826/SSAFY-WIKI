import "./App.css";

import React from "react";
import { Layout } from "antd";
import { BrowserRouter } from "react-router-dom";
import AppRouter from "./AppRouter";
import Navbar from "./components/Common/Navbar";
import MyFooter from "./components/Common/Footer";

import { notification } from "antd";

// Notification 초기화
notification.config({
  placement: "topRight", // 알림 위치 설정
  duration: 3, // 알림 노출 시간 설정 (초 단위)
});

export const openNotification = (type, message, description) => {
  notification[type]({
    message: message,
    description: description,
  });
};

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
          <div className="HeaderBox">
            <Header className="App-header">
              <Navbar />
            </Header>
          </div>
          <Layout className="Content">
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
