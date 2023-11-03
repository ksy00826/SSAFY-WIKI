import React from "react";
import { Button, Form, Input, Select } from "antd";
import { UserOutlined } from "@ant-design/icons";

const roll = [
  {
    value: "학생",
    label: "학생",
  },
  {
    value: "프로",
    label: "프로",
  },
  {
    value: "컨설턴트",
    label: "컨설턴트",
  },
  {
    value: "코치",
    label: "코치",
  },
];
const roll2 = [
  {
    value: "8기",
    label: "8기",
  },
  {
    value: "9기",
    label: "9기",
  },
  {
    value: "10기",
    label: "10기",
  },
  {
    value: "11기",
    label: "11기",
  },
];
const campus = [
  {
    value: "서울",
    label: "서울",
  },
  {
    value: "대전",
    label: "대전",
  },
  {
    value: "구미",
    label: "구미",
  },
  {
    value: "광주",
    label: "광주",
  },
  {
    value: "부울경",
    label: "부울경",
  },
];

const SignUp = ({ goNext, saveInfo }) => {
  const [isStudent, setStudent] = React.useState(false);

  const finish = (e) => {
    saveInfo(e);
    goNext();
  };
  const onChangeRoll = (value) => {
    if (value === "학생") setStudent(true);
    else setStudent(false);
  };
  const filterOption = (input, option) =>
    (option?.label ?? "").toLowerCase().includes(input.toLowerCase());

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
        name="roll"
        rules={[
          {
            required: true,
            message: "",
          },
        ]}
      >
        <Select
          showSearch
          placeholder="역할"
          optionFilterProp="children"
          onChange={onChangeRoll}
          filterOption={filterOption}
          options={roll}
        />
      </Form.Item>

      <Form.Item
        name="campus"
        rules={[
          {
            required: true,
            message: "",
          },
        ]}
      >
        <Select
          showSearch
          placeholder="캠퍼스"
          optionFilterProp="children"
          filterOption={filterOption}
          options={campus}
        />
      </Form.Item>

      {isStudent ? (
        <Form.Item
          name="roll2"
          rules={[
            {
              required: true,
              message: "",
            },
          ]}
        >
          <Select
            showSearch
            placeholder="기수"
            optionFilterProp="children"
            filterOption={filterOption}
            options={roll2}
          />
        </Form.Item>
      ) : (
        <></>
      )}

      {isStudent ? (
        <Form.Item
          name="number"
          rules={[
            {
              required: true,
              message: "",
            },
          ]}
        >
          <Input placeholder="학번" />
        </Form.Item>
      ) : (
        <></>
      )}

      <Form.Item
        name="username"
        rules={[
          {
            required: true,
            message: "",
          },
        ]}
      >
        <Input
          prefix={<UserOutlined className="site-form-item-icon" />}
          placeholder="이름"
        />
      </Form.Item>

      <Form.Item
        name="nickname"
        rules={[
          {
            required: true,
            message: "",
          },
        ]}
      >
        <Input
          prefix={<UserOutlined className="site-form-item-icon" />}
          placeholder="닉네임"
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          다음
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SignUp;
