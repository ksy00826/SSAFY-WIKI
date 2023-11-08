import React, { useEffect } from "react";
import {
  AndroidOutlined,
  AppleOutlined,
  UnlockOutlined,
  LockFilled,
} from "@ant-design/icons";
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
import { useNavigate } from "react-router-dom";

import {
  getTemplate,
  getTemplateDetail,
  getTemplateList,
  deleteTemplate,
  changeTemplateAuthority,
} from "utils/TemplateApi";
// import { openNotification } from "App";

const SearchTemplete = ({ next, title }) => {
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [templateContent, setTemplateContent] = React.useState("# 내용");
  const [templateTitle, setTemplateTitle] = React.useState("타이틀");
  const [templateId, setTemplateId] = React.useState("템플릿 아이디");

  const [initLoading, setInitLoading] = React.useState(true);
  const [loading, setLoading] = React.useState(false);
  const [templateData, setData] = React.useState([]);
  const [templateList, setList] = React.useState([]);
  const [pageNum, setPageNum] = React.useState(0);
  const [lastPage, setLastPage] = React.useState(false);

  const [activeKey, setActiveKey] = React.useState(1);
  const [searchKeyword, setSearchKeyword] = React.useState("");
  const navigate = useNavigate();

  const makeTemplate = () => {
    // console.log("make로 이동");
    navigate(`/wrt/template?title=${title}`);
  };
  const handleOk = () => {
    setIsModalOpen(false);
    next(templateContent);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };
  const handleDelete = () => {
    setIsModalOpen(false);

    // console.log("delete", templateId);
    deleteTemplate(templateId).then(() => {
      window.location.reload();
      // setTimeout(() => {
      //   openNotification(
      //     "success",
      //     "템플릿 삭제 완료",
      //     `${templateTitle} 템플릿이 삭제되었습니다.`
      //   );
      // }, 5000);
    });
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
    // console.log("key", key);
    // console.log("changeTab", searchKeyword);

    getTemplateList(0, key == 1 ? true : false, searchKeyword).then((res) => {
      setPageNum(1); //페이지 초기화
      setData(res);
      setList(res);
      setLastPage(false);

      //비동기적 실행
      setActiveKey(key);
      // console.log("change", activeKey);
      // console.log(res);

      getTemplateList(1, key == 1 ? true : false, searchKeyword).then(
        (res2) => {
          if (res2.length == 0) setLastPage(true);
        }
      );
    });
  };

  useEffect(() => {
    // console.log("useEffect: ", lastPage);
  }, [lastPage]);

  const onLoadMore = () => {
    setLoading(true);
    // console.log("onLoadMore", searchKeyword, pageNum);

    getTemplateList(pageNum, activeKey == 1 ? true : false, searchKeyword).then(
      (res) => {
        const newData = templateData.concat(res);
        setPageNum(pageNum + 1);
        setData(newData);
        setList(newData);
        // setLoading(false);
        window.dispatchEvent(new Event("resize"));
        // console.log(res);

        getTemplateList(
          pageNum + 1, //비동기로 나중에 바뀌기 때문에 +1
          activeKey == 1 ? true : false,
          searchKeyword
        ).then((res2) => {
          // console.log(res2.length == 0);
          // console.log(res2);
          if (res2.length == 0) setLastPage(true);
        });
      }
    );
  };

  const showTemplate = (templateId) => {
    // console.log(templateId);
    getTemplateDetail(templateId).then((res) => {
      // console.log(res);
      setTemplateId(res.templateId);
      setTemplateTitle(res.title);
      setTemplateContent(res.content);
      setIsModalOpen(true);
    });
  };

  const onSearch = (value) => {
    // console.log(value, _e, info); e(에러), info(입력 소스?)

    getTemplateList(0, activeKey == 1 ? true : false, value).then((res) => {
      // const newData = templateData.concat(res);
      setPageNum(1);
      setData(res);
      setList(res);
      setLastPage(false);
      setLoading(false);
      // console.log(res);

      getTemplateList(1, activeKey == 1 ? true : false, value).then((res2) => {
        // console.log(res2.length == 0);
        if (res2.length == 0) setLastPage(true);
      });
    });

    setSearchKeyword(value);
  };
  const changeKeyword = (e) => {
    // console.log(e.target.value);
    const value = e.target.value;
    getTemplateList(0, activeKey == 1 ? true : false, value).then((res) => {
      // const newData = templateData.concat(res);
      setPageNum(1);
      setData(res);
      setList(res);
      setLastPage(false);
      setLoading(false);
      // console.log(res);

      getTemplateList(1, activeKey == 1 ? true : false, value).then((res2) => {
        // console.log(res2.length == 0);
        if (res2.length == 0) setLastPage(true);
      });
    });

    setSearchKeyword(value);
  };
  //!initLoading && !loading &&
  const loadMore = !lastPage ? (
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

  const changeAuthority = (item) => {
    if (
      item != undefined &&
      item.templateId != undefined &&
      item.secret != undefined
    ) {
      // console.log(item);
      changeTemplateAuthority(item.templateId, !item.secret, pageNum).then(
        (res) => {
          // console.log(res);
          setData(res);
          setList(res);
        }
      );
    }
  };

  return (
    <div>
      <Row>
        <Col flex={8}>
          <h2>템플릿 선택</h2>
        </Col>
        <Col>
          <Button type="primary" onClick={next}>
            건너뛰기
          </Button>
        </Col>
      </Row>

      <Row>
        <Col flex={5}>
          <Input.Search
            placeholder="템플릿 검색"
            enterButton
            onSearch={onSearch}
            onChange={changeKeyword}
          />
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
                      <a
                        key="list-loadmore-show"
                        onClick={() => showTemplate(item.templateId)}
                      >
                        미리보기
                      </a>,
                    ]}
                  >
                    <Skeleton title={false} loading={item.loading} active>
                      <List.Item.Meta
                        title={<a onClick={showTemplate}>{item.title}</a>}
                        description={item.author}
                      />
                      {activeKey === 1 ? (
                        <Button
                          type="default"
                          icon={
                            item.secret ? <LockFilled /> : <UnlockOutlined />
                          }
                          // loading={loadings[2]}
                          onClick={() => changeAuthority(item)}
                        />
                      ) : (
                        <></>
                      )}
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
        footer={
          activeKey === 1
            ? [
                <Button type="primary" danger onClick={handleDelete}>
                  삭제
                </Button>,
                <Button key="submit" type="primary" onClick={handleOk}>
                  선택
                </Button>,
              ]
            : [
                <Button key="submit" type="primary" onClick={handleOk}>
                  선택
                </Button>,
              ]
        }
      >
        <Card>
          <MarkdownRenderer content={templateContent} />
        </Card>
      </Modal>
    </div>
  );
};

export default SearchTemplete;
