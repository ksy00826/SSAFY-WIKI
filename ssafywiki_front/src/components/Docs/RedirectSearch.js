import React from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { getSearchDoc } from "utils/DocsApi";

const RedirectSearch = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const onSearch = (keyword) => {
    getSearchDoc(keyword).then((data) => {
      var output = data.data.hits.hits;
      console.log(output);
      var seq = 0;
      var newSearched = output.map(function (element) {
        seq = seq + 1;
        return {
          label: element._source.docs_title,
          value: element._source.docs_id,
        };
      });
      if (newSearched.length > 0 && newSearched[0].label === keyword) {
        navigate(
          `/res/content/${newSearched[0].value}/${newSearched[0].label}`
        );
      } else {
        navigate(`/res/list?title=${keyword}`);
      }
    });
  };

  React.useEffect(() => {
    const title = searchParams.get("title");
    console.log(title);
    onSearch(title);
  }, []);

  return (
    <>
      <div>redirect</div>
    </>
  );
};
export default RedirectSearch;
