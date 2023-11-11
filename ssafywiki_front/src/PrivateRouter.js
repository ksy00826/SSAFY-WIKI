import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { isLogin } from "utils/Authenticate";

export const PrivateRoute = () => {
  return isLogin() ? <Outlet /> : <Navigate to="/member/login" />;
};

export const PublicRoute = () => {
  return !isLogin() ? <Outlet /> : <Navigate to="/userpage" />;
};
