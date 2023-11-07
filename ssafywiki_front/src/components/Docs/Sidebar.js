import { Card } from "antd";
import Discussion from "./Discussion";
import styles from "./SideBar.module.css";
import RecentDocs from "./RecentDocs";
const Sidebar = () => {
  return (
    <div>
      <Card>
        <div>최근 수정된 문서</div>
        <RecentDocs></RecentDocs>
      </Card>
      <Card bodyStyle={{ padding: 10 }}>
        <div>토론</div>
        <Discussion></Discussion>
      </Card>
    </div>
  );
};

export default Sidebar;
