import { axiosInstanceWithLogin } from "./AxiosConfig";

export const reportDocument = async (docsId) => {
    try {
        const response = await axiosInstanceWithLogin.post(`/api/report/docs/${docsId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}