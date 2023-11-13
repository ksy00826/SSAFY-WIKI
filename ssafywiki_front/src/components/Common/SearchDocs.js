import { useState, useEffect } from "react";
import { AutoComplete, Input, Button } from "antd";
import { useNavigate } from "react-router-dom";
import {
  getSearchDoc,
  getDocsContent,
  getRedirectKeyword,
} from "utils/DocsApi";
import { SearchOutlined, UnorderedListOutlined } from "@ant-design/icons";
import styles from "./SearchDocs.module.css";
const App = () => {
  const navigate = useNavigate();
  const [options, setOptions] = useState([]);
  const [docid, setDocid] = useState(0);
  const [doctitle, setDoctitle] = useState("");
  const [searchInput, setSearchInput] = useState("");

  const onInputChange = (event) => {
    //console.log("onInputChange " + event.target.value );
    setSearchInput(event.target.value); // 사용자 입력을 상태에 저장
  };

  const onSearchClick = () => {
    //console.log("onSearchClick " + searchInput);
    onSearch(searchInput); // 버튼 클릭 시 현재 입력된 텍스트로 검색 수행
  };

  const onKeyDown = (event) => {
    if (event.key === "Enter") {
      onSearchClick(searchInput); // 사용자가 엔터 키를 누를 때 onSearchClick 호출
    }
  };

  // const handleSearch = (value) => {
  //   setDoctitle(value);
  //   setOptions(value ? searchResult(value) : []);
  // };
  const onSelect = (val, option) => {
    //console.log("onSelect");
    setDoctitle(option.label);
    setDocid(option.value);
    //console.log("여기서 분명 셋하고있어", option.label);
    setSearchInput(option.label);
    onSearch(option.label);
    // navigate(`res/content/${option.key}/${option.label}`);
  };
  const onSearch = (keyword) => {
    //console.log("onSearch", keyword);
    getSearchDoc(keyword).then((data) => {
      var output = data.data.hits.hits;
      // //console.log(output);
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
  };
  const onSearchList = () => {
    navigate(`res/list?title=${doctitle}`);
  };
  const searchResult = (keyword) => {
    // 자동완성옵션검색
    setDoctitle(keyword);
    getSearchDoc(keyword).then((data) => {
      var output = data.data.hits.hits;
      // //console.log(output);
      var newSearched = output
        .map(function (element) {
          if (element._source.docs_is_deleted) return null;
          return {
            label: element._source.docs_title,
            value: element._source.docs_title,
            key: element._source.docs_id,
          };
        })
        .filter((opt) => opt != null);
      setOptions(newSearched);
    });
    // return [
    //   { label: "새문서만들기", value: 1 },
    // ];
  };

  return (
    <div className={styles.searchBox}>
      <AutoComplete
        backfill="true"
        popupClassName="certain-category-search-dropdown"
        popupMatchSelectWidth={400}
        options={options}
        onSelect={(val, option) => onSelect(val, option)}
        onChange={searchResult}
      >
        <input
          placeholder="검색"
          onSearch={onSearch}
          className={styles.search}
          onChange={onInputChange}
          onKeyDown={onKeyDown}
        />
      </AutoComplete>
      <div className={styles.Button} onClick={onSearchClick}>
        <SearchOutlined />
      </div>
      <div
        className={styles.Button}
        onClick={onSearchList}
        style={{ borderRadius: "0 5px 5px 0" }}
      >
        <UnorderedListOutlined />
      </div>
    </div>
  );
};
export default App;
