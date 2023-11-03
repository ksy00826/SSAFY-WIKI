import React, { useEffect } from "react";
import { Input, Tabs, Button, Select, Col, Row } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

import MarkdownRenderer from "components/Common/MarkDownRenderer";

import { createDocs } from "utils/DocsApi";

const { TextArea } = Input;

const DocsList = () => {
  const [searchParams] = useSearchParams();
  const title = searchParams.get("title"); //url에서 가져오기

  const [content, setContent] = React.useState(``);
  const [searchClass, setSearchClass] = React.useState([]);
  const [selectedClass, setSelectedClass] = React.useState([]);

  const [viewType, setViewType] = React.useState(1);
  const navigate = useNavigate();

  const onChange = (e) => {
    setContent(e.target.value);
  };

  const s = (e) => {
    setViewType(e);
  };

  const createDoc = () => {
    console.log(title, content);
    console.log(selectedClass);
    //여기서 Select값을 가져오고싶어

    // axios로 등록 데이터 넣어줘야함
    const result = createDocs({
      title: title,
      content: content,
      categories: selectedClass,
      readAuth: 0,
      writeAuth: 0,
    });
    console.log(result);

    //완료하면 생성된 문서로 이동
    let id = result.docsId;
    navigate(`/res/content/${id}`);
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

      <Tabs
        items={[
          {
            label: "RAW",
            key: "1",
          },
          {
            label: "미리보기",
            key: "2",
          },
        ]}
        onChange={(e) => {
          s(e);
        }}
        type="card"
      />

      {viewType == 1 ? (
        <TextArea
          rows={4}
          defaultValue={content}
          autoSize="true"
          onChange={onChange}
        />
      ) : (
        <MarkdownRenderer content={content}></MarkdownRenderer>
      )}

      <Button type="primary" onClick={createDoc}>
        등록
      </Button>
    </div>
  );
};

export default DocsList;
