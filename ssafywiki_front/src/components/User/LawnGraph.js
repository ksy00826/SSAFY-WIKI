import React, { useState } from "react";
import {
  Col,
  Row,
  Slider,
  Button,
  Tooltip,
  Divider,
  Timeline,
  Card,
  Avatar,
  Space,
  Flex,
} from "antd";
import { getUserContribute, getUserContributeOneDay } from "utils/UserApi";
import { FileProtectOutlined, SearchOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
// import styled from "styled-components";
// import "./LawnGraph.css"; // CSS 파일 임포트
const { Meta } = Card;
const LawnGraph = () => {
  const [cols, setCols] = React.useState([]);
  const [rows, setRows] = React.useState([]);
  const [loading, setLoading] = React.useState(true);
  const [children, setChildren] = React.useState([]);
  const [day, setDay] = React.useState([]);
  const navigate = useNavigate();
  const weekAgo = 6 * 4 - 1;

  const colors = ["#EBF3E8", "#D2E3C8", "#B2C8BA", "#86A789"];

  const buttonStyle = {
    minWidth: "0",
    minHeight: "0",
    // padding: "10px 10px 10px 10px", // 원하는 패딩 값을 설정
    margin: "2px 2px",
    background: "#EBF3E8",
  };

  const handleDocs = (docsId, title) => {
    //console.log(docsId, title);
    navigate(`/res/history/${docsId}/${title}`);
  };

  const handleClickDay = (e) => {
    //console.log(e.target.value);
    const date = e.target.value;
    setDay(date.substr(0, 10));
    setChildren([]);
    getUserContributeOneDay(date).then((result) => {
      //console.log(result);
      // //console.log(result[0].revisions);

      const chi = result.map((doc, index) => {
        //console.log(doc.revisions);
        const btn = doc.revisions.map((rev, revIndex) => {
          return (
            <>
              <br></br>
              <Button
                key={`${revIndex}`}
                type="text"
                onClick={() => handleDocs(doc.docsId, doc.title)}
              >
                {rev.revisionComment ? (
                  rev.revisionComment
                ) : (
                  <span style={{ color: "gray" }}> No comment</span>
                )}
              </Button>
              <span style={{ fontSize: "smaller", color: "gray" }}>
                - {rev.createdAt.substr(11)}
              </span>
            </>
          );
        });
        //console.log("btn", btn);

        return {
          dot: <FileProtectOutlined style={{ color: "green" }} />,
          children: (
            <>
              <b>Contribute Document - {doc.title}</b>
              {btn}
            </>
          ),
        };
      });
      setChildren(chi);
    });
  };
  React.useEffect(() => {
    //console.log("~~", children);
  }, [children]);

  React.useEffect(() => {
    const now = new Date(); //현재 날짜 및 시간
    // //console.log("현재시간", now);
    const sixMonthAgo = new Date();
    sixMonthAgo.setDate(now.getDate() - now.getDay() - (weekAgo - 1) * 7);
    // //console.log(now);
    // //console.log(sixMonthAgo);

    const curYear = sixMonthAgo.getFullYear();
    const curMonth = sixMonthAgo.getMonth() + 1;
    const curDay = sixMonthAgo.getDate();
    const day = curMonth + " / " + curDay;
    // //console.log(sixMonthAgo);

    const startDate =
      curYear +
      "-" +
      (Math.floor(curMonth / 10) == 0 ? "0" + curMonth : curMonth) +
      "-" +
      (Math.floor(curDay / 10) == 0 ? "0" + curDay : curDay);
    // //console.log("go", startDate);

    if (loading) {
      setRows([]);
      getUserContribute(startDate + "T00:00:00").then((result) => {
        // //console.log(result);
        const rowss = [];
        result.forEach((week) => {
          const row = [];
          week.forEach((cnt) => {
            //날짜 계산
            const curYear = sixMonthAgo.getFullYear();
            const curMonth = sixMonthAgo.getMonth() + 1;
            const curDay = sixMonthAgo.getDate();
            // //console.log(sixMonthAgo);

            const date =
              curYear +
              "-" +
              (Math.floor(curMonth / 10) == 0 ? "0" + curMonth : curMonth) +
              "-" +
              (Math.floor(curDay / 10) == 0 ? "0" + curDay : curDay);

            //색상 계산
            let color = "white";
            if (cnt >= 1 && cnt <= 3) color = "#EBF3E8";
            else if (cnt >= 4 && cnt <= 6) color = "#D2E3C8";
            else if (cnt >= 7 && cnt <= 15) color = "#B2C8BA";
            else if (cnt >= 16) color = "#86A789";
            row.push(
              <Row>
                <Tooltip title={cnt + " contribute"} color="black">
                  <Button
                    style={{
                      ...buttonStyle,
                      background: color,
                    }}
                    value={date + "T00:00:00"}
                    onClick={handleClickDay}
                  />
                </Tooltip>
              </Row>
            );
            // //console.log(sixMonthAgo);
            sixMonthAgo.setDate(sixMonthAgo.getDate() + 1); // 다음 날
          });
          rowss.push(row);
        });
        // //console.log(rows);
        setLoading(false);
        setRows(rowss);
      });
    }
  }, []);

  const cardStyle = {
    height: "320px",
    width: "900px",
    // overflow: "auto" /* 내용이 넘칠 때 스크롤바 자동 생성 */,
    overflowX: "auto",
    overflowY: "hidden",
  };

  // const ScrollStyleComponent = styled.nav`
  //   display: flex;
  //   overflow: auto;
  //   height: 45px;
  //   &::-webkit-scrollbar {
  //     width: 8px;
  //     height: 8px;
  //     border-radius: 6px;
  //     background: rgba(255, 255, 255, 0.4);
  //   }
  //   &::-webkit-scrollbar-thumb {
  //     background: rgba(0, 0, 0, 0.3);
  //     border-radius: 6px;
  //   }
  // `;
  // const ScrollStyleRow = styled(Row)`
  //   display: flex;
  //   flex-wrap: nowrap;
  //   overflow-x: auto;
  //   overflow-y: hidden;
  //   &::-webkit-scrollbar {
  //     width: 8px;
  //     height: 8px;
  //     border-radius: 6px;
  //     background: rgba(255, 255, 255, 0.4);
  //   }
  //   &::-webkit-scrollbar-thumb {
  //     background: rgba(0, 0, 0, 0.3);
  //     border-radius: 6px;
  //   }
  // `;

  return (
    <>
      <Card loading={loading} style={cardStyle}>
        {!loading && (
          <Row
            style={{
              display: "flex",
              flexWrap: "nowrap",
              overflowX: "auto",
              overflowY: "hidden",
            }}
          >
            {rows.map((row, index) => (
              <Col key={index}>{row}</Col>
            ))}
          </Row>
        )}
      </Card>
      <Divider orientation="left" orientationMargin="0">
        <b>Timeline</b>
        <i> {day != "" ? " - " + day : ""}</i>
        <br></br>
      </Divider>

      {children.length != 0 ? (
        <>
          <Timeline items={children} style={{ textAlign: "left" }} />
        </>
      ) : (
        <>No Contribute</>
      )}
    </>
  );
};
export default LawnGraph;
