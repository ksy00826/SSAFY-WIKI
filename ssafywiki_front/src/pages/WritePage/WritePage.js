import React from "react";
import { Button } from "antd";
import { Layout } from "antd";

import SerachTemplete from "components/Write/SearchTemplete";
import WriteForm from "components/Write/WriteDocs";

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
        )}
      </Content>
    </Layout>
  );
};

export default WritePage;
