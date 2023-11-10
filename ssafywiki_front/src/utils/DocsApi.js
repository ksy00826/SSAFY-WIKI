import { axiosInstance, axiosInstanceWithLogin,axiosElasticInstance } from "./AxiosConfig";

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

export const getDocsVersionContent = async (docsId, revId) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/docs/${docsId}?revId=${revId}`
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const subscribeRecentDocs = async () => {
  try {
    await axiosInstance.get(`/api/docs/sub`);
  } catch (error) {
    throw error;
  }
};

export const getRecentDocsList = async () => {
  try {
    const response = await axiosInstance.get("/api/docs/recent");
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createRedirectDocs = async (redirDocs) => {
  try {
    const response = await axiosInstanceWithLogin.post(
      "/api/redirect-docs",
      redirDocs
    );
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getSearchDoc = async (docsTitle) => {
  try {
    const response = await axiosElasticInstance.post(`_search`,`{
      "query": {
        "bool": { 
          "should": [ 
             { "match": { "docs_title": "${docsTitle}" } },
             { "match": { "docs_title.keyword": "${docsTitle}" } },
             { "match": { "docs_title.ngram": "${docsTitle}" } },
             { "match": { "docs_title.nori": "${docsTitle}" } }
          ]
        }
     }
    
    }
      
     `);
    return response;
  } catch (error) {
    throw error;
  }
};

export const getDocsList = async (idList) => {
  try {
    // console.log(`{
    //   docsIds: ${JSON.stringify(idList)}
    // }`);
    const response = await axiosInstanceWithLogin.post(`/api/docs/list`,JSON.stringify(idList));
    return response.data;
  } catch (error) {
    throw error;
  }
};