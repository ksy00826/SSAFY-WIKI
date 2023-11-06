import React, { useEffect, useState } from "react";
import { Card, Row, Col, Typography, Table } from "antd";
import { useParams, useLocation } from "react-router-dom";
import { compareVersions } from "utils/RevisionApi";


const Diff = () => {
  const params = useParams();
  const { state, search } = useLocation();
  const queryParams = new URLSearchParams(search);
  const { Text } = Typography;
  const [diffs, setDiffs] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const result = await compareVersions(state.oldRev, state.rev);
      setDiffs(result);
    }
    fetchData();
  }, [state.oldRev, state.rev]);

  const diffsTolines = diffs.map(diff => ({
    source: diff.source.lines.map((line, index) => ({
      position: diff.source.position + index + 1,
      line: line
    })),
    target: diff.target.lines.map((line, index) => ({
      position: diff.target.position + index + 1,
      line: line
    })),
    type: diff.type
  }));


  return (
    <div>
      <h1>{params.title}</h1>
      <Card>
        <strong>r{queryParams.get('oldrev')} vs r{queryParams.get('rev')}</strong>
      </Card>
      <table>
        <tbody>
          {diffsTolines.map((diff, idx) => (
            <>
              {diff.source.map((item) => (
                <tr>
                  <th className="ant-table-cell">{item.position}</th>
                  <th className="ant-table-cell"></th>
                  <td bgcolor="#FCD2C9">{item.line}</td>
                </tr>
              ))}
              {diff.target.map((item) => (
                <tr>
                  <th className="ant-table-cell"></th>
                  <th className="ant-table-cell">{item.position}</th>
                  <td bgcolor="#D8FCC9">{item.line}</td>
                </tr>
              ))}
            </>
          ))}
        </tbody>
      </table>
    </div >
  );
};

export default Diff;
