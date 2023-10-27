import { axiosInstance } from "./AxiosConfig";

export const getDocsContent = async (id) => {
  try {
    const response = await axiosInstance.get(`/api/docs/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createDocs = async (docs) => {
  try {
    const response = await axiosInstance.post(`/api/docs`);
    return response.data;
  } catch (error) {
    throw error;
  }
};
