import { Routes, Route } from "react-router-dom";

import MyPage from "../../components/User/MyPage";
import Contribution from "../../components/User/ContributionPage";
import Userchats from "../../components/User/UserchatsPage";
import UserGroup from "components/User/UserGroupPage";
import Bookmark from "components/User/BookmarkDocsPage";
import Edituser from "../../components/User/EdituserPage";

const UserPage = () => {
  return (
    <Routes>
      <Route path="/" element={<MyPage />} />
      <Route path="/edituser" element={<Edituser />} />
      {/* <Route path="/contribution" element={<Contribution />} /> */}
      <Route path="/userchats" element={<Userchats />} />
      <Route path="/usergroup" element={<UserGroup />} />
      <Route path="/bookmark" element={<Bookmark />} />
    </Routes>
  );
};

export default UserPage;
