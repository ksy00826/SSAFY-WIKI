import React, { useEffect, useState } from "react";
import { Avatar, Button, List, Skeleton } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";
import { getSearchDoc, getDocsList } from "utils/DocsApi";
const DocsList = () => {
  const [title, setTitle] = useState("");
  const [docs, setDocs] = useState([]);
  const [searchParams] = useSearchParams();
  const [isExist, setExist] = useState(false);
  const navigate = useNavigate();
  const makeDocs = () => {
    navigate(`/wrt?title=${title}`);
  };

  React.useEffect(() => {
    const title = searchParams.get("title");
    setTitle(title == undefined ? "문서 제목" : title); //url에서 가져오기

    getSearchDoc(title).then((data) => {
      var output = data.data.hits.hits;
      // console.log(output);
      var newSearched = output.map(function (element) {
        return element._source.docs_id;
      });
      // console.log(newSearched);

      var getDocsListResponse = getDocsList(newSearched).then((data) => {
        console.log(data[0]);
        if (data[0] !== undefined) {
          setExist(data[0].title === title);
        }

        setDocs(data);
      });
    });
  }, [searchParams]);

  return (
    <div>
      <div>
        {!isExist && (
          <Button type="default" onClick={makeDocs}>
            '{title}' 문서 생성하기
          </Button>
        )}
      </div>
      <div>
        <List
          itemLayout="horizontal"
          dataSource={docs}
          renderItem={(item, index) => (
            <List.Item
              onClick={() => {
                navigate(`/res/content/${item.docsId}/${item.title}`);
              }}
            >
              <List.Item.Meta
                avatar={
                  <Avatar
                    src={`https://xsgames.co/randomusers/avatar.php?g=pixel&key=${index}`}
                  />
                }
                title={item.title}
                description={item.content.substr(0, 60) + "..."}
              />
            </List.Item>
          )}
        />
      </div>
    </div>
  );
};

export default DocsList;
