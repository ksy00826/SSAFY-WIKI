import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import { FloatButton } from "antd";
import { ArrowDownOutlined } from "@ant-design/icons";

const ScrollToDown = () => {
  const location = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [location]);

  return (
    <>
      <FloatButton
        onClick={() =>
          window.scrollTo({
            top: document.body.scrollHeight,
            left: 0,
            behavior: "smooth", // 부드러운 스크롤 효과를 위한 설정
          })
        }
        icon={<ArrowDownOutlined />}
      />
    </>
  );
};

export default ScrollToDown;
