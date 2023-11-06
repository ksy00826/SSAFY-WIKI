import React from "react";
import { Card, Space, Alert, Tooltip } from "antd";
import { useParams, useNavigate, useLocation } from "react-router-dom";

import { FormOutlined, WarningTwoTone } from "@ant-design/icons";

import DocsNav from "./DocsNav";

import { getDocsContent } from "utils/DocsApi";
import { convertDate } from "utils/convertDate";
import MarkdownRenderer from "components/Common/MarkDownRenderer";

import styles from "./Content.module.css";
import { red } from "utils/ColorPicker";

const Content = () => {
  const params = useParams();
  const [content, setContent] = React.useState();
  const [title, setTitle] = React.useState();
  const [modifiedAt, setModifedAt] = React.useState("");
  const [modifyCnt, setModifyCnt] = React.useState(0);
  const navigate = useNavigate();
  
  const location = useLocation();
  const state = location != null ? location.state : null;
  const queryParams = location != null ? location.search != null ? new URLSearchParams(location.search) : null : null;


  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    getDocsContent(params.docsId).then((response) => {
      console.log(response);
      setContent(response.content);
      setTitle(response.title);
      setModifedAt(convertDate(response.modifiedAt));
    });

    // redis에서 수정중인사람 있는지 가져오기
    setModifyCnt(0);
  }, [params]);

  const handleModify = () => {
    navigate(`/res/edit/${params.docsId}/${title}`);
  };

  const handleReport = () => {
    console.log(params.docsId);
    // 유저
  };

  return (
    <div>
      <h1>{title} {state != null && <small style={{fontWeight: "normal"}}>(r{queryParams.get("rev")} 판)</small>}</h1>
      <DocsNav current="content" />
      <Card
        style={{
          textAlign: "left",
        }}
      >
        
        <div className={styles.contentHeader}>
          <Space>
            <p>마지막 수정일: {modifiedAt}</p>
            <Tooltip placement="bottom" title="문서 편집">
              <FormOutlined onClick={handleModify} />
            </Tooltip>
            <Tooltip placement="bottom" title="신고하기">
              <WarningTwoTone twoToneColor={red} onClick={handleReport} />
            </Tooltip>
          </Space>
        </div>
        {modifyCnt > 0 ? (
          <Alert
            type="warning"
            message="현재 문서를 수정하는 사용자가 있습니다. 문서 수정에 유의해 주세요."
            showIcon
          />
        ) : (
          <></>
        )}

        <MarkdownRenderer content={content} />
      </Card>
    </div>
  );
};

export default Content;
