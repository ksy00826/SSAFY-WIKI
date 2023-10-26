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
  Tooltip,
  Select,
} from "antd";
import {
  UserOutlined,
  InfoCircleOutlined,
  LockOutlined,
} from "@ant-design/icons";

const { Search } = Input;

const SignUp = ({ goNext, info }) => {
  const [checking, setChecking] = React.useState(false);
  const [emailBtn, setEmailBtn] = React.useState("이메일 확인");
  const [emailSuccess, setEmailSuccess] = React.useState(false);
  const [authBtn, setAuthBtn] = React.useState("인증번호 전송");
  const [authSuccess, setAuthSuccess] = React.useState(false);

  const finish = (e) => {
    console.log("finish????");
    // axios 회원가입 처리
    console.log(e);
    goNext();
  };

  const checkEmail = (value) => {
    // 실제 SSAFY 이메일인지 검증
    //axios
    console.log("checking", value, info.roll);
    setChecking(true);
    setChecking(false);

    let result = true;
    if (!result) {
    } else {
      setEmailBtn("이메일 확인 완료");
      setEmailSuccess(true);
    }
  };

  const handleAuth = (value) => {
    if (authBtn === "인증번호 전송") {
      sendEmail();
    } else {
      validateEmail();
    }
  };

  const sendEmail = () => {
    console.log(11, "에 인증 메일을 보낸다.");
    //axios
    setAuthBtn("인증번호 확인");
  };

  const validateEmail = () => {
    console.log("인증번호를 확인한다.");
    //axios
    setAuthBtn("인증번호 확인 완료");
    setAuthSuccess(true);
  };

  return (
    <Form
      name="basic"
      initialValues={{
        remember: true,
      }}
      onFinish={finish}
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
          {
            type: "email",
            message: "이메일 형식이 올바르지 않습니다.",
          },
          () => ({
            validator() {
              if (emailSuccess) return Promise.resolve();
              return Promise.reject(new Error("이메일을 확인해주세요."));
            },
          }),
        ]}
      >
        <Search
          placeholder="이메일"
          onSearch={checkEmail}
          enterButton={emailBtn}
          loading={checking}
          disabled={emailSuccess}
        />
      </Form.Item>

      <Form.Item
        name="auth"
        rules={[
          {
            required: true,
            message: "인증번호를 입력해주세요.",
          },
          () => ({
            validator() {
              if (authSuccess) return Promise.resolve();
              return Promise.reject(new Error("인증번호를 확인해주세요."));
            },
          }),
        ]}
      >
        <Search
          placeholder="인증번호"
          enterButton={authBtn}
          onSearch={handleAuth}
          disabled={authSuccess}
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
          ({ getFieldValue }) => ({
            validator(_, value) {
              let regex = new RegExp("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$");
              if (regex.test(value)) {
                return Promise.resolve();
              }
              return Promise.reject(
                new Error("영문 숫자 조합 8자리 이상 입력해주세요.")
              );
            },
          }),
        ]}
      >
        <Input.Password
          prefix={<LockOutlined className="site-form-item-icon" />}
          placeholder="비밀번호"
        />
      </Form.Item>

      <Form.Item
        // label="비밀번호"
        name="again"
        rules={[
          {
            required: true,
            message: "비밀번호를 입력해주세요.",
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue("password") === value) {
                return Promise.resolve();
              }
              return Promise.reject(new Error("비밀번호를 일치시켜주세요."));
            },
          }),
        ]}
      >
        <Input.Password
          prefix={<LockOutlined className="site-form-item-icon" />}
          placeholder="비밀번호 확인"
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          완료
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SignUp;
