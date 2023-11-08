import React, { useEffect } from "react";
import { Input, Button, Select, Col, Row, Divider, Radio } from "antd";

import WriteForm from "./WriteForm";

const WriteTemplate = ({
  title,
  setTitle,
  content,
  setContent,
  disabled,
  button,
  completeLogic,
  secret,
  setSecret,
  setSelectedClass,
}) => {
  const handleChange = (value) => {
    console.log(`selected ${value}`);
    setSelectedClass(value);
  };
  const titleChange = (value) => {
    setTitle(value.target.value);
    console.log(value.target.value);
  };
  const changeSecret = (e) => {
    console.log(e.target.value);
    setSecret(e.target.value);
  };

  return (
    <div>
      <Row>
        <Divider orientation="left" orientationMargin="0">
          <b>템플릿 제목</b>
        </Divider>
        <Input placeholder="템플릿 제목" onChange={titleChange} />
      </Row>
      <div>
        <Row>
          <Divider orientation="left" orientationMargin="0">
            <b>템플릿 분류</b>
          </Divider>
          <Col flex={5}>
            <Select
              mode="tags"
              style={{
                width: "90%",
              }}
              placeholder="인물, CS.."
              onChange={handleChange}
              options={[]}
            />
          </Col>
        </Row>
      </div>
      <Row>
        <Divider orientation="left" orientationMargin="0">
          <b>공개 범위 설정</b>
        </Divider>
        <Radio.Group defaultValue={secret} onChange={changeSecret}>
          <Radio.Button value="true">비공개</Radio.Button>
          <Radio.Button value="false">공개</Radio.Button>
        </Radio.Group>
      </Row>
      <Divider orientation="left" orientationMargin="0">
        <b>템플릿 내용</b>
      </Divider>
      <WriteForm
        content={content}
        setContent={setContent}
        isdisabled={disabled}
      ></WriteForm>
      <Row>
        <Col flex="8"></Col>
        <Col>
          {!disabled ? (
            <div>
              <br />
              <Button type="primary" onClick={completeLogic}>
                {button}
              </Button>
            </div>
          ) : (
            <></>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WriteTemplate;
