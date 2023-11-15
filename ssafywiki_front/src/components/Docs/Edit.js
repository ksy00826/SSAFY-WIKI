import React from "react";
import { Card, Alert, Input, Modal, FloatButton, Form } from "antd";
import { useParams, useNavigate } from "react-router-dom";
import { getUpdateContent } from "utils/DocsApi";

import WriteForm from "components/Write/WriteDocs";
import ImageUpload from "components/Write/ImageUpload";
import DocsNav from "./DocsNav";

import { openNotification } from "App";
import { updateDocs, getSearchDoc } from "utils/DocsApi";

import styles from "./Content.module.css";
import MarkdownRenderer from "components/Common/MarkDownRenderer";
import { FormOutlined, MenuOutlined } from '@ant-design/icons';
import { createDocs } from "utils/DocsApi";

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
  const [revisionId, setRevisionId] = React.useState(0);

  const [comment, setComment] = React.useState("");

  const [conflict, setConflict] = React.useState(false);
  const [topRevId, setTopRevId] = React.useState();
  const { error } = Modal;

  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [subDocumentTitle, setSubDocumentTitle] = React.useState("");
  const [subcontent, setSubcontent] = React.useState();
  const [isSubtitleModalOpen, setIsSubtitleModalOpen] = React.useState(false);
  const [isSubcontentModalOpen, setIsSubcontentModalOpen] = React.useState(false);
  const [form] = Form.useForm();
  const [documentExists, setDocumentExists] = React.useState(false);


  const getSubtitle = () => {
    setIsSubtitleModalOpen(true);
    form.setFieldsValue({ subtitle: title + '/' });
  }

  const handleSubtitleOk = () => {
    form
      .validateFields()
      .then((values) => {
        getSearchDoc(values.subtitle).then((data) => {
          var output = data.data.hits.hits;
          // console.log("output", output);
          var seq = 0;
          var newSearched = output.map(function (element) {
            seq = seq + 1;
            return {
              label: element._source.docs_title,
              value: element._source.docs_id,
              isDeleted: element._source.docs_is_deleted,
            };
          });
          // console.log(newSearched[0].label === keyword);
          // console.log(newSearched[0].isDeleted == false);

          let targetTitle = "";
          let targetDocsId = -1;
          newSearched.forEach((doc) => {
            if (doc.label === values.subtitle && doc.isDeleted === false) {
              targetTitle = doc.label;
              targetDocsId = doc.isDeleted;
              return;
            }
          });

          if (targetTitle != "" && targetDocsId != -1) {
            // 이미 있는 문서입니다....
            setDocumentExists(true);            
          } else {
            setDocumentExists(false);
            setSubDocumentTitle(values.subtitle);
            setIsSubtitleModalOpen(false);
            form.resetFields();
            setIsSubcontentModalOpen(true);
          }
        })

      })
      .catch((info) => {
        console.log('Validate Failed:', info);
      });
  }

  const handleSubtitleCancel = () => {
    setIsSubtitleModalOpen(false);
    form.resetFields();
  }

  const handleOk = () => {
    createDocs({
      title: subDocumentTitle,
      content: subcontent,
      categories: [],
      readAuth: 1,
      writeAuth: 1,
    }).then((result) => {
      //완료
      //console.log(result);
      openNotification(
        "success",
        "문서작성 완료",
        `${result.title}문서가 생성되었습니다.`
      );
    }).catch((err) => {
      openNotification(
        "error",
        "문서작성 실패",
        ""
      );
    }).finally(setIsModalOpen(false));
  };


  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const buttonSubcontent = () => {
    const selectedText = window.getSelection().toString();
    setSubcontent(selectedText);
    console.log(selectedText);
    setIsSubcontentModalOpen(false);
    setIsModalOpen(true);
  };


  // 처음 랜더링시 내용과 권한 가져오기
  React.useEffect(() => {
    getUpdateContent(params.docsId).then((response) => {
      //console.log(response);
      setContent(response.content);
      setTitle(response.title);
      setClasses(response.categoryList);
      setDocsId(response.docsId);
      setRevisionId(response.revId);

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
    // conflict가 true이면 충돌난 부분 수정을 했는지 content내용 검사가 필요
    if (
      conflict &&
      (content.includes("`<<<<<< HEAD`") ||
        content.includes("`======`") ||
        content.includes("`>>>>>>> PATCH`"))
    ) {
      error({
        title: "수정이 완료되지 않았습니다.",
      });
    } else {
      // axios로 등록 데이터 넣어줘야함
      //console.log(comment);
      updateDocs({
        docsId: id,
        content: content,
        categories: classes,
        revId: revisionId,
        topRevId: topRevId,
        comment: comment,
      })
        .then((result) => {
          //완료
          //console.log(result);
          openNotification(
            "success",
            "문서수정 완료",
            `${result.title}문서가 수정되었습니다.`
          );

          navigate(`/res/content/${result.docsId}/${result.title}`);
        })
        .catch((err) => {
          // //console.log(err.response);
          if (err.response.status == 409) {
            error({
              title: "버전 충돌",
              content: (
                <>
                  <p>{"`<<<<<< HEAD`"}</p>
                  <p>{"`======`"}</p>
                  <p>{"`>>>>>>> PATCH`"}</p>
                  <p>사이의 내용이 충돌났습니다.</p>
                </>
              ),
            });

            setContent(err.response.data.content);
            setConflict(true);
            setTopRevId(err.response.data.topRevId);
          }
        });
    }
  };

  return (
    <div>
      <div className={styles.contentTitle}>
        <h1 className={styles.title}>
          {title}{" "}
          <small style={{ fontWeight: "normal" }}>(문서 수정)</small>
        </h1>
      </div>
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
            onChange={(value) => {
              // //console.log(value);
              setComment(value.target.value);
            }}
          />
        ) : (
          <></>
        )}

        {!disabled ? <ImageUpload /> : <></>}
      </Card>

      {!disabled ? (<FloatButton.Group
        trigger="click"
        style={{
          right: 90,
        }}
        icon={<MenuOutlined />}
        tooltip={<div>메뉴</div>}
      >
        <FloatButton icon={<FormOutlined />} tooltip={<div>하위문서 작성</div>} onClick={getSubtitle} />
      </FloatButton.Group>
      ) : (
        <></>
      )}

      <Modal
        title="하위문서 제목 입력"
        open={isSubtitleModalOpen}
        onOk={handleSubtitleOk}
        onCancel={handleSubtitleCancel}
        okText="확인"
        cancelText="취소"
        destroyOnClose
      >
        <Form form={form} layout="vertical" name="form_in_modal"
          initialValues={{
            subtitle: { title }
          }}>
          <Form.Item
            name="subtitle"
            label="하위문서 제목"
            rules={[
              {
                required: true,
                message: '하위문서 제목을 입력해주세요!',
              },
              () => ({
                validator(_, value) {
                  if (!value || !documentExists) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error('이미 존재하는 문서입니다.'));
                },
              }),
            ]}
          >
            <Input />
          </Form.Item>
        </Form>
      </Modal>

      <Modal
        width={1000}
        title="하위문서 내용선택"
        open={isSubcontentModalOpen}
        okButtonProps={{ onClick: buttonSubcontent }}
        onCancel={() => setIsSubcontentModalOpen(false)}
        okText="확인"
        cancelText="취소"
      >
        <div>하위문서로 작성할 부분을 드래그하고 확인을 눌러주세요.</div><br />
        <TextArea
          rows={4}
          // defaultValue={content}
          value={content}
          autoSize={{
            minRows: 12,
          }}
          readOnly={true}
        />
      </Modal>


      <Modal title="하위문서" width={1000} open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
        <MarkdownRenderer content={subcontent}></MarkdownRenderer>
      </Modal>
    </div>
  );
};

export default Edit;
