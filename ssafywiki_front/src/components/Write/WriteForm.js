import React from "react";
import { Input, Tabs } from "antd";

import MarkdownRenderer from "components/Common/MarkDownRenderer";

const { TextArea } = Input;

const DocsList = () => {
  const [content, setContent] = React.useState(``);
  const title = "문서작성"; //url에서 가져오기
  const [viewType, setViewType] = React.useState(1);

  const onChange = (e) => {
    setContent(e.target.value);
  };

  const s = (e) => {
    console.log(e);
    setViewType(e);
  };

  return (
    <div>
      <h1>{title}</h1>

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
    </div>
  );
};

export default DocsList;
