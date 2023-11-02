import { axiosInstance , axiosSsafygitInstance } from "./AxiosConfig";

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
  try {
    let data = `{"usernameOrEmail":"${id}","password":"aaaaa"}`;
    const response = await axiosSsafygitInstance.post(`/signin`,data);
    return response.data;
  } catch (error) {
    if(error.response.data.message === "아이디가 존재하지 않습니다."
      || error.response.data.message.startsWith("비밀번호가 ")) {
        console.log("noID on API");
      return error.response.data.message;
    }
    return error;
  }
};
