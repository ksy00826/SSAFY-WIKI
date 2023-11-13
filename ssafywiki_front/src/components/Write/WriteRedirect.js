import React, { useEffect, useState } from "react";
import { Input, Button, Select, Col, Row, Divider, Card } from "antd";

const WriteRedirect = ({
  title,
  disabled,
  button,
  redirectKeyword,
  setRedirectKeyword,
  completeLogic,
}) => {
  const keywordChange = (value) => {
    setRedirectKeyword(value.target.value);
    //console.log("redirect: ", value.target.value);
  };

  return (
    <div>
      <Row>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
        <Col xs={6} sm={8} md={8} lg={8}>
          <Card title="현재 문서 제목" size="small">
            <p>현재 만드는 문서의 제목입니다.</p>
            <Input placeholder={title} value={title} readOnly={true} />
          </Card>
        </Col>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
        <Col xs={6} sm={8} md={8} lg={8}>
          <Card title="리다이렉트 키워드" size="small">
            <p>바로가기로 이동할 문서의 제목을 입력해주세요</p>
            <Input placeholder={title} onChange={keywordChange} />
          </Card>
        </Col>
        <Col xs={0} sm={1} md={1} lg={2}></Col>
      </Row>

      {!disabled ? (
        <Row>
          <Col xs={8} sm={15} md={15} lg={26}></Col>
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
