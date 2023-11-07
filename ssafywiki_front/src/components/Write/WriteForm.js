import React from "react";
import { Input, Tabs } from "antd";

import MarkdownRenderer from "components/Common/MarkDownRenderer";

const { TextArea } = Input;

const WriteForm = ({ content, setContent, isdisabled }) => {
  const [viewType, setViewType] = React.useState(1);

  const changeContent = (e) => {
    console.log(e.target.value);
    setContent(e.target.value);
  };

  const changeViewType = (e) => {
    setViewType(e);
  };

  React.useEffect(() => {
    console.log(content);
  }, [content]);
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
          onChange={changeContent}
          readOnly={isdisabled}
        />
      ) : (
        <MarkdownRenderer content={content}></MarkdownRenderer>
      )}
    </div>
  );
};

export default WriteForm;
