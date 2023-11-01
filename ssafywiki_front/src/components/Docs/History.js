import { Card } from "antd";
import { useParams } from "react-router-dom";
import { getHistory } from "utils/RevisionApi"
import { useState } from "react";
import DocsNav from "./DocsNav";


const History = () => {
  const params = useParams();
  console.log(docsId);
  console.log(title);

  const [history, setHistory] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalItems, setTotalItems] = useState(0);

  React.useEffect(() => {
    getHistory(params.docsId, 1).then((response) => {
      console.log(response);
      setHistory(response.content);
      setCurrentPage(response.totalElements);
    })
  });

  return (
    <div>
      <h1>문서이름</h1>
      <DocsNav current="history" />
      <Card>
        <div>History</div>
      </Card>
    </div>

  );
};

export default History;
