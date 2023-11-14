import React, { useEffect, useState } from "react";
import { Pagination, Timeline, Radio, Button, Modal } from "antd";
import { useParams, useNavigate, Link, useAsyncError } from "react-router-dom";
import { getHistory, revertVersion } from "utils/RevisionApi";
import DocsNav from "./DocsNav";
import { ExclamationCircleFilled } from "@ant-design/icons";

import styles from "./Content.module.css";

const History = () => {
  const params = useParams();
  const navigate = useNavigate();
  const { confirm, error } = Modal;
  const [title, setTitle] = useState();
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [historyData, setHistoryData] = useState([]);

  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [selectedRevision, setSelectedRevision] = useState({
    oldRev: null,
    rev: null,
    oldRevNum: null,
    revNum: null,
  });

  useEffect(() => {
    setTitle(params.title + (params.subtitle !== undefined ? '/' + params.subtitle : ''));
  }, [params])

  useEffect(() => {
    const fetchData = async () => {
      // Implement the actual getHistory function to fetch data
      const result = await getHistory(params.docsId, currentPage - 1, pageSize);
      setHistoryData(result.content);
      setTotalPages(result.totalPages);
      setTotalElements(result.totalElements);
    };

    fetchData();
  }, [params, currentPage]);

  const handlePageChange = (page, pageSize) => {
    //console.log(page, pageSize);
    setCurrentPage(page);
    setPageSize(pageSize);
  };

  const onSelectRevision = (item, type) => {
    setSelectedRevision((prev) => ({
      ...prev,
      [type]: item.id,
      ...(type === "oldRev"
        ? { oldRevNum: item.number }
        : { revNum: item.number }),
    }));
    //console.log(selectedRevision);
  };

  const isOldRevDisabled = (item) => {
    return (selectedRevision.rev !== null) & (item.id >= selectedRevision.rev);
  };

  const isRevDisabled = (item) => {
    return (
      (selectedRevision.oldRev !== null) & (item.id <= selectedRevision.oldRev)
    );
  };

  const showConfirm = (e, revId, revNum) => {
    e.preventDefault();
    confirm({
      title: `정말 r${revNum} 버전으로 되돌릴건가요?`,
      icon: <ExclamationCircleFilled />,
      async onOk() {
        revertVersion(revId)
          .then((response) => {
            navigate(`/res/content/${params.docsId}/${title}`);
          })
          .catch((err) => {
            if (err.response.data.status == 402) {
              error({
                title: "권한이 없습니다.",
              });
            }
          });
      },
      onCancel() { },
    });
  };

  const timelineItems = historyData.map((item) => ({
    children: (
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <div>
          {item.createdAt}&nbsp;
          {"( "}
          <Link
            to={`/res/content/${params.docsId}/${title}?rev=${item.number}`}
            state={{ revId: item.id }}
          >
            보기
          </Link>
          {" | "}
          <Link
            to={`/res/raw/${title}?rev=${item.number}`}
            state={{ revId: item.id, docsId: params.docsId }}
          >
            RAW
          </Link>
          {" | "}
          <Link to="#" onClick={(e) => showConfirm(e, item.id, item.number)}>
            이 리비전으로 되돌리기
          </Link>
          {" ) "}
          <Radio.Group
            onChange={({ target }) => onSelectRevision(item, target.value)}
            value={
              selectedRevision.oldRev === item.id
                ? "oldRev"
                : selectedRevision.rev === item.id
                  ? "rev"
                  : null
            }
          >
            <Radio value="oldRev" disabled={isOldRevDisabled(item)}></Radio>
            <Radio value="rev" disabled={isRevDisabled(item)}></Radio>
          </Radio.Group>
          {item.originNumber != null && (
            <em>(r{item.originNumber}으로 되돌림)</em>
          )}
          {<strong>r{item.number}</strong>}&nbsp;
          {"(" + item.diffAmount + ")"}&nbsp;
          {item.user.nickname}..
          {item.comment != null ? `(${item.comment})` : ""}
        </div>
      </div>
    ),
  }));

  const onClickDiff = (e) => {
    navigate(
      `/res/diff/${title}?oldrev=${selectedRevision.oldRevNum}&rev=${selectedRevision.revNum}`,
      { state: { oldRev: selectedRevision.oldRev, rev: selectedRevision.rev, title: title } }
    );
  };

  return (
    <div>
      <div className={styles.contentTitle}>
        <h1 className={styles.title}>
          {title}{" "}
          <small style={{ fontWeight: "normal" }}>(문서 역사)</small>
        </h1>
        <div className={styles.nav}>
          <DocsNav current="history" />
        </div>
      </div>

      <div style={{ marginLeft: "3%", textAlign: "left" }}>
        <Button onClick={onClickDiff}>선택 버전 비교</Button>
      </div>
      <div style={{ margin: "3%" }}>
        <Timeline mode="left" items={historyData != null && timelineItems} />
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
