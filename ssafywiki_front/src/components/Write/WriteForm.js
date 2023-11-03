import React from "react";
import { Input, Tabs } from "antd";

import MarkdownRenderer from "components/Common/MarkDownRenderer";

const { TextArea } = Input;

const WriteForm = ({ content, setContent }) => {
  const [viewType, setViewType] = React.useState(1);

  const onChange = (e) => {
    setContent(e.target.value);
  };

  const changeViewType = (e) => {
    setViewType(e);
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
