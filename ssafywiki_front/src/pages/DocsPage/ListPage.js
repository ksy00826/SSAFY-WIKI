import React, { useEffect, useState } from "react";
import { Avatar, Button, List, Skeleton } from "antd";
import { useNavigate, useSearchParams } from "react-router-dom";
import { getSearchDoc, getDocsList } from "utils/DocsApi";
import { FileOutlined } from "@ant-design/icons";
import stlyes from "./ListPage.module.css";
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
      // //console.log(output);
      var newSearched = output
        .map(function (element) {
          if (element._source.docs_is_deleted) return null;
          return element._source.docs_id;
        })
        .filter((opt) => opt != null);
      // //console.log(newSearched);

      var getDocsListResponse = getDocsList(newSearched).then((data) => {
        //console.log(data[0]);
        if (data[0] !== undefined) {
          setExist(data[0].title === title);
        }
        setDocs(data);
      });
    });
  }, [searchParams]);

  return (
    <div>
      <h1 className={stlyes.search}>검색</h1>
      {!isExist && (
        <div className={stlyes.createBox}>
          <p>&gt; 찾는 문서가 없나요? 문서를 바로 생성해보세요</p>
          <Button onClick={makeDocs}>'{title}' 문서 생성하기</Button>
        </div>
      )}
      <div>
        <div type="default" className={stlyes.result}>
          전체 {docs.length} 건
          <hr className={stlyes.border} />
        </div>
        <List
          itemLayout="horizontal"
          dataSource={docs}
          renderItem={(item, index) => (
            <List.Item>
              <div
                className={stlyes.searchBox}
                onClick={() => {
                  navigate(`/res/content/${item.docsId}/${item.title}`);
                }}
              >
                <div className={stlyes.titleBox}>
                  <h1 className={stlyes.docsIcon}>
                    <FileOutlined />
                  </h1>
                  <h1 className={stlyes.title}>{item.title}</h1>
                </div>
                <div className={stlyes.content}>
                  {item.content.substr(0, 400)}
                  {item.content.length > 400 && "..."}
                </div>
              </div>
            </List.Item>
          )}
        />
      </div>
    </div>
  );
};

export default DocsList;
