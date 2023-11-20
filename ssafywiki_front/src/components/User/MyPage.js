import React, { useState } from "react";
import {} from "@ant-design/icons";
import {
  Layout,
  theme,
  Row,
  Col,
  Image,
  Divider,
  Tooltip,
  Button,
  Modal,
  Input,
} from "antd";
import { getUserProfile } from "utils/UserApi";
import { AuditOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import { getSearchDoc, createDocs } from "utils/DocsApi";

import UserNavbar from "components/Common/UserNavbar";
import LawnGraph from "./LawnGraph";
import { openNotification } from "App";

const { Header, Content, Footer, Sider } = Layout;

const MyPage = () => {
  const navigate = useNavigate();
  const [nickname, setNickname] = React.useState();
  const [userDocs, setUserDocs] = React.useState();
  const [info, setInfo] = React.useState();
  const [githubId, setGithubId] = React.useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);

  const {
    token: { colorBgContainer },
  } = theme.useToken();

  React.useEffect(() => {
    getUserProfile().then((result) => {
      setInfo(result);
      //console.log(result);
      setNickname(result.nickname);
      setUserDocs(result.name + " (" + result.number + ")");
      // //console.log(result.name + " " + result.number);
    });
  }, []);

  const makeDefaultDocs = () => {
    createDocs({
      title:
        info.name + (` (${info.number == null ? "PRO" : info.number})` || ""),
      content: `# ${info.name}\n### 생일\n### MBTI\n### 최근 취미\n# 마이 Github\n[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=${githubId}&layout=compact)](https://github.com/anuraghazra/github-readme-stats)\n[![${githubId}'s github stats](https://github-readme-stats.vercel.app/api?username=${githubId}&show_icons=true&theme=default)](https://github.com/${githubId}/)\n[![Solved.ac Profile](http://mazassumnida.wtf/api/generate_badge?boj=${githubId})](https://solved.ac/profile/${githubId})\n# 프로젝트 \n|프로젝트|역할|담당기술|\n|------|---|---|\n| 프로젝트|역할  | 담당기술|\n# 최근 관심사\n# 논란`,
      categories: [info.roll],
      readAuth: 1,
      writeAuth: 2,
    }).then((result) => {
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

  const handleUserDocs = () => {
    const keyword = userDocs;

    //console.log("onSearch", userDocs);
    getSearchDoc(keyword).then((data) => {
      var output = data.data.hits.hits;
      console.log("output", output);
      var seq = 0;
      var newSearched = output.map(function (element) {
        seq = seq + 1;
        return {
          label: element._source.docs_title,
          value: element._source.docs_id,
          isDeleted: element._source.docs_is_deleted,
        };
      });
      console.log(newSearched[0].label === keyword);
      console.log(newSearched[0].isDeleted == false);

      let targetTitle = "";
      let targetDocsId = -1;
      newSearched.forEach((doc) => {
        if (doc.label === keyword && doc.isDeleted === false) {
          targetTitle = doc.label;
          targetDocsId = doc.value;
          return;
        }
      });

      if (targetTitle != "" && targetDocsId != -1) {
        navigate(`/res/content/${targetDocsId}/${targetTitle}`);
      } else {
        // navigate(`/res/list?title=${keyword}`);
        showModal();
      }
    });
  };
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    setIsModalOpen(false);
    makeDefaultDocs();
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };
  return (
    <Layout
      style={{
        minHeight: "100vh",
      }}
    >
      <UserNavbar selectedKey="1"></UserNavbar>
      <Layout style={{ paddingTop: 24 }}>
        <Content
          style={{
            margin: "0 16px",
          }}
        >
          <div
            style={{
              padding: 24,
              minHeight: 360,
              background: colorBgContainer,
            }}
          >
            <Row justify="space-around" align="middle">
              <Col span={1}>
                <Image
                  width={50}
                  src="https://ssafywiki-s3.s3.ap-northeast-2.amazonaws.com/Mon%20Nov%2013%202023%2017%3A27%3A53%20GMT%2B0900%20%28%ED%95%9C%EA%B5%AD%20%ED%91%9C%EC%A4%80%EC%8B%9C%29eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBzc2FmeS5jb20iLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaWF0IjoxNjk5ODYzNTA2LCJleHAiOjE3MDExNTk1MDZ9.M88ZUo5_aAhY3_4i852w-0vWPsSFIzHEnvG_RDQaOtM.jpg"
                />
              </Col>
              <Col span={3}>
                <h1>{nickname}</h1>
              </Col>
              <Tooltip placement="top" title="내 문서">
                <Button
                  type="default"
                  icon={<AuditOutlined />}
                  onClick={handleUserDocs}
                />
              </Tooltip>
              <Col span={17}></Col>
            </Row>

            {/* <Divider orientation="left" orientationMargin="0">
              <b>My Contribute</b>
            </Divider> */}
            <Row justify="space-around" align="middle">
              <Col span={1}></Col>
            </Row>
            <LawnGraph></LawnGraph>
          </div>
        </Content>
        <Footer
          style={{
            textAlign: "center",
          }}
        ></Footer>
      </Layout>
      <Modal
        title="Github Id"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <p>내 문서에 작성할 깃허브 아이디를 입력해주세요!</p>
        <Input
          onChange={(e) => {
            setGithubId(e.target.value);
            // //console.log(e.target.value);
          }}
        ></Input>
      </Modal>
    </Layout>
  );
};
export default MyPage;
