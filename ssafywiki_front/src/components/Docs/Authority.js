import React from "react";
import { useParams } from "react-router-dom";

import DocsNav from "./DocsNav";
import AuthorityForm from "components/Write/AuthorityForm";

const Authority = ({ read, write, userList, modify }) => {
  const params = useParams();

  const [data, setData] = React.useState();
  const checkEmail = (value) => {
    console.log(value);
    // 이메일이 존재하는 유저인지 체크하고 추가.
  };

  React.useEffect(() => {
    // 첫 랜더링시
  }, []);

  return (
    <div>
      <h1>
        {params.title} <small>(문서 권한)</small>
      </h1>
      <DocsNav current="auth" />

      <AuthorityForm modify={true} read={1} write={2}></AuthorityForm>
    </div>
  );
};

export default Authority;
