import React from "react";
import { Card, Alert } from "antd";
import { useParams, useNavigate } from "react-router-dom";
import { getDocsContent } from "utils/DocsApi";

import WriteForm from "components/Write/WriteDocs";
import ImageUpload from "components/Write/ImageUpload";
import DocsNav from "./DocsNav";
const Edit = () => {
  const params = useParams();
  const [errmsg, setErrMsg] = React.useState("000 이상 수정가능한 문서입니다.");
  const [content, setContent] = React.useState();
  const [title, setTitle] = React.useState();
  const [modifyCnt, setModifyCnt] = React.useState(0);
  const navigate = useNavigate();

  // 처음 랜더링시 내용과 권한 가져오기
  React.useEffect(() => {
    getDocsContent(params.docsId).then((response) => {
      console.log(response);
      setContent(response.content);
      setTitle(response.title);

      // 권한이 있으면 수정가능
      //없으면 에러메세지
    });

    // redis에서 수정중인사람 있는지 가져오기
    setModifyCnt(0);
  }, [params]);

  return (
    <div>
      <h1>{title}</h1>
      <DocsNav current="edit" />

      {errmsg === "" ? <></> : <Alert type="error" message={errmsg} showIcon />}
      <Card>
        <WriteForm content={content} setContent={setContent} />
        <ImageUpload />
      </Card>
    </div>
  );
};

export default Edit;
