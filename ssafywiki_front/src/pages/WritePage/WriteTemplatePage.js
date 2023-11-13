import React from "react";
import { Layout } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

import { createTemplate } from "utils/TemplateApi";
import { openNotification } from "App";
import WriteTemplate from "components/Write/WriteTemplate";

const { Content } = Layout;
const WritePage = () => {
  const [title, setTitle] = React.useState();
  const [content, setContent] = React.useState();
  const [secret, setSecret] = React.useState("true");
  const [selectedClass, setSelectedClass] = React.useState();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const create = () => {
    // axios로 등록 데이터 넣어줘야함
    //console.log(title, content, secret);
    createTemplate({
      title: title,
      content: content,
      secret: secret,
    }).then((result) => {
      //완료
      //console.log(result);
      openNotification(
        "success",
        "템플릿 생성 완료",
        `'${result.title}' 템플릿이 생성되었습니다.`
      );
      const docsTitle = searchParams.get("title");
      setTitle(docsTitle == undefined ? "문서 제목" : docsTitle); //url에서 가져오기
      navigate(`/wrt?title=${docsTitle}`);
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
            content={content}
            setContent={setContent}
            setTitle={setTitle}
            button="템플릿 생성"
            completeLogic={create}
            secret={secret}
            setSecret={setSecret}
            setSelectedClass={setSelectedClass}
          />
        </div>
      </Content>
    </Layout>
  );
};

export default WritePage;
