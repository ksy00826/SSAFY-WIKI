import { Routes, Route } from "react-router-dom";

import MyPage from "../../components/User/MyPage";
import Contribution from "../../components/User/ContributionPage";
import Userchats from "../../components/User/UserchatsPage";
import Edituser from "../../components/User/EdituserPage";
const UserPage = () => {
  return (
    <Routes>
      <Route path="/" element={<MyPage />} />
      <Route path="/edituser" element={<Edituser />} />
      {/* <Route path="/contribution" element={<Contribution />} /> */}
      <Route path="/userchats" element={<Userchats />} />
    </Routes>
  );
};

export default UserPage;
