import { Card } from "antd";

import DocsNav from "./DocsNav";
const Authority = () => {
  return (
    <div>
      <h1>문서이름</h1>
      <DocsNav current="auth" />
      <Card>
        <div>권한</div>
      </Card>
    </div>
  );
};

export default Authority;
