import { Button } from "antd";
import { useNavigate } from "react-router-dom";
import cookie from "react-cookies";

const Login = () => {
  const navigate = useNavigate();

  const saveCookie = () => {
    const expires = new Date();
    expires.setMinutes(expires.getMinutes() + 60);
    cookie.save("token", "react200", {
      path: "/",
      expires,
      // secure : true,
      // httpOnly : true
    });
    /*
    쿠키제거는 이렇게!!!
    cookie.remove('token', {path : '/'},1000)
    */
  };

  const login = () => {
    // 1. axios로 확인

    // 2. 쿠키에 저장
    saveCookie();

    // 3. redirect
    navigate("/userpage");
  };

  return (
    <div>
      <Button type="primary" onClick={login}>
        login
      </Button>
    </div>
  );
};

export default Login;
