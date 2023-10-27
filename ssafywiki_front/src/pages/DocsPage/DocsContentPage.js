import { Routes, Route } from "react-router-dom";

import Content from "components/Docs/Content";
import History from "components/Docs/History";
import Edit from "components/Docs/Edit";
import Authority from "components/Docs/Authority";
import Diff from "components/Docs/Diff";
import Raw from "components/Docs/Raw";

const UserPage = () => {
  return (
    <Routes>
      <Route path="/history/*" element={<History />} />
      <Route path="/edit/*" element={<Edit />} />
      <Route path="/auth/*" element={<Authority />} />
      <Route path="/content/*" element={<Content />} />
      <Route path="/diff/*" element={<Diff />} />
      <Route path="/raw/*" element={<Raw />} />
    </Routes>
  );
};

export default UserPage;
