import React, { useState, useParams } from "react";
import UserNavbar from "components/Common/UserNavbar";
import { Layout, theme, Card, Col, Row } from "antd";
import { getContributedDocs } from "utils/UserApi";
import { useNavigate, useLocation, Link } from "react-router-dom";
const { Header, Content, Footer } = Layout;

const ContributionPage = () => {
  const [docc, setdocc] = React.useState();
  const navigate = useNavigate();
  const handleDocument = (id,content) => {
    console.log(id,content);
    navigate(`/res/content/${id}/${content}`);
  };
  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getContributedDocs().then((response) => {
      console.log(response);
      setdocc(
        response.map((doc) => (
          <Col key={doc.createdAt} span={8}>
            <Card
              onClick={() =>  {handleDocument(doc.docsId,doc.title)}}
              title={doc.title}
              bordered={false}
              headStyle={{ backgroundColor: "lightblue", border: 0 }}
              bodyStyle={{ backgroundColor: "white", border: 0 }}
            >
              {doc.createdAt}
            </Card>
          </Col>
        ))
      );
    });
  }, []);

  const {
    token: { colorBgContainer },
  } = theme.useToken();
  
  return (
    <Layout
      style={{
        minHeight: "100vh",
      }}
    >
      <UserNavbar selectedKey="3"></UserNavbar>
      <Layout>
        <Header
          style={{
            padding: 0,
            background: colorBgContainer,
          }}
        />
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
            <Row gutter={16}>{docc}</Row>
          </div>
        </Content>
        <Footer
          style={{
            textAlign: "center",
          }}
        >
          Ant Design ©2023 Created by Ant UED
        </Footer>
      </Layout>
    </Layout>
  );
};
export default ContributionPage;
