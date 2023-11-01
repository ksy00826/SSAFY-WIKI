import { axiosInstance } from "./AxiosConfig";

export const getUserProfile = async (id) => {
  // try {
  //   const response = await axiosInstance.get(`/api/user`);
  //   return response.data;
  // } catch (error) {
  //   throw error;
  // }
  return {
    profile: {
      user_id: "id1",
      user_email: "email1@naver.com",
      user_password: "12345!k",
      user_name: "name1",
      user_nickname: "nickname1",
      user_number: "user_number1",
      user_campus: "user_campus1",
      user_created_at: "2020-10-16",
      user_modified_at: "2022-12-19",
    },
  };
};

export const editUserProfile = async (nickname, passwordOld, passwordNew) => {
  try {
    const response = await axiosInstance.post(`/api/user`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getContributedDocs = async (id) => {
  // try {
  //   const response = await axiosInstance.get(`/api/mypage/writedocs`);
  //   return response.data;
  // } catch (error) {
  //   throw error;
  //
  return {
    docs: [
      {
        docs_id: "1",
        docs_title: "hello1",
        docs_is_redirected: false,
        docs_is_deleted: false,
        rev_modified_at: "2020-10-16",
      },
      {
        docs_id: "2",
        docs_title: "hello2",
        docs_is_redirected: false,
        docs_is_deleted: false,
        rev_modified_at: "2020-10-16",
      },
      {
        docs_id: "3",
        docs_title: "hello3",
        docs_is_redirected: false,
        docs_is_deleted: false,
        rev_modified_at: "2020-10-16",
      },
    ],
  };
};

export const getContributedChats = async (id) => {
  // try {
  //   const response = await axiosInstance.get(`/api/mypage/writechats`);
  //   return response.data;
  // } catch (error) {
  //   throw error;
  // }
  return {
    docs: [
      {
        docs_title: "hello1",
        discuss_created_at: "2021-10-17",
        discuss_content: "안녕하쇼",
      },
      {
        docs_title: "hello2",
        discuss_created_at: "2021-10-18",
        discuss_content: "그건 좀 아닌듯",
      },
      {
        docs_title: "hello3",
        discuss_created_at: "2021-10-19",
        discuss_content: "솔직히 이게 맞지 않나...",
      },
    ],
  };
};

export const getStarDocs = async (id) => {
  try {
    const response = await axiosInstance.get(`/api/mypage/stardocs`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const checkSSAFYEmail = async (id) => {
  var data = '{"usernameOrEmail":"kss4037@gmail.com","password":"kwon5147k!"}';

  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;

  xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
      console.log(this.responseText);
    }
  });

  xhr.open("POST", "https://project.ssafy.com/ssafy/api/auth/signin");
  xhr.setRequestHeader("Accept", "application/json, text/plain, */*");
  xhr.setRequestHeader(
    "Accept-Language",
    "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6"
  );
  xhr.setRequestHeader("Authorization", "");
  xhr.setRequestHeader("Connection", "keep-alive");
  xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  // WARNING: Cookies will be stripped away by the browser before sending the request.
  xhr.setRequestHeader("Cookie", "SCOUTER=z3rk4dv32gumkl");
  xhr.setRequestHeader("Origin", "https://project.ssafy.com");
  xhr.setRequestHeader(
    "Referer",
    "https://project.ssafy.com/login?returnPath=%2Fhome"
  );
  xhr.setRequestHeader("Sec-Fetch-Dest", "empty");
  xhr.setRequestHeader("Sec-Fetch-Mode", "cors");
  xhr.setRequestHeader("Sec-Fetch-Site", "same-origin");
  xhr.setRequestHeader(
    "User-Agent",
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36"
  );
  xhr.setRequestHeader(
    "sec-ch-ua",
    '"Chromium";v="118", "Google Chrome";v="118", "Not=A?Brand";v="99"'
  );
  xhr.setRequestHeader("sec-ch-ua-mobile", "?0");
  xhr.setRequestHeader("sec-ch-ua-platform", '"Windows"');

  return xhr.send(data);
};
