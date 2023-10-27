import { Card } from "antd";

import DocsNav from "./DocsNav";
const History = () => {
  return (
    <div>
      <h1>문서이름</h1>
      <DocsNav current="history" />
      <Card>
        <div>History</div>
      </Card>
    </div>
  );
};

export default History;
