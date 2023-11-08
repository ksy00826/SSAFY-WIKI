import React, { useEffect, useState } from "react";
import { Input, Button, Select, Col, Row, Divider, Card } from "antd";

const WriteRedirect = ({
  title,
  setTitle,
  disabled,
  button,
  redirectKeyword,
  setRedirectKeyword,
  completeLogic,
}) => {
  const keywordChange = (value) => {
    setRedirectKeyword(value.target.value);
    console.log("redirect: ", value.target.value);
  };

  const titleChange = (value) => {
    setTitle(value.target.value);
    console.log("origin: ", value.target.value);
  };

  return (
    <div>
      <Row>
        <Col flex={5}></Col>
        <Col flex={5}>
          <Card title="현재 문서 제목" size="small">
            <p>현재 만드는 문서의 제목을 입력해주세요</p>
            <Input placeholder={title} onChange={titleChange} />
          </Card>
        </Col>
        <Col flex={5}></Col>
        <Col flex={5}>
          <Card title="리다이렉트 키워드" size="small">
            <p>바로가기로 이동할 문서의 제목을 입력해주세요</p>
            <Input placeholder={title} onChange={keywordChange} />
          </Card>
        </Col>
        <Col flex={5}></Col>
      </Row>

      {!disabled ? (
        <Row>
          <Col flex={8}></Col>
          <Col>
            <br></br>
            <Button type="primary" onClick={completeLogic}>
              {button}
            </Button>
          </Col>
        </Row>
      ) : (
        <></>
      )}
    </div>
  );
};

export default WriteRedirect;
