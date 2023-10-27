import { Card } from "antd";

import DocsNav from "./DocsNav";
const Edit = () => {
  return (
    <div>
      <h1>문서이름</h1>
      <DocsNav current="edit" />
      <Card>
        <div>Edit</div>
      </Card>
    </div>
  );
};

export default Edit;
