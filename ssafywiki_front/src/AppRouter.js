import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

import DocsPage from "pages/DocsPage/DocsPage";
import UserPage from "./pages/UserPage/UserPage";
import AdminPage from "./pages/AdminPage/AdminPage";
import MemberPage from "./pages/MemberPage/MemberPage";
import { PrivateRoute, PublicRoute } from "PrivateRouter";

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={<Navigate to="/res/content/싸피위키:대문" />}
        />
        <Route path="/res/*" element={<DocsPage />} />
        <Route path="/admin" element={<AdminPage />} />
        {/* member는 로그인 안해도 되는 페이지, userpage는 로그인해야 들어갈 수 있는 페이지 */}
        <Route element={<PublicRoute />}>
          <Route path="/member/*" element={<MemberPage />} />
        </Route>
        <Route element={<PrivateRoute />}>
          <Route path="/userpage/*" element={<UserPage />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default AppRouter;
