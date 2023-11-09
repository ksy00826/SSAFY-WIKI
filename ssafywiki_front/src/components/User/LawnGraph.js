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
} from "antd";
import { getUserContribute, getUserContributeOneDay } from "utils/UserApi";
import { FileProtectOutlined, SearchOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
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
    margin: "3px 3px",
    background: "#EBF3E8",
  };

  const handleDocs = (docsId, title) => {
    console.log(docsId, title);
    navigate(`/res/content/${docsId}/${title}`);
  };

  const handleClickDay = (e) => {
    console.log(e.target.value);
    const date = e.target.value;
    setDay(date.substr(0, 10));
    getUserContributeOneDay(date).then((result) => {
      console.log(result);

      const chi = [];
      result.forEach((doc) => {
        chi.push({
          dot: <FileProtectOutlined style={{ color: "green" }} />,
          children: (
            <>
              <b>Create Document </b>
              <span style={{ fontSize: "smaller", color: "gray" }}>
                - {doc.createdAt.substr(12)}
              </span>
              <p>
                <Button
                  type="text"
                  onClick={() => handleDocs(doc.docsId, doc.title)}
                >
                  {doc.title}
                </Button>
              </p>
            </>
          ),
        });
      });
      setChildren(chi);
    });
  };
  //   React.useEffect(() => {
  //     console.log("ch", children);
  //   }, [children]);

  React.useEffect(() => {
    const now = new Date(); //현재 날짜 및 시간
    console.log("현재시간", now);
    const sixMonthAgo = new Date();
    sixMonthAgo.setDate(now.getDate() - now.getDay() - (weekAgo - 1) * 7);
    // console.log(now);
    console.log(sixMonthAgo);

    const curYear = sixMonthAgo.getFullYear();
    const curMonth = sixMonthAgo.getMonth() + 1;
    const curDay = sixMonthAgo.getDate();
    const day = curMonth + " / " + curDay;
    console.log(sixMonthAgo);

    const startDate =
      curYear +
      "-" +
      (Math.floor(curMonth / 10) == 0 ? "0" + curMonth : curMonth) +
      "-" +
      (Math.floor(curDay / 10) == 0 ? "0" + curDay : curDay);
    // console.log("go", startDate);

    if (loading) {
      getUserContribute(startDate + "T00:00:00").then((result) => {
        console.log(result);

        result.forEach((week) => {
          const row = [];
          week.forEach((cnt) => {
            //날짜 계산
            const curYear = sixMonthAgo.getFullYear();
            const curMonth = sixMonthAgo.getMonth() + 1;
            const curDay = sixMonthAgo.getDate();
            console.log(sixMonthAgo);

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
            console.log(sixMonthAgo);
            sixMonthAgo.setDate(sixMonthAgo.getDate() + 1); // 다음 날
          });
          rows.push(row);
        });
        console.log(rows);
        setLoading(false);
      });
    }
  }, []);

  return (
    <>
      <Card style={{ marginTop: 16 }} loading={loading}>
        <Row>
          <Col>{rows[0]}</Col>
          <Col>{rows[1]}</Col>
          <Col>{rows[2]}</Col>
          <Col>{rows[3]}</Col>
          <Col>{rows[4]}</Col>
          <Col>{rows[5]}</Col>
          <Col>{rows[6]}</Col>
          <Col>{rows[7]}</Col>
          <Col>{rows[8]}</Col>
          <Col>{rows[9]}</Col>
          <Col>{rows[10]}</Col>
          <Col>{rows[11]}</Col>
          <Col>{rows[12]}</Col>
          <Col>{rows[13]}</Col>
          <Col>{rows[14]}</Col>
          <Col>{rows[15]}</Col>
          <Col>{rows[16]}</Col>
          <Col>{rows[17]}</Col>
          <Col>{rows[18]}</Col>
          <Col>{rows[19]}</Col>
          <Col>{rows[20]}</Col>
          <Col>{rows[21]}</Col>
          <Col>{rows[22]}</Col>
        </Row>
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
//
