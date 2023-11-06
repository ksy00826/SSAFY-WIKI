import React, { useEffect } from "react";
import { Input, Tabs, Button, Select, Col, Row } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

import MarkdownRenderer from "components/Common/MarkDownRenderer";

const { TextArea } = Input;

const WriteForm = ({ content, setContent, isdisabled }) => {
  const [searchParams] = useSearchParams();
  const title = searchParams.get("title"); //url에서 가져오기

  const [viewType, setViewType] = React.useState(1);

  const onChange = (e) => {
    setContent(e.target.value);
  };

  const changeViewType = (e) => {
    setViewType(e);
  };

  return (
    <div
      style={{
        textAlign: "left",
      }}
    >
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
          readOnly={isdisabled}
        />
      ) : (
        <MarkdownRenderer content={content}></MarkdownRenderer>
      )}
    </div>
  );
};

export default WriteForm;
