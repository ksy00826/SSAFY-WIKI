import { axiosInstanceWithLogin } from "./AxiosConfig";

export const getAuth = async (docsId) => {
  try {
    const response = await axiosInstanceWithLogin.get(
      `/api/docs/auth/${docsId}`
    );
    return response.data;
  } catch (err) {
    throw err;
  }
};

export const updateAuth = async (info) => {
  try {
    const response = await axiosInstanceWithLogin.post(`/api/docs/auth`, info);
    return response.data;
  } catch (err) {
    throw err;
  }
};

export const inviteUser = async (info) => {
  try {
    const response = await axiosInstanceWithLogin.post(
      `/api/docs/auth/member`,
      info
    );
    return response.data;
  } catch (err) {
    throw err;
  }
};

export const deleteUser = async (info) => {
  try {
    const response = await axiosInstanceWithLogin.patch(
      `/api/docs/auth/member`,
      info
    );
    return response.data;
  } catch (err) {
    throw err;
  }
};
