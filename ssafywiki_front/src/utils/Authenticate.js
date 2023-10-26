import cookie from "react-cookies";

// 로그인 되어있는지 확인하는 함수
export const isLogin = () => {
  let isUserLoggedIn = false;

  if (cookie.load("token") !== undefined) isUserLoggedIn = true;

  return isUserLoggedIn;
};

// 로그인 로직
export const login = (email, password) => {
  // 1. axios로 확인

  //결과에 따라
  // return { state: 201, msg: "비밀번호가 일치하지 않습니다." };

  // 2. 쿠키에 저장
  saveCookie();
  return { state: 200 };
};

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

// 로그인 아이디 기억하기 쿠키에 저장
export const rememberEmail = (email) => {
  const expires = new Date();
  expires.setFullYear(expires.getFullYear() + 1);
  cookie.save("remember", email, {
    path: "/member/login",
    expires,
    // secure : true,
    // httpOnly : true
  });
};

// 로그인시 저장된 아이디 불러오기
export const getRememberEmail = () => {
  return cookie.load("remember");
};

// 로그인시 저장된 아이디 삭제하기
export const removeRememberEmail = () => {
  return cookie.remove("remember", { path: "/member/login" });
};
