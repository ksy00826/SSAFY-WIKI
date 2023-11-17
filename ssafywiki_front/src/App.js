import "./App.css";

import React from "react";
import { Layout, FloatButton } from "antd";
import { BrowserRouter } from "react-router-dom";
import AppRouter from "./AppRouter";
import Navbar from "./components/Common/Navbar";
import MyFooter from "./components/Common/Footer";
import ScrollToTop from "components/Common/ScrollToTop";

import { notification } from "antd";
import ScrollToDown from "components/Common/ScrollToDown";

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
          <FloatButton.Group>
            <ScrollToTop />
            <ScrollToDown />
          </FloatButton.Group>

          <Header className="App-header">
            <Navbar />
          </Header>
          <Layout className="Content">
            <AppRouter />
          </Layout>
          <Footer style={{ minWidth: "1200px" }}>
            <MyFooter />
          </Footer>
        </Layout>
      </div>
    </BrowserRouter>
  );
}

export default App;
