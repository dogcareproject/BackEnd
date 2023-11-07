import React from "react";
import { useNavigate } from "react-router-dom";


const InquiryItem = ({ inquiryId, account, title, content, email }) => {
  const navigation = useNavigate();

  const goDetail = () => {
    navigation(`/inquiryDetail/${inquiryId}`);
  }

  return (
    <div>
      <div>
        <blockquote onClick={goDetail}>
          <h3><span>{title}</span> - {email}</h3>
          <p>{content}</p>
        </blockquote>
        <div>
          <button onClick={() => navigation(`/inquiryAnswer/${inquiryId}`)}>답변 달기</button>
        </div>
      </div>
    </div>
  )
};

export default InquiryItem;