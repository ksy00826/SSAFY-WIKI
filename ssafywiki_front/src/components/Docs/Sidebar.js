import { Card } from "antd";
import Discussion from "./Discussion";
import styles from "./SideBar.module.css";
import RecentDocs from "./RecentDocs";
const Sidebar = () => {
  return (
    <div>
      <div className={styles.Card}>
        <Card bodyStyle={{ padding: 0 }}>
          <h3>최근 수정된 문서</h3>
          <RecentDocs></RecentDocs>
        </Card>
      </div>
      <div className={styles.Card}>
        <Card bodyStyle={{ padding: 10 }}>
          <h3 className={styles.RecentTitle}>토론</h3>
          <Discussion></Discussion>
        </Card>
      </div>
    </div>
  );
};

export default Sidebar;
