import React, { useEffect, useState } from "react";
import { Card, Table, ConfigProvider } from "antd";
import { useParams, useLocation } from "react-router-dom";
import { compareVersions } from "utils/RevisionApi";
import styles from "components/Docs/Diff.module.css"

const Diff = () => {
  const params = useParams();
  const { state, search } = useLocation();
  const queryParams = new URLSearchParams(search);
  const [diffs, setDiffs] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const result = await compareVersions(state.oldRev, state.rev);
      setDiffs(result);
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
        <div>{text}</div>
      ),
      onCell: (record) => ({
        className: record.type === 'source' ? styles.CellSource : styles.CellTarget
      })
    },
  ];

  return (
    <div>
      <h1>{params.title}</h1>
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
