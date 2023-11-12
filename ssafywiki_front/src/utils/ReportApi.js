import { axiosInstanceWithLogin } from "./AxiosConfig";

export const reportDocument = async (docsId) => {
    try {
        const response = await axiosInstanceWithLogin.post(`/api/report/docs/${docsId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const getDocumentReport = async (page, size) => {
    try {
        const response = await axiosInstanceWithLogin.get(`/api/admin/docs-report?page=${page}&size=${size}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const deleteDocument = async (info) => {
    try {
        const response = await axiosInstanceWithLogin.post(`/api/admin/docs-report`, info);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const rejectReport = async (reportId) => {
    try {
        const response = await axiosInstanceWithLogin.delete(`/api/admin/docs-report/${reportId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}