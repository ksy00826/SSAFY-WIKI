import React from "react";
import { Input, Tabs, Button } from "antd";
import styles from "./WriteForm.module.css";
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
      <div className={styles.Box}>
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
        <div className={styles.TabBox}>
          <Button type="text" className={styles.Button}>굵게</Button>
          <Button type="text" className={styles.Button}>링크</Button>
          <Button type="text" className={styles.Button}>코드</Button>
          <Button type="text" className={styles.Button}>표</Button>
          <Button type="text" className={styles.Button}>문서이동</Button>
          <Button type="text" className={styles.Button}>이미지</Button>
        </div>
      </div>
      {viewType == 1 ? (
        <TextArea
          rows={4}
          // defaultValue={content}
          value={content}
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
