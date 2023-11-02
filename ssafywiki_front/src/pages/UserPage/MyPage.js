import React, { useState } from 'react';
import { Link } from "react-router-dom";
import {
  FileOutlined,
  PieChartOutlined,
  TeamOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {Layout, Menu, theme } from 'antd';
import UserNavbar from 'components/Common/UserNavbar';
const { Header, Content, Footer, Sider } = Layout;
function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}
const MyPage = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  return (
      <Layout
          style={{
            minHeight: '100vh',
          }}
      >
        <UserNavbar selectedKey='1'></UserNavbar>
        <Layout>
          <Header
              style={{
                padding: 0,
                background: colorBgContainer,
              }}
          />
          <Content
              style={{
                margin: '0 16px',
              }}
          >
            <div
                style={{
                  padding: 24,
                  minHeight: 360,
                  background: colorBgContainer,
                }}
            >
              마이페이지
            </div>
          </Content>
          <Footer
              style={{
                textAlign: 'center',
              }}
          >
            Ant Design ©2023 Created by Ant UED
          </Footer>
        </Layout>
      </Layout>
  );
};
export default MyPage;