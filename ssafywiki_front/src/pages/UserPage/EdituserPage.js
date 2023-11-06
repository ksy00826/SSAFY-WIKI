import { PlusOutlined } from "@ant-design/icons";
import UserNavbar from "components/Common/UserNavbar";
import React, { useState } from "react";
import { LockOutlined } from "@ant-design/icons";
import { openNotification } from "App";
import {
  Layout,
  Button,
  Cascader,
  Checkbox,
  DatePicker,
  Form,
  Input,
  InputNumber,
  Radio,
  Select,
  Slider,
  Switch,
  TreeSelect,
  Upload,
} from "antd";
import { getUserProfile, editUserProfile } from "utils/UserApi";
const { RangePicker } = DatePicker;
const { TextArea } = Input;

const EdituserPage = () => {
  // 처음 랜더링시 내용 가져오기
  const [email, setEmail] = React.useState("email");
  const [nickname, setNickname] = React.useState("nickname");
  const [campus, setCampus] = React.useState("campus");
  const [name, setName] = React.useState("name");
  const [number, setNumber] = React.useState("number");
  const [password, setPassword] = React.useState("");
  React.useEffect(() => {
    getUserProfile().then((response) => {
      console.log(response);
      setEmail(response.email);
      setNickname(response.nickname);
      setCampus(response.campus);
      setName(response.name);
      setNumber(response.number);
    });
  }, []);

  const handleNicknameChange = (e) => {
    console.log(e.target.value);
    setNickname(e.target.value);
  };

  const finish = (e) => {
    const responseBody = {
      nickname: nickname,
      password: e.password,
    };
    console.log(responseBody);
    editUserProfile(responseBody)
      .then((response) => {
        console.log(response);
        openNotification("success", "회원정보 수정 완료");
      })
      .catch((e) => console.error(e.message));
  };

  return (
    <Layout
      style={{
        minHeight: "100vh",
      }}
    >
      <UserNavbar selectedKey="2"></UserNavbar>
      <Layout>
        <Form
          labelCol={{
            span: 4,
          }}
          wrapperCol={{
            span: 14,
          }}
          layout="horizontal"
          style={{
            maxWidth: 1200,
          }}
          onFinish={finish}
          autoComplete="off"
        >
          <Form.Item label="아이디">
            <Input disabled placeholder={email}></Input>
          </Form.Item>
          <Form.Item label="닉네임">
            <Input
              autoComplete="false"
              placeholder={nickname}
              value={nickname}
              onChange={handleNicknameChange}
            ></Input>
          </Form.Item>
          <Form.Item label="소속캠퍼스">
            <Input disabled placeholder={campus}></Input>
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
                  return Promise.reject(
                    new Error("비밀번호를 일치시켜주세요.")
                  );
                },
              }),
            ]}
          >
            <Input.Password
              prefix={<LockOutlined className="site-form-item-icon" />}
              placeholder="비밀번호 확인"
            />
          </Form.Item>

          <Form.Item label="학번">
            <Input disabled placeholder={number} />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              완료
            </Button>
          </Form.Item>
        </Form>
      </Layout>
    </Layout>
  );
};
export default EdituserPage;
