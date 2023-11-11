import { Button } from "antd";

import Logoimg from "assets/img/logo.png";

export const MyFooter = () => {
  return (
    <div>
      <img src={Logoimg} alt="logo" style={{ width: "50px", height: "50px" }} />
      <div>SSAFYWIKI | SSAFY 9th | E202 | Bukdu Five Star</div>
    </div>
  );
};

export default MyFooter;
