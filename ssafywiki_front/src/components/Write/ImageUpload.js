import React, { useState } from "react";
import AWS from "aws-sdk";
import { getToken } from "utils/Authenticate";
import { LoadingOutlined, PlusOutlined } from "@ant-design/icons";
import { message, Upload, Card, Space } from "antd";

const S3_BUCKET_NAME = "ssafywiki-s3"; // S3 버킷 이름
const S3_REGION = "ap-northeast-2"; // S3 버킷의 AWS 지역

AWS.config.update({
  accessKeyId: process.env.REACT_APP_AWS_ACCESS_KEY,
  secretAccessKey: process.env.REACT_APP_AWS_SECRET_KEY,
  region: S3_REGION,
});

const s3 = new AWS.S3();

const getBase64 = (img, callback) => {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result));
  reader.readAsDataURL(img);
};

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
  const [selectedImage, setSelectedImage] = useState(null);
  // const [imageUrl, setImageUrl] = useState("");

  const handleImageUpload = () => {
    if (!selectedImage) {
      alert("이미지를 선택하세요.");
      return;
    }
    const date = new Date();
    const file = selectedImage;
    const fileName = date.toString() + getToken() + ".jpg"; //나중에 토큰은 유저 아이디로 수정..

    const params = {
      Bucket: S3_BUCKET_NAME,
      Key: fileName,
      Body: file,
      ACL: "public-read", // 업로드된 파일을 공개로 설정
    };

    s3.upload(params, (err, data) => {
      if (err) {
        console.error("S3 업로드 오류:", err);
      } else {
        const imageUrl = data.Location;
        setImageUrl(imageUrl);
        console.log("이미지 URL:", imageUrl);
      }
    });
  };

  const [imageUrl, setImageUrl] = useState();
  const [loading, setLoading] = useState(false);

  const handleChange = (info) => {
    console.log(info);
    if (info.file.status === "uploading") {
      setLoading(true);

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

      s3.upload(params, (err, data) => {
        if (err) {
          console.error("S3 업로드 오류:", err);
        } else {
          const imageUrl = data.Location;
          setImageUrl(imageUrl);
          console.log("이미지 URL:", imageUrl);
          info.file.status = "done";
        }
      });
      return;
    }
    if (info.file.status === "done") {
      console.log("--", info.file.originFileObj);
      // 클라이언트에 보여주기
      getBase64(info.file.originFileObj, (url) => {
        setLoading(false);
      });
    }
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

  return (
    <div>
      <Space direction="horizental" size={16}>
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
        <Card title="Image URL" style={{}}>
          <p>{imageUrl}</p>
        </Card>
      </Space>
    </div>
  );
}

export default FileUpload;
