import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 1000,
});
