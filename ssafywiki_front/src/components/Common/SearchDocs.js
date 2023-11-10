import { useState, useEffect } from "react";
import { AutoComplete, Input, Button } from "antd";
import { useNavigate } from "react-router-dom";
import {
  getSearchDoc,
  getDocsContent,
  getRedirectKeyword,
} from "utils/DocsApi";
import { SearchOutlined } from '@ant-design/icons';
import { relative } from "path-browserify";
import styles from "./SearchDocs.module.css";
const App = () => {
  const navigate = useNavigate();
  const [options, setOptions] = useState([]);
  const [docid, setDocid] = useState(0);
  const [doctitle, setDoctitle] = useState("");

  // const handleSearch = (value) => {
  //   setDoctitle(value);
  //   setOptions(value ? searchResult(value) : []);
  // };  
  const onSelect = (val, option) => {
    console.log("onSelect")
    setDocid(option.value);
    navigate(`res/content/${option.key}/${option.label}`);
  };
  const onSearch = (keyword) => {
    console.log("onSearch");
    getSearchDoc(keyword).then((data) => {
      var output = data.data.hits.hits;
      // console.log(output);
      var seq = 0;
      var newSearched = output.map(function (element) {
        seq = seq + 1;
        return {
          label: element._source.docs_title,
          value: element._source.docs_id,
        };
      });
      if (newSearched.length > 0 && newSearched[0].label === keyword) {
        getDocsContent(newSearched[0].value).then((response) => {
          //리다이렉트 문서인지 검사
          if (response.redirect) {
            getRedirectKeyword(newSearched[0].value).then((res) => {
              navigate(
                `/res/redirect?title=${res.originDocsTitle}&preId=${newSearched[0].value}&preTitle=${newSearched[0].label}`
              );
            });
          } else {
            navigate(
              `res/content/${newSearched[0].value}/${newSearched[0].label}`
            );
          }
        });
      } else {
        navigate(`res/list?title=${keyword}`);
      }
    });
  }
  const onSearchList = () => {
    console.log(doctitle," aaa");
      navigate(`res/list?title=${doctitle}`);
  }
  const searchResult = (keyword) => {
    // 자동완성옵션검색
    console.log("onChange",keyword);
    setDoctitle(keyword);
    getSearchDoc(keyword).then((data) => {
      var output = data.data.hits.hits;
      // console.log(output);
      var newSearched = output.map(function(element) {
        return {label: element._source.docs_title,value: element._source.docs_title, key: element._source.docs_id};
      });
      console.log(newSearched);
      setOptions(newSearched);
    });
    // return [
    //   { label: "새문서만들기", value: 1 },
    // ];
  };

  return (
    <div>
    <AutoComplete
      backfill="true"
      popupClassName="certain-category-search-dropdown"
      popupMatchSelectWidth={500}
      style={{
        width: 300,
      }}
      options={options}
      onSelect={(val, option) => onSelect(val, option)}
      onChange={searchResult}
    >
      <Input.Search placeholder="검색" onSearch={onSearch}/>
    </AutoComplete>
    <Button onClick={onSearchList} type="primary" icon={<SearchOutlined />}></Button>
    </div>
  );
};
export default App;
