import React from "react";
import { Card, Col, Row, Space, Alert } from "antd";
import { useParams } from "react-router-dom";

import { FormOutlined } from "@ant-design/icons";

import DocsNav from "./DocsNav";

import { getDocsContent } from "utils/DocsApi";
import { convertDate } from "utils/convertDate";
import MarkdownRenderer from "components/Common/MarkDownRenderer";

import styles from "./Content.module.css";

const Content = () => {
  const params = useParams();
  const [content, setContent] = React.useState();
  const [title, setTitle] = React.useState();
  const [modifiedAt, setModifedAt] = React.useState("");
  const [errmsg, setErrMsg] = React.useState("");

  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getDocsContent(params.docsId).then((response) => {
      console.log(response);
      setContent(response.content);
      setTitle(response.title);
      setModifedAt(convertDate(response.modifiedAt));
    });
  }, [params]);

  const handleModify = () => {};

  return (
    <div>
      <h1>{title}</h1>
      <DocsNav current="content" />
      <Card
        style={{
          textAlign: "left",
        }}
      >
        <div className={styles.contentHeader}>
          <Space>
            <p>마지막 수정일: {modifiedAt}</p>
            <FormOutlined onClick={handleModify()} />
          </Space>
        </div>
        {errmsg === "" ? <></> : <Alert type="error" message="aaa" showIcon />}

        <MarkdownRenderer content={content} />
      </Card>
    </div>
  );
};

export default Content;
