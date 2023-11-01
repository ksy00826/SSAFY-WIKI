import React, { useState } from "react";
import AWS from "aws-sdk";
import { getToken } from "utils/Authenticate";

const S3_BUCKET_NAME = "ssafywiki-s3"; // S3 버킷 이름
const S3_REGION = "ap-northeast-2"; // S3 버킷의 AWS 지역

AWS.config.update({
  accessKeyId: process.env.REACT_APP_AWS_ACCESS_KEY,
  secretAccessKey: process.env.REACT_APP_AWS_SECRET_KEY,
  region: S3_REGION,
});

const s3 = new AWS.S3();

function FileUpload() {
  const [selectedImage, setSelectedImage] = useState(null);
  const [imageUrl, setImageUrl] = useState("");

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

  const handleImageChange = (event) => {
    setSelectedImage(event.target.files[0]);
  };

  return (
    <div>
      <h1>React S3 Image Upload</h1>
      <input type="file" accept="image/*" onChange={handleImageChange} />
      <button onClick={handleImageUpload}>이미지 업로드</button>
      <div>{imageUrl}</div>
    </div>
  );
}

export default FileUpload;
