import React, { useEffect } from "react";
import { Input, Button, Select, Col, Row } from "antd";

import WriteForm from "./WriteForm";

const DocsList = ({
  title,
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

  return (
    <div>
      <h1>{title}</h1>

      <div>
        <Row>
          <Col flex={1}>
            <p>문서 분류 선택</p>
          </Col>
          <Col flex={5}>
            <Select
              mode="tags"
              style={{
                width: "90%",
              }}
              placeholder="분류"
              onChange={handleChange}
              options={selectedClass}
            />
          </Col>
        </Row>
      </div>

      <WriteForm
        content={content}
        setContent={setContent}
        isdisabled={disabled}
      ></WriteForm>

      {!disabled ? (
        <Button type="primary" onClick={completeLogic}>
          {button}
        </Button>
      ) : (
        <></>
      )}
    </div>
  );
};

export default DocsList;
