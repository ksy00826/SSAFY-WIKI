import { axiosInstance, axiosInstanceWithLogin } from "./AxiosConfig";

export const getDocsContent = async (id) => {
  try {
    const response = await axiosInstanceWithLogin.get(`/api/docs/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUpdateContent = async (id) => {
  try {
    const response = await axiosInstanceWithLogin.get(`/api/docs/update/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createDocs = async (docs) => {
  try {
    const response = await axiosInstanceWithLogin.post(`/api/docs`, docs);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getDiscussList = async (docsId) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/chatlist/${docsId}`
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const updateDocs = async (docs) => {
  try {
    const response = await axiosInstanceWithLogin.put(`/api/docs`, docs);
    return response.data;
  } catch (error) {
    throw error;
  }
};
