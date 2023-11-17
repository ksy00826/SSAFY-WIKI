import cookie from "react-cookies";
import {
  axiosInstance,
  axiosInstanceNoTimeout,
  axiosInstanceWithLogin,
} from "./AxiosConfig";

// 로그인 되어있는지 확인하는 함수
export const isLogin = () => {
  let isUserLoggedIn = false;

  if (cookie.load("token") !== undefined) isUserLoggedIn = true;

  return isUserLoggedIn;
};

// 쿠키에 저장된 토큰 가져오는 함수
export const getToken = () => {
  return cookie.load("token");
};

// 회원가입 로직
export const signup = async (info) => {
  try {
    const response = await axiosInstance.post(`/api/members/signup`, info);

    //console.log(response.data);
    return response.data;
  } catch (error) {
    throw error;
  }
};

// 로그인 로직
export const login = async (email, password) => {
  // 1. axios로 확인
  try {
    const response = await axiosInstance.post(`/api/members/login`, {
      email: email,
      password: password,
    });

    // 2. 성공시 쿠키에 저장
    //console.log(response.data);
    saveCookie(response.data.access_token);

    return { state: 200 };
  } catch (error) {
    throw error;
  }
  //결과에 따라
  // return { state: 201, msg: "비밀번호가 일치하지 않습니다." };
};

const saveCookie = (toekn) => {
  const expires = new Date();
  expires.setMinutes(expires.getMinutes() + 60);
  cookie.save("token", toekn, {
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

// 로그아웃
export const logout = () => {
  cookie.remove("token", { path: "/" }, 1000);
};

// 회원가입시 이메일 전송
export const sendEmail = async (email, role) => {
  try {
    const response = await axiosInstanceNoTimeout
      .post(`/api/members/email`, {
        email: email,
        role: role,
        authCode: "null",
      })
      .then((data) => {
        //console.log(data);
        return data;
      });
  } catch (error) {
    throw error;
  }
};

export const authEmail = async (email, role, authCode) => {
  try {
    const response = await axiosInstanceNoTimeout
      .post(`/api/members/email/auth`, {
        email: email,
        role: role,
        authCode: authCode,
      })
      .then((data) => {
        //console.log(data);
        return data;
      });
  } catch (error) {
    throw error;
  }
};

export const isAdmin = async () => {
  try {
    const response = await axiosInstanceWithLogin.get(`/api/user/admin`);
    return response.data;
  } catch (error) {
    throw error;
  }
};
