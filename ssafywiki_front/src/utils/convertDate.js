export const convertDate = (isoString) => {
  if (!isoString) {
    throw new Error("Invalid date string provided");
  }

  const date = new Date(isoString);

  // 잘못된 날짜 값 처리
  if (isNaN(date.getTime())) {
    throw new Error("Invalid date string provided");
  }

  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");

  return `${year}-${month}-${day}`;
};

export const convertDateTime = (isoString) => {
  if (!isoString) {
    throw new Error("Invalid date string provided");
  }

  const date = new Date(isoString);

  // 잘못된 날짜 값 처리
  if (isNaN(date.getTime())) {
    throw new Error("Invalid date string provided");
  }

  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  const hour = date.getHours().toString().padStart(2, "0");
  const min = date.getMinutes().toString().padStart(2, "0");

  return `${year}-${month}-${day} ${hour}:${min}`;
};
