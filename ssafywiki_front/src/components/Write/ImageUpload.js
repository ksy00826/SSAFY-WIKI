import React, { useState } from "react";
import AWS from "aws-sdk";
import { getToken } from "utils/Authenticate";
import { LoadingOutlined, PlusOutlined } from "@ant-design/icons";
import { message, Upload, Card, Row, Col, Space, Button, Tooltip } from "antd";
import { openNotification } from "App";
import styles from "./ImageUpload.module.css";

const S3_BUCKET_NAME = "ssafywiki-s3"; // S3 버킷 이름
const S3_REGION = "ap-northeast-2"; // S3 버킷의 AWS 지역

AWS.config.update({
  accessKeyId: process.env.REACT_APP_AWS_ACCESS_KEY,
  secretAccessKey: process.env.REACT_APP_AWS_SECRET_KEY,
  region: S3_REGION,
});

const s3 = new AWS.S3();

// const getBase64 = (img, callback) => {
//   const reader = new FileReader();
//   reader.addEventListener("load", () => callback(reader.result));
//   reader.readAsDataURL(img);
// };

const beforeUpload = (file) => {
  const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
  if (!isJpgOrPng) {
    message.error("You can only upload JPG/PNG file!");
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error("Image must smaller than 2MB!");
  }
  return isJpgOrPng && isLt2M;
};

function FileUpload() {
  const [imageUrl, setImageUrl] = useState();
  const [loading, setLoading] = useState(false);

  const handleChange = (info) => {
    //console.log(info);
    setLoading(true);
    setImageUrl("");
    // S3 업로드
    const date = new Date();
    const file = info.file.originFileObj;
    const fileName = date.toString() + getToken() + ".jpg"; //나중에 토큰은 유저 아이디로 수정..

    const params = {
      Bucket: S3_BUCKET_NAME,
      Key: fileName,
      Body: file,
      ACL: "public-read", // 업로드된 파일을 공개로 설정
    };
    let uploadUrl = "";
    s3.upload(params, async (err, data) => {
      if (err) {
        console.error("S3 업로드 오류:", err);
      } else {
        uploadUrl = data.Location;
        //console.log("이미지 URL:", uploadUrl);
        info.file.status = "done";
      }
    });

    setTimeout(function () {
      //console.log("--", info.file.originFileObj);
      // 클라이언트에 보여주기
      setImageUrl(uploadUrl);
      setLoading(false);
    }, 300);
  };

  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div
        style={{
          marginTop: 8,
        }}
      >
        Upload
      </div>
    </div>
  );
  // 버튼에 적용할 스타일 객체
  const buttonStyle = {
    width: "850px", // 버튼의 너비를 100%로 설정하여 블록 레벨 버튼으로 만듭니다.
    height: "100%",
  };
  const copyToClipboard = () => {
    //console.log("click");
    const textArea = document.createElement("textarea");
    textArea.value = `<img src="${imageUrl}" style={{ maxWidth:"900px" }}/>`
    document.body.appendChild(textArea);
    textArea.select();
    document.execCommand("copy");
    document.body.removeChild(textArea);
    openNotification("success", "복사 완료", `이미지 URL이 복사되었습니다.`);
  };

  return (
    <div>
      <div className={styles.Box}>
        <div className={styles.Upload}>
          <Upload
            name="avatar"
            listType="picture-card"
            className="avatar-uploader"
            showUploadList={false}
            beforeUpload={beforeUpload}
            onChange={handleChange}
          >
            {imageUrl ? (
              <img
                src={imageUrl}
                alt="avatar"
                style={{
                  width: "100%",
                }}
              />
            ) : (
              uploadButton
            )}
          </Upload>
        </div>
        <div className={Button}>
          <Tooltip title="copy" style={{ width: "400px" }}>
            <Button ghost block style={buttonStyle} onClick={copyToClipboard}>
              <Card
                title="Image URL"
                style={{ height: "100px", margin: "0", padding: "0" }}
                bodyStyle={{ heigh: "50px", padding: "0" }}
              >
                {imageUrl ? (
                  <p className={styles.Url}>{imageUrl}</p>
                ) : (
                  <p>URL</p>
                )}
              </Card>
            </Button>
          </Tooltip>
        </div>
      </div>
    </div>
  );
}

export default FileUpload;
