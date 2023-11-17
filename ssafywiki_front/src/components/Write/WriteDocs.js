import React, { useEffect, useState } from "react";
import { Input, Button, Select, Col, Row, Divider } from "antd";

import WriteForm from "./WriteForm";

const DocsList = ({
  title,
  // setTitle,
  content,
  setContent,
  disabled,
  button,
  completeLogic,
  selectedClass,
  setSelectedClass,
}) => {
  const [isError, setIsError] = useState(false);
  const [viewType, setViewType] = useState(1);
  const handleChange = (value) => {
    //console.log(`selected ${value}`);

    setSelectedClass(value);
  };

  // const titleChange = (value) => {
  //   setTitle(value.target.value);
  //   //console.log(value.target.value);
  // };

  return (
    <div>
      <Row>
        <Divider orientation="left" orientationMargin="0">
          <b>문서 제목</b>
        </Divider>
        <Input placeholder={title} value={title} readOnly={true} />
      </Row>

      <div>
        <Divider orientation="left" orientationMargin="0">
          <b>문서 분류</b>
        </Divider>
        <Row>
          <Col flex={5}>
            <Select
              disabled={disabled ? true : false}
              mode="tags"
              style={{
                width: "100%",
              }}
              defaultValue={selectedClass}
              placeholder="인물, CS, .."
              onChange={handleChange}
              options={[]}
            />
          </Col>
        </Row>
      </div>

      <Divider orientation="left" orientationMargin="0">
        <b>문서 내용</b>
      </Divider>
      <WriteForm
        content={content}
        setContent={setContent}
        isdisabled={disabled}
        setIsError={setIsError}
        viewType={viewType}
        setViewType={setViewType}
      ></WriteForm>

      {!disabled && !isError && viewType == 2 ? (
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

export default DocsList;
