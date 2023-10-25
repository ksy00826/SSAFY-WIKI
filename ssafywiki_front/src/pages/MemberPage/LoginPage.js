import React from "react";
import { Alert, Button, Checkbox, Form, Input, Space, Typography } from "antd";
import { useNavigate } from "react-router-dom";
import { LockOutlined, UserOutlined } from "@ant-design/icons";

import {
  login,
  rememberEmail,
  getRememberEmail,
  removeRememberEmail,
} from "utils/Authenticate";

const { Link } = Typography;

const Login = () => {
  const [errmsg, setErrMsg] = React.useState("");

  const navigate = useNavigate();

  const doLogin = (e) => {
    console.log(e);
    let result = login(e.email, e.password);
    if (result.state === 200) {
      //성공
      // 아이디 저장
      if (e.remember) {
        rememberEmail(e.email);
      } else {
        removeRememberEmail();
      }
      navigate("/userpage");
    } else {
      // 실패
      setErrMsg(result.msg);
    }
  };

  const goToSignUp = () => {
    navigate("/member/signup");
  };

  return (
    <Form
      name="basic"
      style={{
        width: 600,
        marginLeft: "10%",
        marginRight: "10%",
      }}
      initialValues={{
        remember: true,
      }}
      onFinish={doLogin}
      autoComplete="off"
    >
      <h1>로그인</h1>
      {errmsg === "" ? (
        <></>
      ) : (
        <Form.Item>
          <Alert type="error" message={errmsg} showIcon />
        </Form.Item>
      )}

      <Form.Item
        // label="이메일"
        name="email"
        rules={[
          {
            required: true,
            message: "이메일을 입력해주세요.",
          },
        ]}
      >
        <Input
          prefix={<UserOutlined className="site-form-item-icon" />}
          placeholder="이메일"
          defaultValue={getRememberEmail}
        />
      </Form.Item>

      <Form.Item
        // label="비밀번호"
        name="password"
        rules={[
          {
            required: true,
            message: "비밀번호를 입력해주세요.",
          },
        ]}
      >
        <Input.Password
          prefix={<LockOutlined className="site-form-item-icon" />}
          placeholder="비밀번호"
        />
      </Form.Item>

      <Form.Item name="remember" valuePropName="checked">
        <Checkbox>이메일 기억하기</Checkbox>
      </Form.Item>

      <Form.Item>
        <Space>
          <Button type="primary" htmlType="submit">
            로그인
          </Button>
          <Button type="default" onClick={goToSignUp}>
            회원가입
          </Button>
        </Space>
      </Form.Item>

      <Link href="/member/findpw">비밀번호 찾기</Link>
    </Form>
  );
};

export default Login;
