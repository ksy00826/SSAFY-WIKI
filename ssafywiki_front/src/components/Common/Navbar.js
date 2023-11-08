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
import { useNavigate, useLocation, Link } from "react-router-dom";

import SearchDocs from "./SearchDocs";

import { isLogin } from "utils/Authenticate";
import { logout } from "utils/Authenticate";

const Navbar = () => {
  const [user, setUser] = React.useState(false);
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    setUser(false);
    window.location.reload();
  };

  const handleLogin = () => {
    console.log(location);
    navigate(`/member/login?redirect=${location.pathname}`);
  };

  const items1 = [
    {
      key: "SubMenu",
      label: <UserOutlined style={{ fontSize: "30px" }} />,
      children: [
        {
          label: <a onClick={handleLogin}>로그인</a>,
          key: "login",
        },
        {
          label: <Link to="/member/signup">회원가입</Link>,
          key: "singup",
        },
      ],
    },
  ];
  const items2 = [
    {
      key: "SubMenu",
      label: <UserOutlined style={{ fontSize: "30px" }}/>,
      children: [
        {
          label: <a onClick={handleLogout}>로그아웃</a>,
          key: "logout",
        },
        {
          label: <Link to="/userpage">마이페이지</Link>,
          key: "mypage",
        },
        { label: <Link to="/userpage">관리자 페이지</Link>, key: "admin" },
        {
          label: <Link to="/userpage/contribution">내 기여 목록</Link>,
          key: "my docs",
        },
        { label: <Link to="/userpage">스크랩 목록</Link>, key: "bookmark" },
      ],
    },
  ];

  // 위치 이동하고 랜더링 될때마다 로그인 되어있는지 확인
  React.useEffect(() => {
    setUser(isLogin());
  });

  const goHome = () => {
    navigate("/");
  };
  return (
    <div className={styles.NavBox}>
      <div className={styles.NavLogoBox} onClick={goHome}>
        <img src={Logoimg} className={styles.NavLogo} />
      </div>
      <div className={styles.ComponentBox}>
        <div className={styles.NavSearch}>
          <SearchDocs />
        </div>
        <ConfigProvider
          theme={{
            token: {},
          }}
          style={{ width: "30px", paddingTop: "5px" }}
        >
          {user ? (
            <Menu className={styles.NavUser} mode="horizontal" items={items2} />
          ) : (
            <Menu className={styles.NavUser} mode="horizontal" items={items1} />
          )}
        </ConfigProvider>
      </div>
    </div>
  );
};
export default Navbar;
