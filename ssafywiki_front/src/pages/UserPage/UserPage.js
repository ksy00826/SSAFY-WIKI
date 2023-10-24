import { Routes, Route } from "react-router-dom";

import MyPage from "./MyPage";
import Contribution from "./ContributionPage";

const UserPage = () => {
  return (
    <Routes>
      <Route path="/" element={<MyPage />} />
      <Route path="/contribution" element={<Contribution />} />
    </Routes>
  );
};

export default UserPage;
