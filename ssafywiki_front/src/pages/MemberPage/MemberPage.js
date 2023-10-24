import { Routes, Route } from "react-router-dom";

import Login from "./LoginPage";
import SignUp from "./SignUpPage";

const MemberPage = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<SignUp />} />
    </Routes>
  );
};

export default MemberPage;
