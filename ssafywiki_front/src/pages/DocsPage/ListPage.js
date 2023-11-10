import React , { useEffect, useState } from "react";
import { Avatar, Button, List, Skeleton } from 'antd';
import { useNavigate, useSearchParams } from "react-router-dom";
import { getSearchDoc, getDocsList } from "utils/DocsApi";
const DocsList = () => {
  const [title, setTitle] = useState("");
  const [docs, setDocs] = useState([]);
  const [searchParams] = useSearchParams();

  const navigate = useNavigate();
  const makeDocs = () => {
    navigate(`/wrt?title=${title}`);
  };

  React.useEffect(() => {
    const title = searchParams.get("title");
    setTitle(title == undefined ? "문서 제목" : title); //url에서 가져오기

    getSearchDoc(title).then((data)=>{
      var output = data.data.hits.hits;
      console.log(output);
      var newSearched = output.map(function(element) {
          return element._source.docs_id;
      });
      console.log(newSearched);
      var getDocsListResponse = getDocsList(newSearched);
      // setDocs(getDocsListResponse);
    });
  }, [searchParams]);

  return (
    <div>
    <div>
      <Button type="primary">DocsList</Button>
      <Button type="default" onClick={makeDocs}>
        '{title}' 문서 생성하기
      </Button>
    </div>
    <div>
    <List
    itemLayout="horizontal"
    dataSource={docs}
    renderItem={(item, index) => (
      <List.Item>
        <List.Item.Meta
          avatar={<Avatar src={`https://xsgames.co/randomusers/avatar.php?g=pixel&key=${index}`} />}
          title={<a href="https://ant.design">{item.label}</a>}
          description="Ant Design, a design language for background applications, is refined by Ant UED Team"
        />
      </List.Item>
    )}
  />
    </div>
    </div>
  );
};

export default DocsList;
