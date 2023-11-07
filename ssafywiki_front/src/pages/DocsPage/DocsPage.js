import { Routes, Route } from "react-router-dom";
import { Layout } from "antd";

import Contents from "components/Docs/Content";
import History from "components/Docs/History";
import Edit from "components/Docs/Edit";
import Authority from "components/Docs/Authority";
import Diff from "components/Docs/Diff";
import Raw from "components/Docs/Raw";

import ListPage from "./ListPage";
import Sidebar from "components/Docs/Sidebar";

const { Sider, Content } = Layout;

const UserPage = () => {
  return (
    <Layout hasSider>
      <Content
        style={{
          backgroundColor: "white",
        }}
      >
        <Routes>
          <Route path="/list" element={<ListPage />} />
          <Route path="/history/:docsId/:title" element={<History />} />
          <Route path="/edit/:docsId/:title" element={<Edit />} />
          <Route path="/auth/:docsId/:title" element={<Authority />} />
          <Route path="/content/:docsId/:title" element={<Contents />} />
          <Route path="/diff/*" element={<Diff />} />
          <Route path="/raw/:title*" element={<Raw />} />
        </Routes>
      </Content>

      <Sider
        style={{
          backgroundColor: "white",
        }}
      >
        <Sidebar />
      </Sider>
    </Layout>
  );
};

export default UserPage;
