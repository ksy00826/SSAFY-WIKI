import { useRef, useState, useEffect } from 'react';
import * as StompJs from '@stomp/stompjs';
import { getDiscussList } from "utils/DocsApi";
import { useLocation } from 'react-router-dom';
import cookie from "react-cookies";

function Discussion() {
  const [chatList, setChatList] = useState([]);
  const [chat, setChat] = useState('');
  const [docsId, setDocsId] = useState(1);
  const location = useLocation();
  const client = useRef({});
  const token = cookie.load('token');

  useEffect(() => {
    // URL 경로를 분석하여 docsId를 추출합니다.
    const match = location.pathname.match(/\/(history|edit|auth|content)\/(\d+)\/?/);
    if (match && match[2]) {
      const currentDocsId = parseInt(match[2], 10); // 현재 문서 ID를 추출합니다.
      // 현재 문서 ID를 사용하여 토론 목록을 가져옵니다.
      getDiscussList(currentDocsId).then((response) => {
        console.log("응답", currentDocsId, response);
        setDocsId(currentDocsId);
        setChatList(response);
      });
      connect();
    }
    return () => disconnect();
  }, [location]);


  const connect = () => {

    if (!token) {
      console.error('인증 토큰이 누락되었습니다');
      return;
    }
    console.log(token);
    client.current = new StompJs.Client({
      brokerURL: process.env.REACT_APP_SERVER_WS_URL,
      connectHeaders: {
        'Authorization': `Bearer ${token}`,
      },
      onConnect: () => {
        console.log('Connected');
        subscribe();
      },
      onStompError: (frame) => {
        // 오류 처리
        console.error(frame);
      },
      webSocketFactory: () => {
        // `withCredentials`를 true로 설정하여 쿠키를 포함시키는 WebSocket 인스턴스를 생성합니다.
        return new WebSocket(process.env.REACT_APP_SERVER_WS_URL, [], { withCredentials: true });
      }
    });
    client.current.activate();
  };

  const publish = (chat) => {
    if (!client.current.connected || chat.length == 0) return;
    console.log('pub', chat, docsId)
    client.current.publish({
      destination: '/pub/chat',
      headers: { 'Authorization': `Bearer ${token}` },
      body: JSON.stringify({
        "nickname" : null,
        "docsId" : docsId,
        "content" : chat,
        "createdAt" : null
      }),
    });

    setChat('');
  };

  const subscribe = () => {
    console.log('sub', docsId);
    client.current.subscribe('/sub/chat/' + docsId, (body) => {
      const json_body = JSON.parse(body.body);
      setChatList((_chat_list) => [
        ..._chat_list, json_body
      ]);
    });
  };

  const disconnect = () => {
    if (typeof client.current.deactivate === 'function') {
      client.current.deactivate();
    } else {
      console.error('deactivate method is not available on the client object');
    }
  };

  const handleChange = (event) => { // 채팅 입력 시 state에 값 설정
    setChat(event.target.value);
  };

  const handleSubmit = (event, chat) => { // 보내기 버튼 눌렀을 때 publish
    event.preventDefault();
    console.log('버튼', chat)
    console.log("주소", docsId)
    publish(chat);
  };
  


  return (
    <div>
      <div className={'chat-list'}>
        {chatList.map((chatMessage, index) => (
          <div key={index}>
            <p>{chatMessage.nickname}: {chatMessage.content}</p>
          </div>
        ))}
      </div>
      <form onSubmit={(event) => handleSubmit(event, chat)}>
        <div>
          <input type={'text'} name={'chatInput'} onChange={handleChange} value={chat} />
        </div>
        <input type={'submit'} value={'의견 보내기'} />
      </form>
    </div>
  );
}

export default Discussion;