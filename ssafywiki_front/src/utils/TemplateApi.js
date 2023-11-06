import { axiosInstance, axiosInstanceWithLogin } from "./AxiosConfig";

export const getTemplate = async (page, isMyTemplate) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/docs/template?isMyTemplate=${isMyTemplate}&page=${page}&size=5`
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const getTemplateDetail = async (templateId) => {
  try {
    const response = await axiosInstance.get(
      `/api/docs/template/${templateId}`
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const searchTemplateWithKeyword = async (
  page,
  isMyTemplate,
  keyword
) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/docs/template/search?isMyTemplate=${isMyTemplate}&keyword=${keyword}&page=${page}&size=5`
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const getTemplateList = async (page, isMyTemplate, keyword) => {
  if (keyword === "") {
    try {
      const response = await axiosInstanceWithLogin.get(
        `/api/docs/template?isMyTemplate=${isMyTemplate}&page=${page}&size=5`
      );
      return response.data;
    } catch (error) {
      console.log(error);
      throw error;
    }
  } else {
    try {
      const response = await axiosInstanceWithLogin.get(
        `/api/docs/template/search?isMyTemplate=${isMyTemplate}&keyword=${keyword}&page=${page}&size=5`
      );
      return response.data;
    } catch (error) {
      console.log(error);
      throw error;
    }
  }
};

export const createTemplate = async (template) => {
  console.log(template);
  try {
    const response = await axiosInstanceWithLogin.post(
      `/api/docs/template`,
      template
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const deleteTemplate = async (templateId) => {
  try {
    const response = await axiosInstanceWithLogin.delete(
      `/api/docs/template/${templateId}`
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};
