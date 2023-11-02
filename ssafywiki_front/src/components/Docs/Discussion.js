import { useRef, useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import * as StompJs from '@stomp/stompjs';
import { getDiscussList } from "utils/DocsApi";
function Discussion() {
  const [chatList, setChatList] = useState([]);
  const [chat, setChat] = useState('');
  const params = useParams();
  const docsId = 1;
  const client = useRef({});

  useEffect(() => {
    getDiscussList(docsId).then((response) => {
      console.log(response);
      setChatList(response);
    });
  }, [params]);


  const connect = () => {
    client.current = new StompJs.Client({
      brokerURL: 'ws://localhost:8080/ws',
      onConnect: () => {
        console.log('success');
        subscribe();
      },
    });
    client.current.activate();
  };

  const publish = (chat) => {
    if (!client.current.connected) return;
    console.log('pub', chat, docsId)
    client.current.publish({
      destination: '/pub/chat',
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
    client.current.subscribe('/sub/chat/' + docsId, (body) => {
      const json_body = JSON.parse(body.body);
      setChatList((_chat_list) => [
        ..._chat_list, json_body
      ]);
    });
  };

  const disconnect = () => {
    client.current.deactivate();
  };

  const handleChange = (event) => { // 채팅 입력 시 state에 값 설정
    setChat(event.target.value);
  };

  const handleSubmit = (event, chat) => { // 보내기 버튼 눌렀을 때 publish
    event.preventDefault();
    console.log('버튼', chat)
    publish(chat);
  };
  
  useEffect(() => {
    connect();

    return () => disconnect();
  }, []);

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