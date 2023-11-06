import React from "react";
import { Button, Modal, Card, Input, Row, Col, List, Skeleton } from "antd";
import MarkdownRenderer from "components/Common/MarkDownRenderer";

import { getTemplate, getTemplateDetail } from "utils/TemplateApi";

// const fakeDataUrl = `https://randomuser.me/api/?results=${count}&inc=name,gender,email,nat,picture&noinfo`;

const SearchTemplete = ({ next }) => {
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [templateContent, setTemplateContent] = React.useState("# 내용");
  const [templateTitle, setTemplateTitle] = React.useState("타이틀");

  const [initLoading, setInitLoading] = React.useState(true);
  const [loading, setLoading] = React.useState(false);
  const [data, setData] = React.useState([]);
  const [list, setList] = React.useState([]);
  const [count, setCount] = React.useState(0);

  const makeTemplate = () => {
    console.log("make로 이동");
  };
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    setIsModalOpen(false);
    next(templateContent);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  // 첫 랜더링시 초기 템플릿 가져오기
  React.useEffect(() => {
    getTemplate(count).then((res) => {
      setCount(count + 1);
      setData(res);
      setList(res);
      setInitLoading(false);
    });
  }, []);

  const onLoadMore = () => {
    setLoading(true);
    setList(
      data.concat(
        [...new Array(count * 2)].map(() => ({
          loading: true,
          name: {},
          picture: {},
        }))
      )
    );
    // fetch(fakeDataUrl)
    //   .then((res) => res.json())
    //   .then((res) => {
    //     const newData = data.concat(res.results);
    //     setData(newData);
    //     setList(newData);
    //     setLoading(false);
    //     // Resetting window's offsetTop so as to display react-virtualized demo underfloor.
    //     // In real scene, you can using public method of react-virtualized:
    //     // https://stackoverflow.com/questions/46700726/how-to-use-public-method-updateposition-of-react-virtualized
    //     window.dispatchEvent(new Event("resize"));
    //   });

    getTemplate(count).then((res) => {
      const newData = data.concat(res);
      setCount(count + 1);
      setData(newData);
      setList(newData);
      setLoading(false);
      setInitLoading(false);
      window.dispatchEvent(new Event("resize"));
    });
  };

  const showTemplate = (id) => {
    getTemplateDetail(id).then((res) => {
      setTemplateTitle(res.title);
      setTemplateContent(res.content);
      setIsModalOpen(true);
    });
  };

  const loadMore =
    !initLoading && !loading ? (
      <div
        style={{
          textAlign: "center",
          marginTop: 12,
          height: 32,
          lineHeight: "32px",
        }}
      >
        <Button onClick={onLoadMore}>더 보기</Button>
      </div>
    ) : null;

  return (
    <div>
      <h2>템플릿 선택</h2>
      <Button type="primary" onClick={next}>
        건너뛰기
      </Button>

      <Row>
        <Col flex={5}>
          <Input.Search placeholder="템플릿 검색" enterButton />
        </Col>
        <Col>
          <Button onClick={makeTemplate}>템플릿 만들기</Button>
        </Col>
      </Row>

      <List
        className="demo-loadmore-list"
        loading={initLoading}
        itemLayout="horizontal"
        loadMore={loadMore}
        dataSource={list}
        renderItem={(item) => (
          <List.Item
            actions={[
              <a key="list-loadmore-show" onClick={showTemplate}>
                미리보기
              </a>,
            ]}
          >
            <Skeleton title={false} loading={item.loading} active>
              <List.Item.Meta
                title={<a onClick={showTemplate}>{item.title}</a>}
                description={item.author}
              />
            </Skeleton>
          </List.Item>
        )}
      />

      <Modal
        title={templateTitle}
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={[
          <Button key="back" onClick={handleCancel}>
            취소
          </Button>,
          <Button key="submit" type="primary" onClick={handleOk}>
            선택
          </Button>,
        ]}
      >
        <Card>
          <MarkdownRenderer content={templateContent} />
        </Card>
      </Modal>
    </div>
  );
};

export default SearchTemplete;
