import React, { useState } from "react";
import { } from "@ant-design/icons";
import {
    Layout,
    theme,
    Row,
    Col,
    Image,
    Tooltip,
    Button,
    Modal,
} from "antd";
import { getMemberProfile } from "utils/UserApi";
import { AuditOutlined } from "@ant-design/icons";
import { useNavigate, useLocation } from "react-router-dom";
import { getSearchDoc } from "utils/DocsApi";

import OtherUserLawnGraph from "./OtherUserLawnGraph";

const { Content, Footer } = Layout;

const OtherUserPage = () => {
    const navigate = useNavigate();
    const [nickname, setNickname] = React.useState();
    const [userDocs, setUserDocs] = React.useState();
    const [info, setInfo] = React.useState();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const { state } = useLocation();
    const [userId, setUserId] = useState();

    const {
        token: { colorBgContainer },
    } = theme.useToken();

    React.useEffect(() => {
        getMemberProfile(state.userId).then((result) => {
            setInfo(result);
            setNickname(result.nickname);
            setUserDocs(result.name + " (" + result.number + ")");
            setUserId(result.id);
        });
    }, [state.userId]);

    React.useEffect(()=>{
        console.log(userId);
    },[userId])

    const handleUserDocs = () => {
        const keyword = userDocs;

        getSearchDoc(keyword).then((data) => {
            var output = data.data.hits.hits;
            var seq = 0;
            var newSearched = output.map(function (element) {
                seq = seq + 1;
                return {
                    label: element._source.docs_title,
                    value: element._source.docs_id,
                    isDeleted: element._source.docs_is_deleted,
                };
            });
            if (
                newSearched.length > 0 &&
                newSearched[0].label === keyword &&
                newSearched[0].isDeleted == false
            ) {
                navigate(
                    `/res/content/${newSearched[0].value}/${newSearched[0].label}`
                );
            } else {
                showModal();
            }
        });
    };
    const showModal = () => {
        setIsModalOpen(true);
    };
    return (
        <Layout
            style={{
                minHeight: "100vh",
            }}
        >
            <Layout style={{ paddingTop: 24 }}>
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
                        <Row justify="space-around" align="middle">
                            <Col span={1}>
                                <Image
                                    width={50}
                                    src="https://ssafywiki-s3.s3.ap-northeast-2.amazonaws.com/Mon%20Nov%2013%202023%2017%3A27%3A53%20GMT%2B0900%20%28%ED%95%9C%EA%B5%AD%20%ED%91%9C%EC%A4%80%EC%8B%9C%29eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBzc2FmeS5jb20iLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaWF0IjoxNjk5ODYzNTA2LCJleHAiOjE3MDExNTk1MDZ9.M88ZUo5_aAhY3_4i852w-0vWPsSFIzHEnvG_RDQaOtM.jpg"
                                />
                            </Col>
                            <Col span={3}>
                                <h1>{nickname}</h1>
                            </Col>
                            <Tooltip placement="top" title="내 문서">
                                <Button
                                    type="default"
                                    icon={<AuditOutlined />}
                                    onClick={handleUserDocs}
                                />
                            </Tooltip>
                            <Col span={17}></Col>
                        </Row>

                        {/* <Divider orientation="left" orientationMargin="0">
              <b>My Contribute</b>
            </Divider> */}
                        <Row justify="space-around" align="middle">
                            <Col span={1}></Col>
                        </Row>
                        {userId && <OtherUserLawnGraph userId={userId}></OtherUserLawnGraph>}
                    </div>
                </Content>
                <Footer
                    style={{
                        textAlign: "center",
                    }}
                ></Footer>
            </Layout>
            <Modal
                title="문서가 없습니다"
                open={isModalOpen}
            >
            </Modal>
        </Layout>
    );
};
export default OtherUserPage;
