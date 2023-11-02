import { Button } from "antd";
import { Layout } from "antd";

import SerachTemplete from "components/Write/SearchTemplete";
import WriteForm from "components/Write/WriteForm";

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
      </Content>
    </Layout>
  );
};

export default WritePage;
