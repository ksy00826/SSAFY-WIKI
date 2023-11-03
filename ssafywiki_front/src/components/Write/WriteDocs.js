import React, { useEffect } from "react";
import { Input, Button, Select, Col, Row } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

import WriteForm from "./WriteForm";

import { createDocs } from "utils/DocsApi";
import { openNotification } from "App";

const { TextArea } = Input;

const DocsList = ({ content, setContent }) => {
  const [searchParams] = useSearchParams();
  const title = searchParams.get("title"); //url에서 가져오기

  const [searchClass, setSearchClass] = React.useState([]);
  const [selectedClass, setSelectedClass] = React.useState([]);

  const [viewType, setViewType] = React.useState(1);

  const navigate = useNavigate();

  const create = () => {
    // axios로 등록 데이터 넣어줘야함
    createDocs({
      title: title,
      content: content,
    }).then((result) => {
      //완료
      openNotification(
        "success",
        "문서작성 완료",
        `${title}문서가 생성되었습니다.`
      );

      let id = 1;
      // navigate(`/res/content/${id}/${title}`);
      navigate("/");
    });
  };

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
              options={searchClass}
            />
          </Col>
        </Row>
      </div>

      <WriteForm content={content} setContent={setContent}></WriteForm>

      <Button type="primary" onClick={create}>
        등록
      </Button>
    </div>
  );
};

export default DocsList;
