import { Button } from "antd";
import { useNavigate } from "react-router-dom";

const DocsList = () => {
  const navigate = useNavigate();
  const makeDocs = () => {
    let aa = "aaa";
    navigate(`/wrt?title=${aa}`);
  };
  return (
    <div>
      <Button type="primary">DocsList</Button>
      <Button type="default" onClick={makeDocs}>
        '---'로 문서 생성하기
      </Button>
    </div>
  );
};

export default DocsList;
