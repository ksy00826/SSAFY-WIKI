import { Tabs } from "antd";
import { useNavigate} from "react-router-dom";

const items = [
  {
    label: "정보수정",
    key: "profile",
  },
  {
    label: "기여한 문서",
    key: "contributionPage",
  },
  {
    label: "참여한 채팅",
    key: "chatPage",
  },
  {
    label: "그룹 목록",
    key: "groupPage",
  },
];

const MyPageNavbar = ({ current }) => {
  const navigate = useNavigate();

  const onChange = (e) => {
    // 같은 곳 누르면 이동 X
    if (e.key === current) return;
    // 원래 문서 위치 가져오기
    // 누른 곳으로 이동
    navigate(`/Userpage/${e}`);
  };

  return (
    <Tabs
      defaultActiveKey={current}
      items={items}
      onChange={onChange}
      centered
    />
  );
};
export default MyPageNavbar;
