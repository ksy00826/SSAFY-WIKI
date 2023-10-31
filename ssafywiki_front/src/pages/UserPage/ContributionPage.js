import React, { useState,useParams } from 'react';
import UserNavbar from 'components/Common/UserNavbar';
import { Layout, Menu, theme } from 'antd';
import { getContributedDocs } from "utils/UserApi";

const { Header, Content, Footer } = Layout;

const ContributionPage = () => {
  const [content, setContent] = React.useState();
  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getContributedDocs().then((response) => {
      console.log(response);
      setContent(response.content);
    });
  });
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  return (
      <Layout
          style={{
            minHeight: '100vh',
          }}
      >
        <UserNavbar selectedKey='3'></UserNavbar>
        <Layout>
          <Header
              style={{
                padding: 0,
                background: colorBgContainer,
              }}
          />
          <Content
              style={{
                margin: '0 16px',
              }}
          >

            <div
                style={{
                  padding: 24,
                  minHeight: 360,
                  background: colorBgContainer,
                }}
            >
              {content}
            </div>
          </Content>
          <Footer
              style={{
                textAlign: 'center',
              }}
          >
            Ant Design ©2023 Created by Ant UED
          </Footer>
        </Layout>
      </Layout>
  );
};
export default ContributionPage;