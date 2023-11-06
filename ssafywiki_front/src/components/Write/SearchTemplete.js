import React from "react";
import { AndroidOutlined, AppleOutlined } from "@ant-design/icons";
import {
  Button,
  Modal,
  Card,
  Input,
  Row,
  Col,
  List,
  Skeleton,
  Tabs,
} from "antd";
import MarkdownRenderer from "components/Common/MarkDownRenderer";

import { getTemplate, getTemplateDetail } from "utils/TemplateApi";

const SearchTemplete = ({ next }) => {
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [templateContent, setTemplateContent] = React.useState("# 내용");
  const [templateTitle, setTemplateTitle] = React.useState("타이틀");

  const [initLoading, setInitLoading] = React.useState(true);
  const [loading, setLoading] = React.useState(false);
  const [templateData, setData] = React.useState([]);
  const [templateList, setList] = React.useState([]);
  const [pageNum, setPageNum] = React.useState(0);
  const [lastPage, setLastPage] = React.useState(false);

  const [activeKey, setActiveKey] = React.useState(1);

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
    getTemplate(pageNum, activeKey == 1 ? true : false).then((res) => {
      setPageNum(pageNum + 1);
      setData(res);
      setList(res);
      setInitLoading(false);
      setLastPage(false);
    });
  }, []);

  //탭 바꾸면 템플릿 가져오기
  const changeTap = (key) => {
    console.log("key", key);
    getTemplate(0, key == 1 ? true : false).then((res) => {
      setPageNum(1); //페이지 초기화
      setData(res);
      setList(res);
      setLastPage(false);

      //비동기적 실행
      setActiveKey(key);
      console.log("change", activeKey);
    });
    getTemplate(1, key == 1 ? true : false).then((res) => {
      console.log(res == []);
      if (res == []) setLastPage(true);
    });
  };

  const onLoadMore = () => {
    setLoading(true);

    getTemplate(pageNum, activeKey == 1 ? true : false).then((res) => {
      console.log(res == []);
      if (res == []) setLastPage(true);
      const newData = templateData.concat(res);
      setPageNum(pageNum + 1);
      setData(newData);
      setList(newData);
      setLoading(false);
      window.dispatchEvent(new Event("resize"));
    });
    getTemplate(pageNum + 1, activeKey == 1 ? true : false).then((res) => {
      console.log(res == []);
      if (res == []) setLastPage(true);
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
    !initLoading && !loading && !lastPage ? (
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

      <Tabs
        activeKey={activeKey}
        defaultActiveKey="1"
        onChange={changeTap}
        items={[AppleOutlined, AndroidOutlined].map((Icon, i) => {
          const taps = ["", "My Template", "Others"];
          i++;
          return {
            label: (
              <span>
                <Icon />
                {taps[i]}
              </span>
            ),
            key: i,
            children: (
              <List
                className="demo-loadmore-list"
                loading={initLoading}
                itemLayout="horizontal"
                loadMore={loadMore}
                dataSource={templateList}
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
            ),
          };
        })}
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
