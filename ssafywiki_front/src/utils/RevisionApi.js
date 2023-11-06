import { axiosInstance } from "./AxiosConfig";

export const getHistory = async (docsId, page, size) => {
    try {
        const response = await axiosInstance.get(`/api/version/${docsId}?page=${page}&size=${size}`);
        return response.data;
    } catch (err) {
        throw err;
    }
};

export const compareVersions = async (oldrev, rev) => {
    try {
        const response = await axiosInstance.get(`/api/version/compare?rev=${rev}&oldrev=${oldrev}`);
        return response.data;
    } catch (err) {
        throw err;
    }
};

export const getConflict = async () => {
    try {
        const response = await axiosInstance.get(`/api/version/merge`);
        return response.data;
    } catch (err) {
        throw err;
    }
}