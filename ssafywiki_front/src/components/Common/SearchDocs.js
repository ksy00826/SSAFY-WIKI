import React from "react";
import { AutoComplete, Input } from "antd";
import { useNavigate } from "react-router-dom";

const App = () => {
  const navigate = useNavigate();
  const [options, setOptions] = React.useState([]);

  const handleSearch = (value) => {
    setOptions(value ? searchResult(value) : []);
  };
  const onSelect = (value, text) => {
    console.log("onSelectt", text);
    navigate(`res/content/${text.value}/${text.label}`);
  };

  const searchResult = (keyword) => {
    // 키워드로 검색
    return [
      { label: "싸피위키:대문", value: 1 },
      { label: "2번문서", value: 2 },
    ];
  };

  return (
    <AutoComplete
      popupClassName="certain-category-search-dropdown"
      popupMatchSelectWidth={500}
      style={{
        width: 250,
      }}
      options={options}
      onSelect={onSelect}
      onSearch={handleSearch}
    >
      <Input.Search placeholder="input here" />
    </AutoComplete>
  );
};
export default App;
