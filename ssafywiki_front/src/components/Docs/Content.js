import React from "react";
import { Card, Space, Alert, Tooltip, Modal, Button } from "antd";
import { useParams, useNavigate, useLocation } from "react-router-dom";

import { FormOutlined, WarningTwoTone } from "@ant-design/icons";

import DocsNav from "./DocsNav";

import { getDocsContent, getDocsVersionContent } from "utils/DocsApi";
import { convertDate } from "utils/convertDate";
import MarkdownRenderer from "components/Common/MarkDownRenderer";
import BookMarkBtn from "components/Docs/atom/BookMarkBtn";

import styles from "./Content.module.css";
import { red, yellow } from "utils/ColorPicker";

import { reportDocument } from "utils/ReportApi";
import { openNotification } from "App";

const Content = () => {
  const params = useParams();
  const [content, setContent] = React.useState();
  const [title, setTitle] = React.useState();
  const [modifiedAt, setModifedAt] = React.useState("");
  const [modifyCnt, setModifyCnt] = React.useState(0);
  const [errMsg, setErrMsg] = React.useState("");
  const navigate = useNavigate();

  const location = useLocation();
  const state = location != null ? location.state : null;
  const queryParams =
    location != null
      ? location.search != null
        ? new URLSearchParams(location.search)
        : null
      : null;

  const { confirm, error } = Modal;

  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    if (state == null) {
      getDocsContent(params.docsId)
        .then((response) => {
          console.log(response);
          setContent(response.content);
          setTitle(response.title);
          setModifedAt(convertDate(response.modifiedAt));
        })
        .catch((err) => {
          console.log(err.response.data.message);
          setTitle(params.title);
          setErrMsg(err.response.data.message);
        });
    } else {
      getDocsVersionContent(params.docsId, state.revId).then((response) => {
        console.log(response);
        setContent(response.content);
        setTitle(response.title);
        setModifedAt(convertDate(response.modifiedAt));
      });
    }

    // redis에서 수정중인사람 있는지 가져오기
    setModifyCnt(0);
  }, [params]);

  const handleModify = () => {
    navigate(`/res/edit/${params.docsId}/${title}`);
  };

  const handleReport = () => {
    confirm({
      title: "신고",
      content: "관리자에게 부적절한 문서임을 알립니다.",
      onOk() {
        reportDocument(params.docsId)
          .then(() => {
            openNotification(
              "success",
              "신고 완료",
              `${title}문서가 신고되었습니다.`
            );
          })
          .catch((err) => {
            if (err.response.status == 403) {
              error({
                title: "권한이 없습니다.",
              });
            }
          });
      },
    });

    // 유저
  };

  return (
    <div>
      {errMsg ? (
        <div className="contentTitle">
          <h1 className="title">
            {title}{" "}
            {state != null && (
              <small style={{ fontWeight: "normal" }}>
                (r{queryParams.get("rev")} 판)
              </small>
            )}
          </h1>
          <Alert type="warning" message={errMsg} showIcon />
        </div>
      ) : (
        <div>
          <div className={styles.contentTitle}>
            <h1 className={styles.title}>
              {title}{" "}
              {state != null && (
                <small style={{ fontWeight: "normal" }}>
                  (r{queryParams.get("rev")} 판)
                </small>
              )}
            </h1>
            <div className={styles.bookmark}>
              <BookMarkBtn docsId={params.docsId} />
            </div>
            <div className={styles.nav}>
              <DocsNav current="content" />
            </div>
          </div>
          <Card className={styles.card}>
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
      )}
    </div>
  );
};

export default Content;
