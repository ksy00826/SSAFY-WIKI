import { axiosInstance } from "./AxiosConfig";

export const getHistory = async (id, page) => {
    try {
        const response = await axiosInstance.get(`/api/version/${id}?page=${page}&size=20`);
        return response.data;
    } catch (err) {
        throw err;
    }
};