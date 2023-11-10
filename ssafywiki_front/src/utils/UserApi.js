import {
  axiosInstance,
  axiosInstanceWithLogin,
  axiosSsafygitInstance,
} from "./AxiosConfig";

export const getUserProfile = async () => {
  try {
    const response = await axiosInstanceWithLogin.get(`/api/user/info`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const editUserProfile = async (info) => {
  try {
    const response = await axiosInstanceWithLogin.post(`/api/user/info`, info);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getContributedDocs = async (id) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/user/info/contributeDocs`
    );
    if (response == null) {
      return [
        {
          docsId: null,
          author: null,
          title: "작성한 문서가 없어요",
          content: null,
          createdAt: "ㅠㅠ",
          modifiedAt: null,
          categoryList: null,
        },
      ];
    }
    return response.data;
  } catch (error) {
    throw error;
  }

  // return {
  //   docs: [
  //     {
  //       docs_id: "1",
  //       docs_title: "hello1",
  //       docs_is_redirected: false,
  //       docs_is_deleted: false,
  //       rev_modified_at: "2020-10-16",
  //     },
  //     {
  //       docs_id: "2",
  //       docs_title: "hello2",
  //       docs_is_redirected: false,
  //       docs_is_deleted: false,
  //       rev_modified_at: "2020-10-16",
  //     },
  //     {
  //       docs_id: "3",
  //       docs_title: "hello3",
  //       docs_is_redirected: false,
  //       docs_is_deleted: false,
  //       rev_modified_at: "2020-10-16",
  //     },
  //   ],
  // };
};

export const getContributedChats = async (id) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/user/info/contributeChats`
    );
    if (response == null) {
      return [
        {
          docsId: 1,
          nickname: "",
          content: "채팅이 없어요",
          createdAt: "NOTIME",
        },
      ];
    }
    return response.data;
  } catch (error) {
    throw error;
  }
  // return {
  //   docs: [
  //     {
  //       docs_title: "hello1",
  //       discuss_created_at: "2021-10-17",
  //       discuss_content: "안녕하쇼",
  //     },
  //     {
  //       docs_title: "hello2",
  //       discuss_created_at: "2021-10-18",
  //       discuss_content: "그건 좀 아닌듯",
  //     },
  //     {
  //       docs_title: "hello3",
  //       discuss_created_at: "2021-10-19",
  //       discuss_content: "솔직히 이게 맞지 않나...",
  //     },
  //   ],
  // };
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
    console.log(`{"usernameOrEmail":"${id}","password":"aaaaa"}`);
    let data = `{"usernameOrEmail":"${id}","password":"aaaaa"}`;
    const response = await axiosSsafygitInstance.post(`/signin`, data);
    return response.data;
  } catch (error) {
    if (
      error.response.data.message === "아이디가 존재하지 않습니다." ||
      error.response.data.message.startsWith("비밀번호가 ")
    ) {
      console.log("noID on API");
      return error.response.data.message;
    }
    return error;
  }
};

export const getUserInfo = async () => {
  try {
    const response = await axiosInstanceWithLogin.get(`/api/user/userinfo`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserContribute = async (startDate) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/user/info/contribute-docs?startDate=${startDate}`
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserContributeOneDay = async (date) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/user/info/day-contribute-docs?date=${date}`
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};
