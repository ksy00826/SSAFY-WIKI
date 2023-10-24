import { Routes, Route } from "react-router-dom";
import { Layout } from "antd";

import DocsContentPage from "./DocsContentPage";
import ListPage from "./ListPage";
import Sidebar from "components/Docs/Sidebar";

const { Sider, Content } = Layout;

const UserPage = () => {
  return (
    <Layout hasSider>
      <Content>
        <Routes>
          <Route path="/list" element={<ListPage />} />
          <Route path="/*" element={<DocsContentPage />} />
        </Routes>
      </Content>
      <Sider
        style={{
          backgroundColor: "lightgray",
        }}
      >
        <Sidebar />
      </Sider>
    </Layout>
  );
};

export default UserPage;
