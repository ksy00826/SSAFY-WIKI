import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Layout, theme, List, Flex, Button } from "antd";
import VirtualList from 'rc-virtual-list';
import UserNavbar from "components/Common/UserNavbar";
import { getDocumentReport } from "utils/ReportApi"


const { Header, Content, Footer } = Layout;

const DocumentReport = () => {
    const {
        token: { colorBgContainer },
    } = theme.useToken();

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState(1);

    const appendData = () => {
        setLoading(true);
        getDocumentReport(page - 1, 10).then((response) => {
            console.log(response);
            if (response.content != null) {
                setData((prevData) => {
                    const newData = response.content.filter(item => {
                        return !prevData.some(prevItem => prevItem.id === item.id);
                    })
                    return [...prevData, ...newData];
                });
                setPage(page+1);
                console.log(page);
            }
            setLoading(false);
        })
    }

    const handleScroll = (event) => {
        const { scrollHeight , scrollTop , clientHeight } = event.target;
        if (clientHeight + scrollTop >= scrollHeight - 5) { // 스크롤이 바닥에 도달하면 추가 데이터 로드
            appendData();
        }
    };

    useEffect(() => {
        appendData();
    }, [])

    return (
        <Layout
            style={{
                minHeight: "100vh",
            }}
        >
            <UserNavbar selectedKey="6"></UserNavbar>
            <Layout>
                {/* <Header
                    style={{
                        padding: 0,
                        background: colorBgContainer,
                    }}
                /> */}
                <Content
                    style={{
                        margin: "0 16px",
                    }}
                >
                    <div
                        style={{
                            padding: 24,
                            minHeight: 360,
                            background: colorBgContainer,
                        }}
                    >
                        <h2>신고 목록</h2>
                        <List>
                            <VirtualList
                                id="my-virtual-list"
                                data={data}
                                height={600}
                                itemHeight={40}
                                itemKey="id"
                                onScroll={handleScroll}
                            >
                                {(item) => (
                                    <List.Item key={item.id}>
                                        <List.Item.Meta
                                            title={<Link to={`/res/content/${item.document.id}/${item.document.title}`}>{item.document.title}</Link>}
                                            description={item.user.nickname}
                                        />
                                        <div>
                                            <Button>문서 삭제</Button>
                                            <Button>반려</Button>
                                        </div>

                                    </List.Item>
                                )}
                            </VirtualList>
                        </List>
                    </div>
                </Content>
                <Footer
                    style={{
                        textAlign: "center",
                    }}
                >
                    Ant Design ©2023 Created by Ant UED
                </Footer>
            </Layout>
        </Layout>
    )
}

export default DocumentReport;