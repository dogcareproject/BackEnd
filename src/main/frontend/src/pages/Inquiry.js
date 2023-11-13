import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const Inquiry = () => {

  const { id } = useParams();
  const navigation = useNavigate();

  const [date, setDate] = useState("");
  const [account, setAccount] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem('token');
  useEffect(() => {

    axios.get('/admin/getInquiries', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        const inquiryData = response.data.find(inquiry => parseInt(inquiry.inquiryId) === parseInt(id));
        if (inquiryData) {
          setAccount(inquiryData.member.account);
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

  const onInquiryDeleteHandler = () => {
    console.log(id);
    if (window.confirm('확인을 누르면 문의가 삭제됩니다.')) {
      axios.post('/admin/deleteInquiries', {
        inquiryId: id,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      })
        .then(response => {
          if (response.status === 200) {
            console.log(response.status);
            navigation('/inquiryList');
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }


  return <div className="Inquiry">
    {loading ? (
      <p>데이터를 불러오는 중...</p>
    ) : (<div>
      <div >
        <div>
          <h3>{title}</h3>
          <span>{account}</span>
          <span>{date}</span>
        </div>
        <hr />
        <div>
          <p>
            {content}
          </p>
        </div>
        <div>
          <hr />
          <button style={{ fontSize: "20px" }} onClick={onInquiryDeleteHandler}><i className="bi bi-trash"></i>&nbsp;&nbsp;문의 삭제</button>
          <button style={{ fontSize: "20px" }} onClick={() => navigation(`/inquiryAnswer/${id}`)}><i className="bi bi-envelope-fill"></i>&nbsp;&nbsp;답변</button>
        </div>
      </div>
    </div>
    )}
  </div >
}

export default Inquiry;