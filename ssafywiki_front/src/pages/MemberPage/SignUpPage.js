import React from "react";
import { Input, Steps, Card, Button } from "antd";

import styles from "./SignUpPage.module.css";
import Form1 from "components/Member/SignUpForm1";
import Form2 from "components/Member/SignUpForm2";

const { Search } = Input;
const description = "This is a description.";
const steps = [
  {
    title: "회원 정보 기입",
  },
  {
    title: "SSAFY 인증",
  },
  {
    title: "회원가입 완료",
  },
];

const SignUp = () => {
  const [current, setCurrent] = React.useState(0);
  const [info, setInfo] = React.useState();

  const next = () => {
    setCurrent(current + 1);
  };

  const save = (e) => {
    setInfo(e);
    console.log(e);
  };

  return (
    <Card
      style={{
        width: 600,
        marginLeft: "10%",
        marginRight: "10%",
      }}
    >
      <h1>회원가입</h1>
      <Steps current={current} items={steps} className={styles.margintd} />

      {current === 0 ? <Form1 goNext={next} saveInfo={save}></Form1> : <></>}
      {current === 1 ? <Form2 goNext={next} info={info}></Form2> : <></>}
      {current === 2 ? (
        <>
          <h3>안녕하세요, {info.username}님</h3>
          <Button type="default" href="/member/login">
            로그인 화면으로
          </Button>
        </>
      ) : (
        <></>
      )}
    </Card>
  );
};

export default SignUp;
