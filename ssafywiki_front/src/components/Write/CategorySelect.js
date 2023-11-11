import React, { useEffect, useState } from "react";
import { Input, Button, Select, Col, Row, Divider, Space, Card } from "antd";

const CategorySelect = ({ next, setReadAuth, setWriteAuth, setIsRedirect }) => {
  const clickSsafyOpen = () => {
    setReadAuth(1);
    setWriteAuth(2);
    next();
  };
  const clickSsafyClose = () => {
    setReadAuth(2);
    setWriteAuth(2);
    next();
  };
  const clickCS = () => {
    setReadAuth(1);
    setWriteAuth(1);
    next();
  };
  const clickRedirect = () => {
    setReadAuth(1);
    setWriteAuth(2);
    setIsRedirect(true);
    next();
  };

  // 버튼에 적용할 스타일 객체
  const buttonStyle = {
    width: "100%", // 버튼의 너비를 100%로 설정하여 블록 레벨 버튼으로 만듭니다.
    height: "100%",
  };

  return (
    <Space direction="vertical" size="middle" style={{ display: "flex" }}>
      <Row>
        <Col flex={8}>
          <h2>문서 분류 선택</h2>
        </Col>
      </Row>
      <Row gutter={24}>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
        <Col xs={6} sm={8} md={8} lg={10}>
          <Button ghost block onClick={clickSsafyOpen} style={buttonStyle}>
            <Card title="SSAFY 공개 문서" size="small">
              <p>SSAFY에 대한 흥미로운 정보!</p>
              <p>외부인 열람이 가능해요</p>
            </Card>
          </Button>
        </Col>
        <Col xs={6} sm={8} md={8} lg={10}>
          <Button ghost block onClick={clickSsafyClose} style={buttonStyle}>
            <Card title="SSAFY 대외비 문서" size="small">
              <p>SSAFY에 대한 상세한 정보!</p>
              <p>외부인 열람이 불가능해요</p>
            </Card>
          </Button>
        </Col>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
      </Row>
      <Row gutter={24}>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
        <Col xs={6} sm={8} md={8} lg={10}>
          <Button ghost block onClick={clickCS} style={buttonStyle}>
            <Card title="일반 문서" size="small">
              <p>여러 목적으로 작성되는 문서!</p>
              <p>인물, CS 등 본인이 원하는 정보를 작성할 수 있어요</p>
            </Card>
          </Button>
        </Col>
        <Col xs={6} sm={8} md={8} lg={10}>
          <Button ghost block onClick={clickRedirect} style={buttonStyle}>
            <Card title="리다이렉트 문서" size="small">
              <p>기존 문서로 연결되는 문서!</p>
              <p>키워드를 등록하여 기존 문서로 바로 이동할 수 있어요</p>
            </Card>
          </Button>
        </Col>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
      </Row>
    </Space>
  );
};

export default CategorySelect;
