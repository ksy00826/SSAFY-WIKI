import { useNavigate } from "react-router-dom";
import { Card } from "antd";
import {
  FileOutlined,
  SettingOutlined,
  EditOutlined,
  ArrowRightOutlined,
} from "@ant-design/icons";

const { Meta } = Card;

const DocsItem = ({ title, content, docsId }) => {
  const navigate = useNavigate();

  const handleDocument = (id, title) => {
    navigate(`/res/content/${id}/${title}`);
  };

  const handleEdit = (id, title) => {
    navigate(`/res/edit/${id}/${title}`);
  };

  const handleSetting = (id, title) => {
    navigate(`/res/auth/${id}/${title}`);
  };

  return (
    <Card
      // cover={<FileOutlined style={{ fontSize: 40 }} />}

      actions={[
        <SettingOutlined
          key="setting"
          onClick={() => {
            handleSetting(docsId, title);
          }}
        />,
        <EditOutlined
          key="edit"
          onClick={() => {
            handleEdit(docsId, title);
          }}
        />,
        <ArrowRightOutlined
          key="ellipsis"
          onClick={() => {
            handleDocument(docsId, title);
          }}
        />,
      ]}
      style={{ textAlign: "left" }}
    >
      <Meta
        avatar={<FileOutlined style={{ fontSize: 36 }} />}
        title={title}
        description={content}
      />
    </Card>
  );
};

export default DocsItem;
