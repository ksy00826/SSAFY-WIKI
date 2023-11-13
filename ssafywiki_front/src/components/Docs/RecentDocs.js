import * as StompJs from "@stomp/stompjs";
import { useRef, useState, useEffect } from "react";
import { subscribeRecentDocs, getRecentDocsList } from "utils/DocsApi";
import { Divider } from "antd";
import styles from "./RecentDocs.module.css";
import { Link } from "react-router-dom";

const RecentDocs = () => {
  const [recentDocsList, setRecentDocsList] = useState([]);
  const client = useRef({});
  useEffect(() => {
    connect();
    //console.log("");
    getRecentDocsList().then((response) => {
      setRecentDocsList(response);
    });
    return () => disconnect();
  }, []);

  const connect = () => {
    client.current = new StompJs.Client({
      brokerURL: process.env.REACT_APP_SERVER_WS_URL,
      onConnect: () => {
        //console.log("Connected");
        subscribe();
      },
    });
    client.current.activate();
  };
  const subscribe = () => {
    //console.log("subRecent");
    subscribeRecentDocs();
    client.current.subscribe("/sub/recent/", (body) => {
      const json_body = JSON.parse(body.body);
      setRecentDocsList((prevRecentDocsList) => {
        // 이미 존재하는 docsId를 가진 메시지 제거
        const updatedList = prevRecentDocsList.filter(
          (message) => message.docsId !== json_body.docsId
        );
        // 새로운 메시지를 가장 앞에 추가
        updatedList.unshift(json_body);
        // 최대 개수를 초과하는 경우 마지막 메시지 제거
        if (updatedList.length > 10) {
          updatedList.pop();
        }
        return updatedList;
      });
    });
  };

  const disconnect = () => {
    if (typeof client.current.deactivate === "function") {
      client.current.deactivate();
    } else {
      console.error("deactivate method is not available on the client object");
    }
  };

  const formatTime = (dateString, flag) => {
    const date = new Date(dateString);

    // 한 자리 숫자일 경우 앞에 '0'을 붙여줌
    if (flag === "time") {
      let hours = date.getHours();
      let minutes = date.getMinutes();
      hours = hours < 10 ? `0${hours}` : hours;
      minutes = minutes < 10 ? `0${minutes}` : minutes;

      return `${hours}:${minutes}`;
    } else {
      let year = date.getFullYear();
      let month = date.getMonth();
      let day = date.getDay();
      day = day < 10 ? `0${day}` : day;
      return `${year}-${month}-${day}`;
    }
  };

  return (
    <div className={styles.RecentBox}>
      {recentDocsList.map((recentDocs, index) => (
        <div key={index}>
          <Divider style={{ margin: "0" }} />
          <Link to={`/res/content/${recentDocs.docsId}/${recentDocs.title}`}>
            <div className={styles.RecentTitle}>
              <span className={styles.Title}>{recentDocs.title}</span>
              <span className={styles.Time}>
                {formatTime(recentDocs.modifiedAt, "time")}
              </span>
            </div>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default RecentDocs;
