import React from "react";
import { Button } from "antd";
import { Layout } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

import { createDocs } from "utils/DocsApi";
import { createTemplate } from "utils/TemplateApi";
import { openNotification } from "App";
import WriteTemplate from "components/Write/WriteTemplate";

const { Content } = Layout;
const WritePage = () => {
  const [title, setTitle] = React.useState();
  const [content, setContent] = React.useState();
  const [selectedClass, setSelectedClass] = React.useState([]);
  const [readAuth, setReadAuth] = React.useState(0);
  const [writeAuth, setWriteAuth] = React.useState(0);

  const navigate = useNavigate();

  const create = () => {
    // axios로 등록 데이터 넣어줘야함
    console.log(title, content, selectedClass, readAuth, writeAuth);
    createTemplate({
      title: title,
      content: content,
      categories: selectedClass,
      readAuth: readAuth,
      writeAuth: writeAuth,
    }).then((result) => {
      //완료
      console.log(result);
      openNotification(
        "success",
        "템플릿 생성 완료",
        `${result.title}템플릿이 생성되었습니다.`
      );

      navigate(`/wrt`);
    });
  };
  return (
    <Layout>
      <Content
        style={{
          backgroundColor: "white",
          textAlign: "left",
          padding: "2%",
        }}
      >
        <div>
          <WriteTemplate
            title=""
            setTitle={setTitle}
            button="템플릿 생성"
            completeLogic={create}
            selectedClass={selectedClass}
            setSelectedClass={setSelectedClass}
            setContent={setContent}
          />
        </div>
      </Content>
    </Layout>
  );
};

export default WritePage;
