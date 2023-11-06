import { Routes, Route } from "react-router-dom";
import { Layout } from "antd";

import Login from "./LoginPage";
import SignUp from "./SignUpPage";
import FindPW from "./FindPassword";

import styles from "./MemberPage.module.css";

const { Content } = Layout;
const MemberPage = () => {
  return (
    <Content className={styles.box}>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/findpw" element={<FindPW />} />
      </Routes>
    </Content>
  );
};

export default MemberPage;
