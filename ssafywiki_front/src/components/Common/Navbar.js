import React, { useState } from "react";
import styles from "./Navbar.module.css";
import Logoimg from "assets/img/logo.png";
import {
  AppstoreOutlined,
  MailOutlined,
  SettingOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Menu, ConfigProvider } from "antd";
const items = [
  {
    key: "SubMenu",
    label: <UserOutlined />,
    children: [
      {
        type: "group",
        label: "Item 1",
        children: [
          {
            label: "로그인",
            key: "setting:1",
          },
          {
            label: "회원가입",
            key: "setting:2",
          },
        ],
      },
      {
        type: "group",
        label: "Item 2",
        children: [
          {
            label: "로그아웃",
            key: "setting:3",
          },
          { label: "관리자 페이지", key: "admin" },
          { label: "내 기여 목록", key: "my docs" },
          { label: "스크랩 목록", key: "my docs" },
          {
            label: "마이페이지",
            key: "setting:4",
          },
        ],
      },
    ],
  },
];
const Navbar = () => {
  return (
    <div className={styles.NavBox}>
      <div className={styles.NavLogoBox}>
        <img src={Logoimg} className={styles.NavLogo} />
      </div>
      <ConfigProvider
        theme={{
          token: {},
        }}
      >
        <Menu className={styles.NavBar} mode="horizontal" items={items} />
      </ConfigProvider>
    </div>
  );
};
export default Navbar;
