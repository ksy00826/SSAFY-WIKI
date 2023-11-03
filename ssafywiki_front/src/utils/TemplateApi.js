import { axiosInstance } from "./AxiosConfig";

export const getTemplate = async (page) => {
  const dummyData = [
    { templateId: 1, title: "title1", author: "1" },
    { templateId: 2, title: "title2", author: "2" },
    { templateId: 3, title: "title3", author: "3" },
  ];
  return dummyData;
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
