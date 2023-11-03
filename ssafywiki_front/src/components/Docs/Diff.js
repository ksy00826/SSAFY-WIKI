import React, { useEffect, useState } from "react";
import { Card, Row, Col, Typography } from "antd";
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

  const setTitle = () => (
    <strong>r{queryParams.get('oldrev')} vs r{queryParams.get('rev')}</strong>
  );

  const getDiffStyle = (type) => {
    switch (type) {
      case 'CHANGE':
        return { backgroundColor: '#ffffdd' };
      case 'DELETE':
        return { backgroundColor: '#ffdddd', textDecoration: 'line-through' };
      case 'INSERT':
        return { backgroundColor: '#ddffdd' };
      default:
        return {};
    }
  };


  return (
    <div>
      <h1>{params.title}</h1>
      <Card>
        <strong>r{queryParams.get('oldrev')} vs r{queryParams.get('rev')}</strong>
      </Card>
      
      <Row gutter={16}>
        <Col span={12}>
          {diffs.map((diff, index) => (
            <div key={`old-${index}`} style={getDiffStyle(diff.type)}>
              <Text delete={diff.type === 'DELETE'}>{diff.source?.lines.join('\n') || ''}</Text>
            </div>
          ))}
        </Col>
        <Col span={12}>
          {diffs.map((diff, index) => (
            <div key={`new-${index}`} style={getDiffStyle(diff.type)}>
              <Text>{diff.target?.lines.join('\n') || ''}</Text>
            </div>
          ))}
        </Col>
      </Row>

    </div >
  );
};

export default Diff;
