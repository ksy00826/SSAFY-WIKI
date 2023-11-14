import React, { useEffect } from "react";
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
import { LockOutlined } from "@ant-design/icons";
import { checkSSAFYEmail } from "utils/UserApi";
import { axiosInstance } from "utils/AxiosConfig";
import { openNotification } from "App";
import { signup, sendEmail, authEmail } from "utils/Authenticate";

import styles from "./SignUpForm2.module.css";

const { Search } = Input;

const SignUp = ({ goNext, info, saveInfo, saveGithub }) => {
  const [checking, setChecking] = React.useState(false);
  const [emailBtn, setEmailBtn] = React.useState("이메일 확인");
  const [emailSuccess, setEmailSuccess] = React.useState(false);
  const [emailFail, setEmailFail] = React.useState(false);
  const [authBtn, setAuthBtn] = React.useState("인증번호 전송");
  const [authSuccess, setAuthSuccess] = React.useState(false);
  const [form] = Form.useForm();

  const finish = (e) => {
    //console.log("finish????");
    //console.log(e);
    //console.log(info);

    const responseBody = {
      name: info.username,
      nickname: info.nickname,
      email: e.email,
      password: e.password,
      campus: info.campus,
    };

    if (info.roll === "학생") {
      responseBody.number = info.number;
      responseBody.role = info.roll2;
    } else {
      responseBody.role = info.roll;
    }
    //console.log(responseBody);
    // axios 회원가입 처리
    signup(responseBody)
      .then((response) => {
        //console.log(response);
        saveInfo({
          access_token: response.access_token,
          email: responseBody.email,
          number: responseBody.number,
        });
        saveGithub((responseBody.email || "").split("@")[0]);
        goNext();
      })
      .catch((e) => console.error(e.message));
  };

  const checkEmail = (value) => {
    // 실제 SSAFY 이메일인지 검증
    //axios
    //console.log(info);
    //console.log("checking", value, info.roll);
    setChecking(true);

    let result = true;
    if (info.roll === "학생") {
      checkSSAFYEmail(value).then((data) => {
        //console.log(data);
        if (data === "아이디가 존재하지 않습니다.") {
          result = false;
        }
        if (!result) {
          setEmailBtn("이메일 확인");
          setEmailSuccess(false);
        } else {
          setEmailBtn("이메일 확인 완료");
          setEmailSuccess(true);
        }
      });
    } else {
      var emails = value.split("@");
      if (!(emails[1] === "multicampus.com" || emails[1] === "ssafy.com")) {
        result = false;
      }
      //console.log(result);
      if (!result) {
        setEmailBtn("이메일 확인");
        setEmailSuccess(false);
      } else {
        setEmailBtn("이메일 확인 완료");
        setEmailSuccess(true);
      }
    }
    if (!result) setEmailFail(true);
    else setEmailFail(false);

    setChecking(false);
  };

  const handleSendEmail = async () => {
    const email = form.getFieldValue(["email"]);
    //console.log(email, "에 인증 메일을 보낸다.");
    if (!emailSuccess) {
      openNotification("error", "인증메일 전송", `이메일을 확인해주세요`);
      return;
    }
    openNotification(
      "success",
      "인증메일 전송",
      `${email}로 이메일이 전송되었습니다.`
    );
    try {
      sendEmail(email, info.roll).then((data) => {
        //console.log(data);
        setAuthBtn("재전송");
      });
    } catch (error) {
      //console.log(error);
    }
  };

  const validateEmail = () => {
    const email = form.getFieldValue(["email"]);
    const value = form.getFieldValue(["auth"]);
    //console.log("인증번호를 확인한다.");
    let result = true;
    authEmail(email, info.roll, value).then((data) => {
      //console.log(data);
      if (data === "실패") {
        result = false;
      }
      if (!result) {
        setAuthBtn("인증번호 확인");
        setAuthSuccess(false);
      } else {
        setAuthBtn("인증번호 확인 완료");
        setAuthSuccess(true);
      }
    });
  };

  useEffect(() => {
    //console.log("s", emailSuccess, "f", emailFail);
    form.validateFields(["email"]);
  }, [emailSuccess, emailFail]);

  useEffect(() => {
    //console.log("s", emailSuccess, "f", emailFail);
    form.validateFields(["auth"]);
  }, [authSuccess]);

  return (
    <Form
      name="sing_up"
      initialValues={{
        remember: true,
      }}
      onFinish={finish}
      autoComplete="off"
      form={form}
    >
      <Form.Item
        // label="이메일"
        name="email"
        rules={[
          () => ({
            validator(_, value) {
              if (!value) {
                return Promise.reject(new Error("이메일을 입력해주세요."));
              }
              if (
                /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/.test(value)
              ) {
                //console.log("1");
              } else {
                setEmailFail(false);
                return Promise.reject(
                  new Error("이메일 형식이 올바르지 않습니다.")
                );
              }

              if (emailFail)
                return Promise.reject(new Error("SSAFY인증에 실패하였습니다."));
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
          () => ({
            validator(_, value) {
              if (!value) {
                return Promise.reject(new Error("인증번호를 입력해주세요."));
              }
              if (authSuccess) return Promise.resolve();
              return Promise.reject(new Error("인증번호를 확인해주세요."));
            },
          }),
        ]}
      >
        <div className={styles.authBox}>
          <Input placeholder="인증번호" disabled={authSuccess} />
          <Button type="primary" onClick={handleSendEmail}>
            {authBtn}
          </Button>
          <Button onClick={validateEmail}>확인</Button>
        </div>
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
        name="password2"
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
