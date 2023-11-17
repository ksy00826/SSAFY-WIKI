import React, { useState, useParams } from "react";
import UserNavbar from "components/Common/UserNavbar";
import { Layout, theme, Card, Col, Row } from "antd";
import { getBookMarks } from "utils/UserApi";
import DocsItem from "./atom/DocsItem";

const { Header, Content } = Layout;

const BookMark = () => {
  const [renderList, setRenderList] = React.useState([]);

  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getBookMarks().then((response) => {
      //console.log(response);
      setRenderList(
        response.map((item) => (
          <Col key={item.docsId} span={8}>
            <DocsItem
              title={item.title}
              docsId={item.docsId}
              content={`마지막 수정일: ${item.lastModifyTime}`}
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
      <UserNavbar selectedKey="6"></UserNavbar>
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
            <Row gutter={16}>{renderList}</Row>
          </div>
        </Content>
      </Layout>
    </Layout>
  );
};
export default BookMark;
