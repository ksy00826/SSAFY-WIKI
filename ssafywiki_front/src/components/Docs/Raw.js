import { Button, Card, Flex } from "antd";
import TextArea from "antd/es/input/TextArea";
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { getDocsVersionContent } from "utils/DocsApi"

const Raw = () => {
  const params = useParams();
  const { state, search } = useLocation();
  const queryParams = new URLSearchParams(search);
  const navigate = useNavigate();

  const [content, setContent] = useState();
  const [title, setTitle] = useState();

  useEffect(() => {
    getDocsVersionContent(state.docsId, state.revId)
      .then((response) => {
        console.log(response.content);
        setContent(response.content);
        setTitle(response.title);
      })
      .catch()
  }, [params]);

  const toHistory = () => {
    navigate(`/res/history/${state.docsId}/${params.title}`);
  }

  return (
    <div>
      <h1>{title} <small style={{ fontWeight: "normal" }}>(r{queryParams.get("rev")} RAW)</small></h1>
      <Flex justify="end" style={{marginBottom: "16px"}}>
        <Button onClick={toHistory}>역사</Button>
      </Flex>

      <TextArea style={{ color: "black" }} disabled={true} value={content} rows="15">
      </TextArea>
    </div>
  );
};

export default Raw;
