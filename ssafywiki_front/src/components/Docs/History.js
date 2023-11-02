import React, { useEffect, useState } from "react";
import { Card, Pagination, List, Timeline, Radio, Button } from "antd";
import { useParams } from "react-router-dom";
import { getHistory, compareVersions } from "utils/RevisionApi"
import DocsNav from "./DocsNav";


const History = () => {
  const params = useParams();

  const [data, setData] = useState([]);
  const [history, setHistory] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalItems, setTotalItems] = useState(0);
  const [pageSize, setPageSize] = useState(20);

  const [selectedVersions, setSelectedVersions] = useState({
    rev: null,
    oldrev: null,
  });

  const [selected, setSelected] = useState({ value1: null, value2: null });


  const handlePageChange = (page, pageSize) => {
    setCurrentPage(page);
    setPageSize(pageSize);
    console.log(page);
  };

  const handleVersionChange = (version, number) => {
    console.log(version, number);
    setSelectedVersions(prev => {
      const newState = {
        ...prev,
        [number === 1 ? 'oldrev' : 'rev']: version
      };
      console.log("Updating state to:", newState);
      return newState;
    });

    setSelected(prev => {
      if (number === 1) {
        return { ...prev, value1: version, value2: null };
      } else {
        return { ...prev, value1: null, value2: version };
      }
    });
  };


  const handleRadioDisabled = (version, number) => {
    console.log(selectedVersions);
    if (selectedVersions != null && number === 1 && version >= selectedVersions.rev) {
      return true;
    }
    if (selectedVersions != null && number === 2 && version <= selectedVersions.oldrev) {
      return true;
    }

    return false;
  }

  const handleVersionCompare = (selectedVersions) => {
    if (selectedVersions.rev != null && selectedVersions.oldrev != null) {
      compareVersions(selectedVersions.oldrev, selectedVersions.rev);
    }
  }

  useEffect(() => {
    getHistory(params.docsId, currentPage, pageSize).then((response) => {
      console.log(response);
      setData(response);
      setHistory(response.content.map(item => ({
        children: (
          <p key={item.id}>
            {item.createdAt}&nbsp;
            <Radio.Group
              onChange={e => handleVersionChange(item.id, e.target.value)}
              value={selected.value1 === item.id ? 1 : selected.value2 === item.id ? 2 : null}
            >
              <Radio value={1} disabled={selected.value2 && item.id < selected.value2}></Radio>
              <Radio value={2} disabled={selected.value1 && item.id > selected.value1}></Radio>
            </Radio.Group>
            {item.originNumber != null && <em>(r{item.originNumber}으로 되돌림)</em>}
            {<strong>r{item.number}</strong>}&nbsp;
            {'(' + item.diffAmount + ')'}&nbsp;
            {item.user.nickname}..
            {item.comment != null ? `(${item.comment})` : ''}
          </p >
        )
      })));
      setTotalItems(response.totalElements);
      setSelectedVersions(null, null);
      console.log(selectedVersions);
    }).catch(err => {
      console.error("Failed Fetch History: ", err);
    });
  }, [params, currentPage]);

  useEffect(() => {
    setHistory(data.content.map(item => ({
      children: (
        <p key={item.id}>
          {item.createdAt}&nbsp;
          <Radio.Group
            onChange={e => handleVersionChange(item.id, e.target.value)}
            value={selected.value1 === item.id ? 1 : selected.value2 === item.id ? 2 : null}
          >
            <Radio value={1} disabled={selected.value2 && item.id < selected.value2}></Radio>
            <Radio value={2} disabled={selected.value1 && item.id > selected.value1}></Radio>
          </Radio.Group>
          {item.originNumber != null && <em>(r{item.originNumber}으로 되돌림)</em>}
          {<strong>r{item.number}</strong>}&nbsp;
          {'(' + item.diffAmount + ')'}&nbsp;
          {item.user.nickname}..
          {item.comment != null ? `(${item.comment})` : ''}
        </p >
      )
    })));
  }, [selected, data]);

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

        <Timeline style={{ marginLeft: '10%', marginRight: '10%', textAlign: 'left' }} items={history} />

      </div>

      <Pagination
        current={currentPage}
        pageSize={pageSize}
        onChange={handleVersionCompare}
        total={totalItems}
      />
    </div>

  );
};

export default History;
