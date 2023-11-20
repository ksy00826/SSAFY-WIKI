import React from "react";
import { Input, Steps, Card, Button } from "antd";
import { useNavigate } from "react-router-dom";
import styles from "./SignUpPage.module.css";
import Form1 from "components/Member/SignUpForm1";
import Form2 from "components/Member/SignUpForm2";
import { createDocsWithoutLogin, createRedirectDocs } from "utils/DocsApi";
import { openNotification } from "App";

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
  const [info2, setInfo2] = React.useState();
  const [github, setGithub] = React.useState();
  const navigate = useNavigate();
  const next = () => {
    setCurrent(current + 1);
  };
  const saveGithub = (element) => {
    setGithub(element);
  };
  const save = (e) => {
    setInfo(e);
    //console.log(e);
  };
  const save2 = (e) => {
    setInfo2(e);
    //console.log(e);
  };
  const makeDefaultDocs = () => {
    //console.log(info2);
    //console.log(github);
    //console.log(info.username + (` (${info.number})` || "" ));
    createDocsWithoutLogin(
      {
        title: info.username + (` (${info.number})` || ""),
        content: `
        # ${info.username}

        ### 생일
        00 . 00 . 00
        
        ### MBTI
        
        ### 최근 취미
        
        # 마이 Github
        [![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=${github}&layout=compact)](https://github.com/anuraghazra/github-readme-stats)
        [![${github}'s github stats](https://github-readme-stats.vercel.app/api?username=${github}&show_icons=true&theme=default)](https://github.com/${github}/)
        [![Solved.ac Profile](http://mazassumnida.wtf/api/generate_badge?boj=${github})](https://solved.ac/profile/${github})
        
        # 프로젝트 
        |프로젝트|역할|담당기술|
        |------|---|---|
        | 프로젝트|역할  | 담당기술|
        
        # 최근 관심사
        
        # 논란`,
        categories: [info.roll],
        readAuth: 1,
        writeAuth: 2,
      },
      info2.access_token
    ).then((result) => {
      //완료
      //console.log(result);
      openNotification(
        "success",
        "문서작성 완료",
        `${result.title}문서가 생성되었습니다.`
      );
      navigate(`/res/content/${result.docsId}/${result.title}`);
    });
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
      {current === 1 ? (
        <Form2
          goNext={next}
          info={info}
          saveInfo={save2}
          saveGithub={saveGithub}
        ></Form2>
      ) : (
        <></>
      )}
      {current === 2 ? (
        <>
          <h3>안녕하세요, {info.username}님</h3>
          <Input
            addonBefore="GITHUB ID"
            onChange={saveGithub}
            defaultValue={(info2.email || "").split("@")[0]}
          ></Input>
          <Button type="default" onClick={makeDefaultDocs}>
            {info.username} 문서 생성하기
          </Button>
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
