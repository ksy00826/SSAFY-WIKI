import React, { useEffect, useState } from "react";
import { Card, Pagination, List, Timeline, Radio, Button } from "antd";
import { useParams } from "react-router-dom";
import { getHistory, compareVersions } from "utils/RevisionApi"
import DocsNav from "./DocsNav";


const History = () => {
  const params = useParams();

  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalItems, setTotalItems] = useState(0);
  const [pageSize, setPageSize] = useState(20);


  const [historyData, setHistoryData] = useState([]);

  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [selectedRevision, setSelectedRevision] = useState({ oldRev: null, rev: null });



  useEffect(() => {
    const fetchData = async () => {
      // Implement the actual getHistory function to fetch data
      const result = await getHistory(params.docsId, currentPage, pageSize);
      setHistoryData(result.content);
      setTotalPages(result.totalPages);
      setTotalElements(result.totalElements);
    };

    fetchData();
  }, [params, currentPage, pageSize]);

  const handlePageChange = (page, pageSize) => {
    setCurrentPage(page);
    setPageSize(pageSize);
  };

  const onSelectRevision = (id, type) => {
    setSelectedRevision(prev => ({
      ...prev,
      [type]: id === prev[type] ? null : id
    }));
  };

  const isOldRevDisabled = (item) => {
    return selectedRevision.rev !== null & item.id >= selectedRevision.rev;
  };

  const isRevDisabled = (item) => {
    return selectedRevision.oldRev !== null & item.id <= selectedRevision.oldRev;
  }

  const timelineItems = historyData.map(item => ({
    children: (
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          {item.createdAt}&nbsp;
          <Radio.Group
            onChange={({ target }) => onSelectRevision(item.id, target.value)}
            value={selectedRevision.oldRev === item.id ? 'oldRev' : selectedRevision.rev === item.id ? 'rev' : null}
          >
            <Radio value="oldRev" disabled={isOldRevDisabled(item)}>Old Rev</Radio>
            <Radio value="rev" disabled={isRevDisabled(item)}>Rev</Radio>
          </Radio.Group>
          {item.originNumber != null && <em>(r{item.originNumber}으로 되돌림)</em>}
          {<strong>r{item.number}</strong>}&nbsp;
          {'(' + item.diffAmount + ')'}&nbsp;
          {item.user.nickname}..
          {item.comment != null ? `(${item.comment})` : ''}
        </div>
      </div>
    )
  }));



  return (
    <div>
      <h1>문서이름</h1>
      <DocsNav current="history" />
      <Card>
        <div>History</div>
      </Card>
      <Button
        onClick={compareVersions}
      >
        버전 비교
      </Button>
      <div>
        <Timeline mode="left" items={timelineItems} />
        <Pagination
          current={currentPage}
          pageSize={pageSize}
          total={totalElements}
          onChange={handlePageChange}
        />
      </div>
    </div>

  );
};

export default History;
