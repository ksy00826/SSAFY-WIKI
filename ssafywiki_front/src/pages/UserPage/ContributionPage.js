import React, { useState,useParams } from 'react';
import UserNavbar from 'components/Common/UserNavbar';
import { Layout, theme, Card, Col, Row  } from 'antd';
import { getContributedDocs } from "utils/UserApi";

const { Header, Content, Footer } = Layout;

const ContributionPage = () => {
  const [docList, setdocList] = React.useState([]);
  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getContributedDocs().then((response) => {
      setdocList(response.docs);
      
    });
  },[]);

  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const docc =  docList.map((doc) => 
    <Col span={8}>
      <Card title={doc.docs_title} bordered={false}>
        {doc.rev_modified_at}
      </Card>
    </Col>
  );
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
              <Row gutter={16}>
                
                {docc}
          
              </Row>
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