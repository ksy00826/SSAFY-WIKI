import { Button } from "antd";
import { Routes, Route } from "react-router-dom"
import DocumentReport from "components/Admin/DocumentReport";

const AdminPage = () => {
  return (
    <Routes>
      <Route path="/report" element={<DocumentReport />} />
    </Routes>
  );
};

export default AdminPage;
