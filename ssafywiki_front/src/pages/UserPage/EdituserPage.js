import { PlusOutlined } from "@ant-design/icons";
import UserNavbar from "components/Common/UserNavbar";
import React, { useState } from "react";
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
const { RangePicker } = DatePicker;
const { TextArea } = Input;
const normFile = (e) => {
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};
const EdituserPage = () => {
  const [componentDisabled, setComponentDisabled] = useState(true);
  const [docList, setdocList] = React.useState([]);
  // 처음 랜더링시 내용 가져오기
  // React.useEffect(() => {
  //   getContributedDocs().then((response) => {
  //     setdocList(response.docs);
  //   });
  // }, []);

  // const {
  //   token: { colorBgContainer },
  // } = theme.useToken();

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
        >
          {/* <Form.Item label="Checkbox" name="disabled" valuePropName="checked">
            <Checkbox>Checkbox</Checkbox>
          </Form.Item> */}
          {/* <Form.Item label="소속 캠퍼스">
            <Radio.Group>
              <Radio value="부울경"> 부울경 </Radio>
              <Radio value="서울"> 서울 </Radio>
              <Radio value="광주"> 광주 </Radio>
              <Radio value="대전"> 대전 </Radio>
              <Radio value="구미"> 구미 </Radio>
            </Radio.Group>
          </Form.Item> */}
          <Form.Item label="아이디">
            <Input disabled />
          </Form.Item>
          <Form.Item label="비밀번호">
            <Input.Password placeholder="비밀번호" />
          </Form.Item>
          <Form.Item label="비밀번호 확인">
            <Input.Password placeholder="비밀번호 확인" />
          </Form.Item>
          <Form.Item label="소속캠퍼스">
            <Select>
              <Select.Option value="부울경">부울경</Select.Option>
              <Select.Option value="서울">서울</Select.Option>
              <Select.Option value="구미">구미</Select.Option>
              <Select.Option value="광주">광주</Select.Option>
              <Select.Option value="대전">대전</Select.Option>
            </Select>
          </Form.Item>
          {/* <Form.Item label="TreeSelect">
            <TreeSelect
              treeData={[
                {
                  title: "Light",
                  value: "light",
                  children: [
                    {
                      title: "Bamboo",
                      value: "bamboo",
                    },
                  ],
                },
              ]}
            />
          </Form.Item> */}
          <Form.Item label="Cascader">
            <Cascader
              options={[
                {
                  value: "zhejiang",
                  label: "Zhejiang",
                  children: [
                    {
                      value: "hangzhou",
                      label: "Hangzhou",
                    },
                  ],
                },
              ]}
            />
          </Form.Item>
          <Form.Item label="DatePicker">
            <DatePicker />
          </Form.Item>
          <Form.Item label="RangePicker">
            <RangePicker />
          </Form.Item>
          <Form.Item label="InputNumber">
            <InputNumber />
          </Form.Item>
          <Form.Item label="TextArea">
            <TextArea rows={4} />
          </Form.Item>
          <Form.Item label="Switch" valuePropName="checked">
            <Switch />
          </Form.Item>
          <Form.Item
            label="Upload"
            valuePropName="fileList"
            getValueFromEvent={normFile}
          >
            <Upload action="/upload.do" listType="picture-card">
              <div>
                <PlusOutlined />
                <div
                  style={{
                    marginTop: 8,
                  }}
                >
                  Upload
                </div>
              </div>
            </Upload>
          </Form.Item>
          <Form.Item label="Button">
            <Button>Button</Button>
          </Form.Item>
          <Form.Item label="Slider">
            <Slider />
          </Form.Item>
        </Form>
      </Layout>
    </Layout>
  );
};
export default EdituserPage;
