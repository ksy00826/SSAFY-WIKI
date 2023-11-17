import React, { useState, useParams } from "react";
import UserNavbar from "components/Common/UserNavbar";
import { Layout, theme, Card, Col, Row } from "antd";
import { getContributedChats } from "utils/UserApi";
import DocsItem from "./atom/DocsItem";
const { Header, Content, Footer } = Layout;

const UserChatsPage = () => {
  const [chatc, setchatc] = React.useState([]);

  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getContributedChats().then((response) => {
      setchatc(
        response.map((chat) => (
          <Col key={chat.docsId} span={8}>
            <DocsItem
              docsId={chat.docsId}
              title={chat.content}
              content={chat.createdAt}
            />
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
      <UserNavbar selectedKey="4"></UserNavbar>
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
            <Row gutter={16}>{chatc}</Row>
          </div>
        </Content>
      </Layout>
    </Layout>
  );
};
export default UserChatsPage;
