import { Card } from "antd";
import Discussion from "./Discussion";
const Sidebar = () => {
  return (
    <div>
      <Card>
        <div>최근 수정된 문서</div>
      </Card>
      <Card>
        <div>채팅</div>
        <Discussion></Discussion>
      </Card>
    </div>
  );
};

export default Sidebar;
