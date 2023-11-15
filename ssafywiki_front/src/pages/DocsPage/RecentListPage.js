import styles from "./ListPage.module.css";
import { getRecentDocsList } from "utils/DocsApi";
import { Pagination, Table } from "antd";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
const RecentListPage = () => {
  const [docsList, setDocsList] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  // 페이지 변경 시 호출되는 함수
  const handlePageChange = (page) => {
    fetchDocsList(page - 1); // API는 0부터 시작하는 페이지 인덱스를 기대
  };

  // API 호출 함수
  const fetchDocsList = async (page) => {
    const response = await getRecentDocsList(page);
    const formattedDocs = response.content.map((doc) => ({
      ...doc,
      modifiedAt: formatTime(doc.modifiedAt),
    }));
    console.log(formattedDocs);
    setDocsList(formattedDocs); // 응답으로 받은 문서 목록을 설정

    setTotalPages(response.totalPages); // 전체 페이지 수를 설정
  };

  // 컴포넌트 마운트 시 초기 데이터 로드
  useEffect(() => {
    fetchDocsList(currentPage);
  }, []);

  const formatTime = (dateString) => {
    return dateString.substring(0, 10) + " " + dateString.substring(11);
  };

  const columns = [
    {
      title: "문서 제목",
      dataIndex: "title",
      key: "title",
      render: (text, record) => (
        <Link to={`/res/content/${record.docsId}/${text}`}>{text}</Link>
      ),
      width: "80%",
    },
    {
      title: "수정 시간",
      dataIndex: "modifiedAt",
      key: "modifiedAt",
      width: "20%",
    },
  ];

  return (
    <div>
      <h1 className={styles.search}>전체 문서</h1>
      <hr className={styles.border} />
      <Table columns={columns} dataSource={docsList} className={styles.table} />
      <Pagination
        defaultCurrent={1}
        total={totalPages * 10} // 가정: 각 페이지에 10개의 문서가 있음
        onChange={handlePageChange}
      />
    </div>
  );
};

export default RecentListPage;
