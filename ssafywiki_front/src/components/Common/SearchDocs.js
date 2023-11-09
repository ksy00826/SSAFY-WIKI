import { useState, useEffect } from "react";
import { AutoComplete, Input } from "antd";
import { useNavigate } from "react-router-dom";
import { getSearchDoc } from "utils/DocsApi";
const App = () => {
  const navigate = useNavigate();
  const [options, setOptions] = useState([]);


  const handleSearch = (value) => {
    setOptions(value ? searchResult(value) : [])
  };
  const onSelect = (value, text) => {
    console.log("onSelectt", text);
    navigate(`res/content/${text.value}/${text.label}`);
  };

  const searchResult = (keyword) => {
    // 키워드로 검색
    getSearchDoc(keyword).then((data)=>{
      var output = data.data.hits.hits;
      // console.log(output);
      var seq = 0;
      var newSearched = output.map(function(element) {
        seq = seq + 1;
        return {label: element._source.docs_title, value: seq};
      });
      console.log(newSearched);
      setOptions(newSearched);
    });
    // return [
    //   { label: "싸피위키:대문", value: 1 },
    //   { label: "2번문서", value: 2 },
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
      onSelect={onSelect}
      onSearch={handleSearch}
    >
      <Input.Search placeholder="검색" />
    </AutoComplete>
  );
};
export default App;
