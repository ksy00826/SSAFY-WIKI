import axios from "axios";
import { getToken } from "./Authenticate";

export const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 5000,
});

export const axiosInstanceWithLogin = axios.create({
  baseURL: process.env.REACT_APP_SERVER_API_URL,
  headers: {
    "Content-Type": "application/json",
    Authorization: `Bearer ${getToken()}`,
  },
});

export const axiosInstanceNoTimeout = axios.create({
  baseURL: process.env.REACT_APP_SERVER_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const axiosSsafygitInstance = axios.create({
  maxBodyLength: Infinity,
  baseURL: "https://project.ssafy.com/ssafy/api/auth/",
  headers: {
    Accept: "application/json, text/plain, */*",
    "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6",
    Authorization: "",
    "Content-Type": "application/json;charset=UTF-8",
  },
});
export const axiosElasticInstance = axios.create({
  maxBodyLength: Infinity,
  baseURL: "https://k9e202a.p.ssafy.io/test_v1.0.0/",
  headers: {
    Accept: "application/json, text/plain, */*",
    "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6",
    Authorization: "",
    "Content-Type": "application/json;charset=UTF-8",
  },
  
});