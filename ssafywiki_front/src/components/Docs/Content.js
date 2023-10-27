import React from "react";
import { Card } from "antd";
import { useParams } from "react-router-dom";

import DocsNav from "./DocsNav";

import { getDocsContent } from "utils/DocsApi";
import MarkdownRenderer from "components/Common/MarkDownRenderer";

const Content = () => {
  const params = useParams();
  const [content, setContent] = React.useState();
  const [title, setTitle] = React.useState();
  const [modifiedAt, setModifedAt] = React.useState();

  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getDocsContent(params.docsId).then((response) => {
      console.log(response);
      setContent(response.content);
      setTitle(response.title);
      setModifedAt(response.modifiedAt);
    });
  }, [params]);

  return (
    <div>
      <h1>{title}</h1>
      <DocsNav current="content" />
      <Card
        style={{
          textAlign: "left",
        }}
      >
        <p>{modifiedAt}</p>
        <MarkdownRenderer content={content} />
      </Card>
    </div>
  );
};

export default Content;
