import React from "react";
import { Card, Alert, Input } from "antd";
import { useParams, useNavigate } from "react-router-dom";
import { getUpdateContent } from "utils/DocsApi";

import WriteForm from "components/Write/WriteDocs";
import ImageUpload from "components/Write/ImageUpload";
import DocsNav from "./DocsNav";

import { openNotification } from "App";
import { updateDocs } from "utils/DocsApi";

const { TextArea } = Input;
const Edit = () => {
  const params = useParams();
  const [loading, setLoading] = React.useState(false);
  const [id, setDocsId] = React.useState();
  const [errmsg, setErrMsg] = React.useState("");
  const [content, setContent] = React.useState();
  const [title, setTitle] = React.useState();
  const [classes, setClasses] = React.useState([]);
  const [modifyCnt, setModifyCnt] = React.useState(0);
  const navigate = useNavigate();
  const [disabled, setDisabled] = React.useState("");

  const [comment, setComment] = React.useState("");

  // 처음 랜더링시 내용과 권한 가져오기
  React.useEffect(() => {
    getUpdateContent(params.docsId).then((response) => {
      console.log(response);
      setContent(response.content);
      setTitle(response.title);
      setClasses(response.categoryList);
      setDocsId(response.docsId);

      setLoading(true);

      // 권한이 있으면 수정가능
      //없으면 에러메세지
      if (!response.canUpdate) {
        setDisabled("disabled");
        setErrMsg("해당 문서의 수정 권한이 없습니다.");
      }
    });

    // redis에서 수정중인사람 있는지 가져오기
    setModifyCnt(0);
  }, [params]);

  const handlemodify = () => {
    // axios로 등록 데이터 넣어줘야함
    updateDocs({
      docsId: id,
      content: content,
      categories: classes,
      comment: comment,
    }).then((result) => {
      //완료
      console.log(result);
      openNotification(
        "success",
        "문서수정 완료",
        `${result.title}문서가 수정되었습니다.`
      );

      navigate(`/res/content/${result.docsId}/${result.title}`);
    });
  };

  return (
    <div>
      <Card>
        {errmsg === "" ? (
          <></>
        ) : (
          <Alert type="error" message={errmsg} showIcon />
        )}
        {loading ? (
          <WriteForm
            title={title}
            content={content}
            setContent={setContent}
            disabled={disabled}
            button="수정"
            completeLogic={handlemodify}
            selectedClass={classes}
            setSelectedClass={setClasses}
          />
        ) : (
          <></>
        )}

        {!disabled ? (
          <TextArea
            placeholder="comment"
            rows={4}
            defaultValue={comment}
            autoSize={{
              minRows: 4,
            }}
            onChange={(value) => setComment(value)}
          />
        ) : (
          <></>
        )}

        {!disabled ? <ImageUpload /> : <></>}
      </Card>
    </div>
  );
};

export default Edit;
