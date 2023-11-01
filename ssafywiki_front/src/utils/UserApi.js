import { axiosInstance } from "./AxiosConfig";

export const getUserProfile = async (id) => {
  try {
    const response = await axiosInstance.get(`/api/user`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const editUserProfile = async (nickname,passwordOld,passwordNew) => {
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
  return { 'docs' : [{docs_id : '1',docs_title:'hello1',docs_is_redirected : false,docs_is_deleted : false,rev_modified_at:"2020-10-16"},
  {docs_id : '2',docs_title:'hello2',docs_is_redirected : false,docs_is_deleted : false,rev_modified_at:"2020-10-16"},
  {docs_id : '3',docs_title:'hello3',docs_is_redirected : false,docs_is_deleted : false,rev_modified_at:"2020-10-16"}]};
};

export const getContributedChats = async (id) => {
  try {
    const response = await axiosInstance.get(`/api/mypage/writechats`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getStarDocs = async (id) => {
  try {
    const response = await axiosInstance.get(`/api/mypage/stardocs`);
    return response.data;
  } catch (error) {
    throw error;
  }
};
