import { axiosInstance, axiosInstanceWithLogin } from "./AxiosConfig";

export const checkBookMarked = async (docsId) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/docs/bookmark/${docsId}`,
      null,
      null
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const countBookMark = async (docsId) => {
  try {
    const response = await axiosInstance.get(
      `/api/docs/bookmark/cnt/${docsId}`
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const writeBookMark = async (docsId) => {
  try {
    const response = await axiosInstanceWithLogin.post(
      `/api/docs/bookmark/${docsId}`,
      null,
      null
    );
    // const response = await axiosInstanceWithLogin.get(
    //   `/api/admin/admin`,
    //   null,
    //   null
    // );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const deleteBookMark = async (docsId) => {
  try {
    const response = await axiosInstanceWithLogin.delete(
      `/api/docs/bookmark/${docsId}`
    );
    //console.log(response);
    return response.data;
  } catch (error) {
    throw error;
  }
};
