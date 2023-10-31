import { Routes, Route } from "react-router-dom";

import MyPage from "./MyPage";
import Contribution from "./ContributionPage";
import Userchats from "./UserchatsPage";
const UserPage = () => {
  return (
    <Routes>
      <Route path="/" element={<MyPage />} />
      <Route path="/contribution" element={<Contribution />} />
      <Route path="/userchats" element={<Userchats />} />
    </Routes>
  );
};

export default UserPage;
