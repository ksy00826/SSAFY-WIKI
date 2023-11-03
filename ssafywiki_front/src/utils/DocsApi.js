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
    const response = await axiosInstance.post(`/api/docs`, docs);
    console.log("성공");
    // console.log(response.data);
    return response.data;
  } catch (error) {
    throw error;
  }
};
