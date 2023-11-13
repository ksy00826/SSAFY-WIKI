import { useRef, useState, useEffect } from "react";
import * as StompJs from "@stomp/stompjs";
import { getDiscussList } from "utils/DocsApi";
import { useLocation } from "react-router-dom";
import cookie from "react-cookies";
import styles from "./Discussion.module.css";
import { getUserInfo } from "utils/UserApi";
import { Button, Input, Space } from "antd";
import { WechatOutlined } from "@ant-design/icons";
function Discussion() {
  const [chatList, setChatList] = useState([]);
  const [chat, setChat] = useState("");
  const [docsId, setDocsId] = useState(1);
  const location = useLocation();
  const client = useRef({});
  const [token, setToken] = useState(cookie.load("token"));
  const scrollRef = useRef(null);
  const [email, setEmail] = useState("");

  useEffect(() => {
    // URL 경로를 분석하여 docsId를 추출합니다.
    setToken(cookie.load("token"));
    if (token) {
      getUserInfo().then((response) => {
        setEmail(response.email);
        console.log("유저정보", response);
      });
    }
    const match = location.pathname.match(
      /\/(history|edit|auth|content)\/(\d+)\/?/
    );
    var currentDocsId = 1;
    // 현재 문서 ID를 추출합니다.
    // 현재 문서 ID를 사용하여 토론 목록을 가져옵니다.
    if (match && match[2]) {
      currentDocsId = parseInt(match[2], 10);
    }
    getDiscussList(currentDocsId).then((response) => {
      console.log("응답", currentDocsId, response);
      setDocsId(currentDocsId);
      setChatList(response);
    });
    connect(currentDocsId);

    return () => disconnect();
  }, [location]);

  useEffect(() => {
    // 채팅 목록의 변화를 감지하고 스크롤을 맨 아래로 이동시키는 함수
    const scrollToBottom = () => {
      if (scrollRef.current) {
        scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
      }
    };

    // 첫 마운트시 스크롤 맨 아래로 이동
    scrollToBottom();

    // 채팅 목록의 변화가 있을 때마다 스크롤을 맨 아래로 이동
    const chatListLength = chatList.length;
    if (chatListLength > 0) {
      scrollToBottom();
    }
  }, [chatList]);

  const connect = (currentDocsId) => {
    if (!token) {
      console.error("인증 토큰이 누락되었습니다");
      return;
    }
    console.log(token);
    client.current = new StompJs.Client({
      brokerURL: process.env.REACT_APP_SERVER_WS_URL,
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      onConnect: () => {
        console.log("Connected");
        subscribe(currentDocsId);
      },
      onStompError: (frame) => {
        // 오류 처리
        console.error(frame);
      },
      webSocketFactory: () => {
        // `withCredentials`를 true로 설정하여 쿠키를 포함시키는 WebSocket 인스턴스를 생성합니다.
        return new WebSocket(process.env.REACT_APP_SERVER_WS_URL, [], {
          withCredentials: true,
        });
      },
    });
    client.current.activate();
  };

  const publish = (chat) => {
    if (!client.current.connected || chat.length == 0) return;
    console.log("pub", chat, docsId, email);
    client.current.publish({
      destination: "/pub/chat",
      headers: { Authorization: `Bearer ${token}` },
      body: JSON.stringify({
        nickname: null,
        docsId: docsId,
        content: chat,
      }),
    });

    setChat("");
  };

  const subscribe = (currentDocsId) => {
    console.log("sub", currentDocsId);
    client.current.subscribe("/sub/chat/" + currentDocsId, (body) => {
      const json_body = JSON.parse(body.body);
      setChatList((_chat_list) => [..._chat_list, json_body]);
      console.log(body.body);
    });
  };

  const disconnect = () => {
    if (typeof client.current.deactivate === "function") {
      client.current.deactivate();
    } else {
      console.error("deactivate method is not available on the client object");
    }
  };

  const handleChange = (event) => {
    // 채팅 입력 시 state에 값 설정
    setChat(event.target.value);
  };

  const handleSubmit = (event, chat) => {
    // 보내기 버튼 눌렀을 때 publish
    event.preventDefault();
    console.log("버튼", chat);
    console.log("주소", docsId);
    publish(chat);
  };

  const formatTime = (dateString, flag) => {
    const date = new Date(dateString);
    console.log(date);
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
      let day = date.getDate();
      day = day < 10 ? `0${day}` : day;
      console.log("날짜", date, month, day);
      return `${year}-${month + 1}-${day}`;
    }
  };
  return (
    <div>
      <div className={styles.ChatCard} ref={scrollRef}>
        {chatList.map((chatMessage, index) => (
          <div key={index}>
            {(index === 0 ||
              formatTime(chatList[index - 1].createdAt, "date") !==
                formatTime(chatMessage.createdAt, "date")) && (
              <div className={styles.DateBox}>
                <span className={styles.Date}>
                  {formatTime(chatMessage.createdAt, "date")}
                </span>
                <hr />
              </div>
            )}
            {(index === 0 ||
              chatList[index - 1].nickname !== chatMessage.nickname) && (
              <p
                className={
                  email === chatMessage.email
                    ? styles.NicknameRight
                    : styles.Nickname
                }
              >
                {chatMessage.nickname}
              </p>
            )}
            <div
              className={
                email === chatMessage.email
                  ? styles.DiscussionDivRight
                  : styles.DiscussionDiv
              }
            >
              <div
                className={
                  email === chatMessage.email
                    ? styles.DiscussionBoxRight
                    : styles.DiscussionBox
                }
              >
                {chatMessage.content}
              </div>
              <div className={styles.Time}>
                {formatTime(chatMessage.createdAt, "time")}
              </div>
            </div>
          </div>
        ))}
      </div>
      <form onSubmit={(event) => handleSubmit(event, chat)}>
        <Space.Compact style={{ width: "100%" }}>
          <Input
            type={"text"}
            name={"chatInput"}
            onChange={handleChange}
            value={chat}
          />
          <Button type="default" htmlType="submit">
            <WechatOutlined />
          </Button>
        </Space.Compact>
      </form>
    </div>
  );
}

export default Discussion;
