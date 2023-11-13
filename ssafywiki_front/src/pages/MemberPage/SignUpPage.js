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
  const navigate = useNavigate();
  const next = () => {
    setCurrent(current + 1);
  };
  const save = (e) => {
    setInfo(e);
    console.log(e);
  };
  const save2 = (e) => {
    setInfo2(e);
    console.log(e);
  };
  const makeDefaultDocs = () => {
    console.log(info2);
    console.log(info);
    createDocsWithoutLogin(
      {
        title: info.username,
        content: `### Hi there 👋\nI'm ${(info2.email || "").split('@')[0]}, a software engineer 💻 currently working at [Takeaway.com](https://www.ssafy.com/) 🍲🥡\n\nI have a passion for clean code, Java, teaching, PHP, Lifeguarding and Javascript\n\n# Here are some good things to introduce yourself\n###  change several \"${(info2.email || "").split('@')[0]}\" to your github Id\n# 문서를 꾸미기 위한 마크다운 뱃지들\n![C++](https://img.shields.io/badge/c++-%2300599C.svg?style=for-the-badge&logo=c%2B%2B&logoColor=white)\n![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)\n![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)\n![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)\n![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)\n\nhttps://ileriayo.github.io/markdown-badges/#markdown-badges\n\n# 깃허브에서 사용한 언어 그래프\n[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=${(info2.email || "").split('@')[0]}&layout=compact)](https://github.com/anuraghazra/github-readme-stats)\n\n# 깃허브 스탯\n[![${(info2.email || "").split('@')[0]}'s github stats](https://github-readme-stats.vercel.app/api?username=${(info2.email || "").split('@')[0]}&show_icons=true&theme=default)](https://github.com/${(info2.email || "").split('@')[0]}/)\n\n### thema can be one of [ dark radical merko gruvbox tokyonight ondark cobalt synthwave highcontrast dracula ]\n\n# 하이퍼링크\n[Email 📬](mailto:hallo@dannyverpoort.nl)\n[LinkedIn 💼](https://linkedin.com/in/dannyverpoort)\n[Twitter 🐦](https://twitter.com/dannyverp)\n[Website 🌍](https://dannyverpoort.dev/)`,
        categories: [info.roll],
        readAuth: 1,
        writeAuth: 2,
      },
      info2.access_token
    ).then((result) => {
      //완료
      console.log(result);
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
        <Form2 goNext={next} info={info} saveInfo={save2}></Form2>
      ) : (
        <></>
      )}
      {current === 2 ? (
        <>
          <h3>안녕하세요, {info.username}님</h3>
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
