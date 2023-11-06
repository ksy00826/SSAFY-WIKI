import React, { useEffect } from "react";
import { Input, Button, Select, Col, Row } from "antd";

import WriteForm from "./WriteForm";

const WriteTemplate = ({
  title,
  setTitle,
  content,
  setContent,
  disabled,
  button,
  completeLogic,
  selectedClass,
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

  return (
    <div>
      <Row>
        <Input placeholder="템플릿 제목" onChange={titleChange} />
      </Row>
      <div>
        <Row>
          <Col flex={1}>
            <p>템플릿 분류 선택</p>
          </Col>
          <Col flex={5}>
            <Select
              mode="tags"
              style={{
                width: "90%",
              }}
              placeholder="분류"
              onChange={handleChange}
              options={[]}
            />
          </Col>
        </Row>
      </div>
      <WriteForm
        content={content}
        setContent={setContent}
        isdisabled={disabled}
      ></WriteForm>
      <Row>
        <Col flex="8"></Col>
        <Col>
          {!disabled ? (
            <Button type="primary" onClick={completeLogic}>
              {button}
            </Button>
          ) : (
            <></>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WriteTemplate;
