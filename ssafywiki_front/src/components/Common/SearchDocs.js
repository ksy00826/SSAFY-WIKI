import { useState, useEffect } from "react";
import { AutoComplete, Input } from "antd";
import { useNavigate } from "react-router-dom";
import { getSearchDoc } from "utils/DocsApi";
const App = () => {
  const navigate = useNavigate();
  const [options, setOptions] = useState([]);
  const [docid, setDocid] = useState(0);
  const [doctitle, setDoctitle] = useState("");

  const handleSearch = (value) => {
    setOptions(value ? searchResult(value) : [])
  };  
  const onSelect = (val, option) => {
    console.log(option);
    setDoctitle(option.label);
    setDocid(option.value);
    navigate(`res/content/${option.key}/${option.label}`);
  };
  const onSearch = (keyword) => {
    getSearchDoc(keyword).then((data)=>{
      var output = data.data.hits.hits;
      // console.log(output);
      var seq = 0;
      var newSearched = output.map(function(element) {
        seq = seq + 1;
        return {label: element._source.docs_title, value: element._source.docs_id};
      });
      if(newSearched.length > 0 && newSearched[0].label === keyword) {
        navigate(`res/content/${newSearched[0].value}/${newSearched[0].label}`);
      }
      else {
        navigate(`res/list?title=${keyword}`);
      }
    });
  }
  const searchResult = (keyword) => {
    // 키워드로 검색
    getSearchDoc(keyword).then((data)=>{
      var output = data.data.hits.hits;
      // console.log(output);
      var seq = 0;
      var newSearched = output.map(function(element) {
        seq = seq + 1;
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
    <AutoComplete
      popupClassName="certain-category-search-dropdown"
      popupMatchSelectWidth={500}
      style={{
        width: 300,
      }}
      options={options}
      onSelect={(val, option) => onSelect(val, option)}
      onSearch={handleSearch}
    >
      <Input.Search onSearch={onSearch} placeholder="검색" />
    </AutoComplete>
  );
};
export default App;
