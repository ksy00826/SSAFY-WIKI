import React from "react";
import { Button } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";

const DocsList = () => {
  const [title, setTitle] = React.useState("");
  const [searchParams] = useSearchParams();

  const navigate = useNavigate();
  const makeDocs = () => {
    navigate(`/wrt?title=${title}`);
  };

  React.useEffect(() => {
    const title = searchParams.get("title");
    setTitle(title == undefined ? "문서 제목" : title); //url에서 가져오기
  }, []);

  return (
    <div>
      <Button type="primary">DocsList</Button>
      <Button type="default" onClick={makeDocs}>
        '{title}' 문서 생성하기
      </Button>
    </div>
  );
};

export default DocsList;
