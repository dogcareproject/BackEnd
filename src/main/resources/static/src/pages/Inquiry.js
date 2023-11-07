import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const Inquiry = () => {
  const token = localStorage.getItem('token');

  const { id } = useParams();
  const navigation = useNavigate();

  const [date, setDate] = useState("");
  const [account, setAccount] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');

    axios.get('/admin/getInquiries', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        const inquiryData = response.data.find(inquiry => parseInt(inquiry.inquiryId) === parseInt(id));
        if (inquiryData) {
          setAccount(inquiryData.account);
          setDate(inquiryData.createTime);
          setTitle(inquiryData.title);
          setContent(inquiryData.content);
          setLoading(false);
        }
      })
      .catch(error => {
        console.error(error);
      })
  }, []);


  return <div className="Inquiry">
    {loading ? (
      <p>데이터를 불러오는 중...</p>
    ) : (<div>
      <div >
        <div>
          <h4>{title}</h4>
          <span>{account}</span>
        </div>
        <div>
          <textarea type="text" disabled>
            {content}
          </textarea>
        </div>
        <div>
          <button onClick={() => navigation(`/inquiryAnswer/${id}`)}>답변 달기</button>
        </div>
      </div>
    </div>
    )}
  </div>
}

export default Inquiry;