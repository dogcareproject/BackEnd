import axios from "axios"
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom"

const InquiryAnswer = () => {
  const navigation = useNavigate();
  const { id } = useParams();
  const token = localStorage.getItem('token');
  const [answer, setAnswer] = useState("");

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const [account, setAccount] = useState("");
  const [memberId, setMemberId] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  console.log(content);

  const answerhandler = (e) => {
    setAnswer(e.currentTarget.value);
  }

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('/admin/getInquiries', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        const inquiryData = response.data.find(inquiry => parseInt(inquiry.inquiryId) === parseInt(id));
        setAccount(inquiryData.member.account);
        setMemberId(inquiryData.member.id);
        setTitle(inquiryData.title);
        setContent(inquiryData.content);
        setData(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error(error);
        setLoading(false);
      });
  }, []);

  const inquiryAnswerHandelr = (e) => {
    e.preventDefault()
    axios.post('/admin/inquiryAnswer', {
      memberId: memberId,
      message: answer
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.data === 200) {
          console.log(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      })
    navigation('/inquiryList');
  };


  return <div>
    <form className="InquiryAnswer" onSubmit={inquiryAnswerHandelr}>
      <div>
        <div >
          <h5>{account} - 답변</h5>
          <textarea style={{ fontSize: "18px" }} onChange={answerhandler}></textarea>
        </div>
        <button style={{ fontSize: "20px" }} type="submit"><i class="bi bi-envelope-paper-fill"></i>&nbsp;&nbsp;답변 보내기</button>
      </div>
    </form>
  </div>
}

export default InquiryAnswer;