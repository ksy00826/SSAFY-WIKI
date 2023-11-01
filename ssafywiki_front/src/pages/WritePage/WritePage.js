import { Button } from "antd";
import { Layout } from "antd";

import SerachTemplete from "components/Write/SearchTemplete";
import WriteForm from "components/Write/WriteForm";
import ImageUpload from "components/Write/ImageUpload";

const { Sider, Content } = Layout;
const WritePage = () => {
  return (
    <Layout hasSider>
      <Sider
        style={{
          backgroundColor: "white",
        }}
      >
        <SerachTemplete />
      </Sider>
      <Content
        style={{
          backgroundColor: "white",
          textAlign: "left",
        }}
      >
        <WriteForm />
        <ImageUpload />
      </Content>
    </Layout>
  );
};

export default WritePage;
