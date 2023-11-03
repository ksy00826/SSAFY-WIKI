import React from "react";
import { Button } from "antd";
import { Layout } from "antd";

import SerachTemplete from "components/Write/SearchTemplete";
import WriteForm from "components/Write/WriteDocs";
import WriteForm from "components/Write/WriteForm";
import ImageUpload from "components/Write/ImageUpload";

const { Content } = Layout;
const WritePage = () => {
  const [step, setStep] = React.useState(1);
  const [content, setContent] = React.useState();
  const next = (content) => {
    setContent(content);
    setStep(step + 1);
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
        {step == 1 ? (
          <SerachTemplete next={next} />
        ) : (
          <WriteForm content={content} setContent={setContent} />
          <ImageUpload />
        )}
      </Content>
    </Layout>
  );
};

export default WritePage;
