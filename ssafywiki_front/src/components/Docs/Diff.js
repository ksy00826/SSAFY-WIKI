import React, { useEffect, useState } from "react";
import { Card, Table, ConfigProvider } from "antd";
import { useParams, useLocation } from "react-router-dom";
import { compareVersions } from "utils/RevisionApi";
import styles from "components/Docs/Diff.module.css"
import titleStyles from "./Content.module.css";

const Diff = () => {
  const params = useParams();
  const { state, search } = useLocation();
  const queryParams = new URLSearchParams(search);
  const [diffs, setDiffs] = useState([]);
  const [title, setTitle] = useState();

  useEffect(() => {
    const fetchData = async () => {
      const result = await compareVersions(state.oldRev, state.rev);
      setDiffs(result);
      setTitle(state.title);
    };
    fetchData();
  }, [state.oldRev, state.rev]);

  const diffsToLines = diffs.flatMap(diff => [
    ...diff.source.lines.map((line, index) => ({
      key: `source-${index}`,
      sourcePosition: diff.source.position + index + 1,
      targetPosition: null,
      line: line,
      type: 'source'
    })),
    ...diff.target.lines.map((line, index) => ({
      key: `target-${index}`,
      sourcePosition: null,
      targetPosition: diff.target.position + index + 1,
      line: line,
      type: 'target'
    }))
  ]);

  const columns = [
    {
      dataIndex: 'sourcePosition',
      key: 'sourcePosition',
      width: '5%',
      onCell: () => ({
        className: styles.CellPosition
      })
    },
    {
      dataIndex: 'targetPosition',
      key: 'targetPosition',
      width: '5%',
      onCell: () => ({
        className: styles.CellPosition
      })
    },
    {
      dataIndex: 'line',
      key: 'line',
      render: (text, record) => (
        <div style={{ wordBreak: 'break-all' }}>{text}</div>
      ),
      onCell: (record) => ({
        className: record.type === 'source' ? styles.CellSource : styles.CellTarget
      })
    },
  ];

  return (
    <div>
       <div className={titleStyles.contentTitle}>
        <h1 className={titleStyles.title}>
          {title}{" "}
          <small style={{ fontWeight: "normal" }}>(비교)</small>
        </h1>
      </div>
      <Card>
        <strong>r{queryParams.get('oldrev')} vs r{queryParams.get('rev')}</strong>
      </Card>
      <Card>
        <ConfigProvider
          theme={{
            components: {
              Table: {
                cellPaddingBlock: 10,
                cellPaddingInline: 10,
                rowHoverBg: null
              }
            }
          }}
        >
          <Table showHeader={false} dataSource={diffsToLines} columns={columns} pagination={false} bordered />
        </ConfigProvider>

      </Card>

    </div>
  );
};

export default Diff;
