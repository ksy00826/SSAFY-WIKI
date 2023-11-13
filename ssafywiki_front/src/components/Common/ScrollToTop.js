import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import { FloatButton } from "antd";
import { ArrowUpOutlined } from "@ant-design/icons";

const ScrollToTop = () => {
  const location = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [location]);

  return (
    <>
      <FloatButton
        onClick={() =>
          window.scrollTo({
            top: 0,
            left: 0,
            behavior: "smooth", // 부드러운 스크롤 효과를 위한 설정
          })
        }
        icon={<ArrowUpOutlined />}
      />
    </>
  );
};

export default ScrollToTop;
