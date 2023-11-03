import React, { useEffect } from "react";
import { Input, Tabs, Button, Select, Col, Row } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

import { createDocs } from "utils/DocsApi";

import MarkdownRenderer from "components/Common/MarkDownRenderer";

const { TextArea } = Input;

const WriteForm = ({ content, setContent }) => {
  const [searchParams] = useSearchParams();
  const title = searchParams.get("title"); //url에서 가져오기

  // const [content, setContent] = React.useState(``);
  const [searchClass, setSearchClass] = React.useState([]);
  const [selectedClass, setSelectedClass] = React.useState([]);

  const [viewType, setViewType] = React.useState(1);
  const navigate = useNavigate();

  const onChange = (e) => {
    setContent(e.target.value);
  };

  const changeViewType = (e) => {
    setViewType(e);
  };

  const handleChange = (value) => {
    console.log(`selected ${value}`);
    setSelectedClass(value);
  };

  return (
    <div>
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
          changeViewType(e);
        }}
        type="card"
      />

      {viewType == 1 ? (
        <TextArea
          rows={4}
          defaultValue={content}
          autoSize={{
            minRows: 12,
          }}
          onChange={onChange}
        />
      ) : (
        <MarkdownRenderer content={content}></MarkdownRenderer>
      )}
    </div>
  );
};

export default WriteForm;
