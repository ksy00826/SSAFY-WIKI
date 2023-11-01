import { Routes, Route } from "react-router-dom";

import MyPage from "./MyPage";
import Contribution from "./ContributionPage";
import Userchats from "./UserchatsPage";
import Edituser from "./EdituserPage";
const UserPage = () => {
  return (
    <Routes>
      <Route path="/" element={<MyPage />} />
      <Route path="/edituser" element={<Edituser />} />
      <Route path="/contribution" element={<Contribution />} />
      <Route path="/userchats" element={<Userchats />} />
    </Routes>
  );
};

export default UserPage;
