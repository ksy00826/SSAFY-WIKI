import React from "react";
import {
  Card,
  Row,
  Space,
  Col,
  Select,
  Divider,
  Form,
  Input,
  List,
  Skeleton,
  Button,
} from "antd";
import { UserOutlined } from "@ant-design/icons";

const readAuth = [
  { value: 1, label: "전체공개" },
  { value: 2, label: "싸피인공개" },
  { value: 3, label: "관리자 공개" },
  { value: 100, label: "사용자 지정 권한" },
];
const writeAUth = [
  { value: 2, label: "싸피인 수정가능" },
  { value: 3, label: "관리자 수정가능" },
  { value: 100, label: "사용자 지정 권한" },
];
const dummydata = [
  {
    nickname: "최영은",
    email: "duddms0190@naver.com",
  },
  {
    nickname: "Ant Design Title 2",
    email: "duddms0190@naver.com",
  },
  {
    nickname: "Ant Design Title 3",
    email: "duddms0190@naver.com",
  },
  {
    nickname: "Ant Design Title 4",
    email: "duddms0190@naver.com",
  },
];
const { Search } = Input;

const AuthorityForm = ({
  selectedRead,
  setSelectedRead,
  selectedWrite,
  setSelectedWrite,
  userList,
  modify,
  handleInvite,
  handleDelete,
}) => {
  const [isUserDefine1, setUserDefine1] = React.useState(false);
  const [isUserDefine2, setUserDefine2] = React.useState(false);
  const [invitloading, setInvitLoading] = React.useState();

  const checkEmail = (value) => {
    console.log(value);
    // 이메일이 존재하는 유저인지 체크하고 추가.
    handleInvite(value);
  };

  const deleteUser = (item) => {
    handleDelete(item);
  };

  React.useEffect(() => {
    // 첫 랜더링시
    if (selectedRead >= 100) setUserDefine1(true);
    if (selectedWrite >= 100) setUserDefine2(true);
  }, []);

  const handleRead = (value) => {
    setSelectedRead(value);
    if (value == 100) setUserDefine1(true);
    else setUserDefine1(false);
  };

  const handleWrite = (value) => {
    setSelectedWrite(value);
    if (value == 100) setUserDefine2(true);
    else setUserDefine2(false);
  };

  return (
    <div>
      <Card
        style={{
          textAlign: "left",
        }}
      >
        <h3>권한</h3>

        <Row>
          <Space>
            <Col>읽기권한</Col>
            <Col>
              <Select
                style={{
                  width: 150,
                }}
                options={readAuth}
                onChange={handleRead}
                defaultValue={selectedRead < 1000 ? selectedRead : 100}
              />
            </Col>
          </Space>
        </Row>
        <Row>
          <Space>
            <Col>쓰기권한</Col>
            <Col>
              <Select
                style={{
                  width: 150,
                }}
                options={writeAUth}
                onChange={handleWrite}
                defaultValue={selectedWrite < 1000 ? selectedWrite : 100}
              />
            </Col>
          </Space>
        </Row>

        {modify ? <Button onClick={modify}>수정</Button> : <></>}

        <Divider />
        <h3>사용자 지정권한</h3>

        <Form>
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
            ]}
          >
            <Search
              placeholder="이메일"
              onSearch={checkEmail}
              enterButton="초대"
              loading={invitloading}
              disabled={!(isUserDefine1 || isUserDefine2)}
            />
          </Form.Item>
        </Form>
        <List
          className="demo-loadmore-list"
          dataSource={userList}
          renderItem={(item) => (
            <List.Item
              actions={[
                <a onClick={() => deleteUser(item)} key="list-item-delete">
                  삭제
                </a>,
              ]}
            >
              <Skeleton avatar title={false} loading={item.loading} active>
                <List.Item.Meta
                  avatar={<UserOutlined />}
                  title={<a>{item.nickname}</a>}
                  description={item.email}
                />
              </Skeleton>
            </List.Item>
          )}
        />
      </Card>
    </div>
  );
};

export default AuthorityForm;
