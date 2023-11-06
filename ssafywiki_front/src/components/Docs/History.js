import React, { useEffect, useState } from "react";
import { Card, Pagination, Timeline, Radio, Button } from "antd";
import { useParams, useNavigate, Link } from "react-router-dom";
import { getHistory } from "utils/RevisionApi"
import DocsNav from "./DocsNav";


const History = () => {
  const params = useParams();
  const navigate = useNavigate();

  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  const [pageSize, setPageSize] = useState(20);


  const [historyData, setHistoryData] = useState([]);

  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [selectedRevision, setSelectedRevision] = useState({ oldRev: null, rev: null, oldRevNum: null, revNum: null });



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

  const onSelectRevision = (item, type) => {
    setSelectedRevision(prev => ({
      ...prev,
      [type]: item.id,
      ...(type === 'oldRev' ? { oldRevNum: item.number } : { revNum: item.number })
    }));
    console.log(selectedRevision);
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
          {"( "}<Link to={`/res/content/${params.docsId}/${params.title}?rev=${item.number}`} state={{revId: item.id}}>보기</Link>{" | "}<Link to="">RAW</Link>{" | "}<Link>이 리비전으로 되돌리기</Link>{" ) "}
          <Radio.Group
            onChange={({ target }) => onSelectRevision(item, target.value)}
            value={selectedRevision.oldRev === item.id ? 'oldRev' : selectedRevision.rev === item.id ? 'rev' : null}
          >
            <Radio value="oldRev" disabled={isOldRevDisabled(item)}></Radio>
            <Radio value="rev" disabled={isRevDisabled(item)}></Radio>
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

  const onClickDiff = (e) => {
    navigate(`/res/diff/${params.title}?oldrev=${selectedRevision.oldRevNum}&rev=${selectedRevision.revNum}`,
      { state: { oldRev: selectedRevision.oldRev, rev: selectedRevision.rev } });
  };

  return (
    <div>
      <h1>{params.title} <small>(문서 역사)</small></h1>
      <DocsNav current="history" />
      <Card>
        <div>History</div>
      </Card>
      <Button
        onClick={onClickDiff}
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
