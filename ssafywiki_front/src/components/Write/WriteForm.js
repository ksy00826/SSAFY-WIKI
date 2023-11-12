import { useState, useRef, useEffect, Component } from "react";
import { Input, Tabs, Button } from "antd";
import styles from "./WriteForm.module.css";
import MarkdownRenderer from "components/Common/MarkDownRenderer";

const { TextArea } = Input;

const WriteForm = ({
  content,
  setContent,
  isdisabled,
  setIsError,
  viewType,
  setViewType,
}) => {
  const changeContent = (e) => {
    // console.log(e.target.value);
    setContent(e.target.value);
  };

  const changeViewType = (e) => {
    setViewType(e);
  };

  const textAreaRef = useRef(null);
  const insertAtCursor = (text) => {
    try {
      const textArea = textAreaRef.current.resizableTextArea.textArea;
      const start = textArea.selectionStart;
      const end = textArea.selectionEnd;
      const before = content.substring(0, start);
      const after = content.substring(end);
      setContent(before + text + after);
      // 커서를 삽입된 텍스트 뒤로 이동시키기 위한 setTimeout
      setTimeout(() => {
        textArea.selectionStart = textArea.selectionEnd = start + text.length;
        textArea.focus();
      }, 0);
    } catch {}
  };

  class ErrorBoundary extends Component {
    constructor(props) {
      super(props);
      this.state = { hasError: false, error: null };
    }

    static getDerivedStateFromError(error) {
      // 다음 렌더링에서 fallback UI를 표시하기 위한 상태 업데이트
      return { hasError: true, error };
    }

    render() {
      if (this.state.hasError) {
        // 여기서 에러 UI를 커스터마이징 할 수 있습니다.
        setIsError(true);
        return (
          <p>
            문서에 문법 오류가 있습니다. `&lt;` 또는 `&gt;` 사용시 오류가 발생
            할 수 있습니다. 문법을 수정해주세요.
          </p>
        );
      }
      setIsError(false);
      return this.props.children;
    }
  }

  return (
    <div
      style={{
        textAlign: "left",
      }}
    >
      <div className={styles.Box}>
        <Tabs
          items={[
            {
              label: "RAW",
              key: "1",
            },
            {
              label: "문서확인",
              key: "2",
            },
          ]}
          onChange={(e) => {
            changeViewType(e);
          }}
          type="card"
        />
        <div className={styles.TabBox}>
          <Button
            type="text"
            className={styles.Button}
            onClick={() => insertAtCursor('<LinkTo link=""></LinkTo>')}
          >
            외부링크
          </Button>
          <Button
            type="text"
            className={styles.Button}
            onClick={() => insertAtCursor('<MoveDocs docs=""></MoveDocs>')}
          >
            문서이동
          </Button>
          <Button
            type="text"
            className={styles.Button}
            onClick={() => insertAtCursor("<Note></Note>")}
          >
            주석
          </Button>
        </div>
      </div>
      {viewType == 1 ? (
        <TextArea
          rows={4}
          // defaultValue={content}
          value={content}
          autoSize={{
            minRows: 12,
          }}
          onChange={changeContent}
          readOnly={isdisabled}
          ref={textAreaRef}
        />
      ) : (
        <ErrorBoundary>
          <MarkdownRenderer content={content}></MarkdownRenderer>
        </ErrorBoundary>
      )}
    </div>
  );
};

export default WriteForm;
