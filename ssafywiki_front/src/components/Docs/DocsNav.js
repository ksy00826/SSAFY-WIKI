import { Tabs } from "antd";
import { useNavigate, useParams } from "react-router-dom";

const items = [
  {
    label: "상세정보",
    key: "content",
  },
  {
    label: "기록",
    key: "history",
  },
  {
    label: "권한",
    key: "auth",
  },
];

const Navbar = ({ current }) => {
  const navigate = useNavigate();
  const params = useParams();

  const onChange = (e) => {
    // 같은 곳 누르면 이동 X
    if (e.key === current) return;
    // 원래 문서 위치 가져오기
    // 누른 곳으로 이동
    navigate(`/res/${e}/${params.docsId}/${params.title}`);
  };

  return <Tabs defaultActiveKey={current} items={items} onChange={onChange} />;
};
export default Navbar;
