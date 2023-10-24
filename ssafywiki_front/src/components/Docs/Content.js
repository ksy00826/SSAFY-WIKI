import { Card } from "antd";

import DocsNav from "./DocsNav";

const Content = () => {
  return (
    <div>
      <h1>문서이름</h1>
      <DocsNav current="content" />
      <Card>
        <div>내용</div>
      </Card>
    </div>
  );
};

export default Content;
