import React from "react";
import {
  Alert,
  Button,
  Checkbox,
  Form,
  Input,
  Space,
  Typography,
  Steps,
} from "antd";
import {
  LoadingOutlined,
  SmileOutlined,
  SolutionOutlined,
  UserOutlined,
} from "@ant-design/icons";

import { LockOutlined } from "@ant-design/icons";

const description = "This is a description.";
const steps = [
  {
    title: "SSAFY 인증",
    status: "Verification",
    // icon: <UserOutlined />,
  },
  {
    title: "회원 정보 기입",
    status: "info",
    // icon: <SolutionOutlined />,
  },
  {
    title: "회원가입 완료",
    status: "finish",
    // icon: <SmileOutlined />,
  },
];

const SignUp = () => {
  const [current, setCurrent] = React.useState(0);
  const finish = () => {};
  return (
    <div>
      <h1>회원가입</h1>
      <Steps
        size="small"
        current={current}
        items={steps}
        style={{ marginTop: "10%", marginBottom: "10%" }}
      />
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
        onFinish={finish()}
        autoComplete="off"
      >
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
            <Button type="default">회원가입</Button>
          </Space>
        </Form.Item>
      </Form>
    </div>
  );
};

export default SignUp;
