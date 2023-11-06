import { axiosInstance, axiosInstanceWithLogin } from "./AxiosConfig";

export const getTemplate = async (page) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/docs/template?isMyTemplate=true&page=${page}&size=5`
    );
    return response.data;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const getTemplateDetail = async (id) => {
  const dummyData = {
    templateId: 1,
    title: "title1",
    content: "content1",
    secret: false,
  };

  return dummyData;
};
