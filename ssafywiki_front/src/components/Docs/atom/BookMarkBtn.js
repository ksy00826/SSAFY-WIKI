import React from "react";
import { Tooltip, Button, message } from "antd";

import { StarOutlined, StarFilled } from "@ant-design/icons";
import { yellow } from "utils/ColorPicker";

import {
  countBookMark,
  checkBookMarked,
  writeBookMark,
  deleteBookMark,
} from "utils/BookMark";

const BookMark = ({ docsId }) => {
  const [bookmark, setBookmark] = React.useState(false);
  const [cntBookmark, setCntBookmark] = React.useState(0);
  const [messageApi, contextHolder] = message.useMessage();

  // 처음 랜더링시 내용 가져오기
  React.useEffect(() => {
    // bookmark 수 가져오기
    countBookMark(docsId)
      .then((response) => {
        setCntBookmark(response);
      })
      .catch((err) => {
        setCntBookmark(0);
      });
    // bookmark 등록여부 확인
    checkBookMarked(docsId)
      .then((response) => {
        setBookmark(response);
      })
      .catch((err) => console.log(err));
  });

  const handleAddStar = () => {
    writeBookMark(docsId)
      .then((response) => {
        setCntBookmark(response);
        setBookmark(true);
      })
      .catch((err) => {
        messageApi.open({
          type: "error",
          content: "로그인이 필요합니다.",
        });
      });
  };

  const handleRemoveStar = () => {
    deleteBookMark(docsId)
      .then((response) => {
        setCntBookmark(response);
        setBookmark(false);
      })
      .catch((err) => {
        messageApi.open({
          type: "error",
          content: "로그인이 필요합니다.",
        });
      });
  };

  return (
    <div>
      {contextHolder}
      {bookmark ? (
        <Tooltip placement="bottom" title="remove star">
          <Button onClick={handleRemoveStar}>
            <StarFilled twoToneColor={yellow} />
            {cntBookmark}
          </Button>
        </Tooltip>
      ) : (
        <Tooltip placement="bottom" title="add star">
          <Button type="default" onClick={handleAddStar}>
            <StarOutlined />
            {cntBookmark}
          </Button>
        </Tooltip>
      )}
    </div>
  );
};

export default BookMark;
