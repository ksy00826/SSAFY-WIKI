import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FileOutlined,
  PieChartOutlined,
  TeamOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Breadcrumb, Layout, Menu, theme, ConfigProvider } from "antd";
import {} from "antd";
const { Header, Content, Footer, Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}
const items = [
  getItem(<Link to="/userpage">마이페이지</Link>, "1", <UserOutlined />),
  getItem(
    <Link to="/userpage/edituser">회원정보 수정</Link>,
    "2",
    <UserOutlined />
  ),
  getItem(
    <Link to="/userpage/contribution">기여한 문서</Link>,
    "3",
    <FileOutlined />
  ),
  getItem(
    <Link to="/userpage/userchats">참여한 채팅</Link>,
    "4",
    <PieChartOutlined />
  ),
  getItem(
    <Link to="/userpage">참여한 그룹</Link>,
    "5",
    <TeamOutlined />
    // [getItem('Team 1', '6'), getItem('Team 2', '8')]
  ),
];
const UserNavbar = (props) => {
  const [collapsed, setCollapsed] = useState(false);
  // console.log(props.selectedKey);
  return (
    <Sider
      collapsible
      collapsed={collapsed}
      onCollapse={(value) => setCollapsed(value)}
      theme="light"
    >
      <div className="demo-logo-verticall" />
      <Menu
        theme="light"
        mode="inline"
        items={items}
        activeKey={"1"}
        selectedKeys={props.selectedKey}
        style={{
          paddingTop: 20,
          padding: 12,
        }}
      />
    </Sider>
  );
};
export default UserNavbar;
