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
  try {
    const response = await axiosInstance.get(`/api/mypage/writedocs`);
    return response.data;
  } catch (error) {
    throw error;
  }
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
