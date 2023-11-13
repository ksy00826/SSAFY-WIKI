import React, { useState } from "react";
import styles from "./Navbar.module.css";
import Logoimg from "assets/img/ssafywiki.png";
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
import { getIsAdmin } from "utils/UserApi";

const Navbar = () => {
  const [user, setUser] = React.useState(false);
  const [isAdmin, setIsAdmin] = React.useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  // 현재 경로에 따라 선택된 메뉴 키를 결정
  const selectedKeys = location.pathname === "/" ? [] : [location.pathname];

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
      label: <div className={styles.label}><span className={styles.labelText}>/</span><UserOutlined style={{ fontSize: "30px" }} /></div>,
      children: [
        {
          label: <a onClick={handleLogin}>로그인</a>,
          key: "/login",
        },
        {
          label: <Link to="/member/signup">회원가입</Link>,
          key: "/member/signup",
        },
      ],
    },
  ];
  const items2 = [
    {
      key: "SubMenu",
      label: <div className={styles.label}><span className={styles.labelText}>/</span><UserOutlined style={{ fontSize: "30px" }} /></div>,
      children: [
        {
          label: <Link to="/userpage">마이페이지</Link>,
          key: "/userpage",
        },
        {
          label: <a onClick={handleLogout}>로그아웃</a>,
          key: "logout",
        },
      ],
    },
  ];

  const items3 = [
    {
      key: "SubMenu",
      label: <div className={styles.label}><span className={styles.labelText}>/</span><UserOutlined style={{ fontSize: "30px" }} /></div>,
      children: [
        { label: <Link to="/userpage">관리자 페이지</Link>, key: "userpage" },
        {
          label: <a onClick={handleLogout}>로그아웃</a>,
          key: "logout",
        },
      ],
    },
  ];
  // 위치 이동하고 랜더링 될때마다 로그인 되어있는지 확인
  React.useEffect(() => {
    setUser(isLogin());

    console.log("location.pathname", location.pathname);

    //admin
    getIsAdmin()
      .then((response) => {
        console.log("isAdmin", response);
        setIsAdmin(response);
      })
      .catch((err) => console.log("not admin"));
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
          style={{ width: "30px", paddingTop: "10px", marginTop: "5px" }}
        >
          {user ? (
            isAdmin ? (
              <Menu
                className={styles.NavUser}
                mode="horizontal"
                items={items3}
                selectedKeys={selectedKeys}
              />
            ) : (
              <Menu
                className={styles.NavUser}
                mode="horizontal"
                items={items2}
                selectedKeys={selectedKeys}
              />
            )
          ) : (
            <Menu
              className={styles.NavUser}
              mode="horizontal"
              items={items1}
              selectedKeys={selectedKeys}
            />
          )}
        </ConfigProvider>
      </div>
    </div>
  );
};
export default Navbar;
