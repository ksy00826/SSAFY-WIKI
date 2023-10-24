import cookie from "react-cookies";

export const isLogin = () => {
  // 로그인 되어있는지 확인하는 함수
  let isUserLoggedIn = false;

  if (cookie.load("token") !== undefined) isUserLoggedIn = true;

  return isUserLoggedIn;
};
