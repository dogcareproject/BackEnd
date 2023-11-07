import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const UserEdit = () => {
  const navigation = useNavigate();

  const { id } = useParams();
  const [originalUserId, setOriginalUserId] = useState("");
  const [originalPassword, setOriginalPassword] = useState("");
  const [originalName, setOriginalName] = useState("");
  const [originalEmail, setOriginalEmail] = useState("");

  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('/getMemberList', {
      // headers: { 'X-Requested-With': 'XMLHttpRequest' }
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        const userData = response.data.find(user => parseInt(user.id) === parseInt(id));
        if (userData) {
          setOriginalUserId(userData.account);
          setOriginalPassword(userData.password);
          setOriginalName(userData.name);
          setOriginalEmail(userData.email);
        }
      })
      .catch(error => {
        console.error(error);
      })
  }, [id])

  // const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  // const onUserIdHandler = (e) => {
  //   setUserId(e.target.value);
  // }
  const onPasswordHandler = (e) => {
    setPassword(e.target.value);
  }
  const onNameHandler = (e) => {
    setName(e.target.value);
  }
  const onEmailHandler = (e) => {
    setEmail(e.target.value);
  }

  const onUserEditHandler = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    axios.put('/user/infoChange', {
      memberId: id,
      password: password,
      name: name,
      email: email,
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        // const userData = response.data.find(user => parseInt(user.id) === parseInt(id));
        // if (userData) {
        if (response.status === 200) {
          console.log("정보 수정");
          navigation('/userList');
          // }
        }
      })
      .catch(error => {
        console.error(error);
      });
  }

  return <div>
    <div>
      <div>
        <div></div>
        <div>
          <div>
            <div>
              <form onSubmit={onUserEditHandler}>
                <div>
                  <label>아이디</label>
                  <input type="text" placeholder={originalUserId} disabled />
                </div>
                <div>
                  <label>비밀번호</label>
                  <input type="password" onChange={onPasswordHandler} />
                </div>
                <div>
                  <label>이름</label>
                  <input type="text" placeholder={originalName} onChange={onNameHandler} />
                </div>
                <div>
                  <label>이메일</label>
                  <input type="email" placeholder={originalEmail} onChange={onEmailHandler} />
                </div>
                <div>
                  <div>
                  </div>
                  <div>
                    <button type="submit">수정 완료</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div className="col-lg-3 col-md-2"></div>
        </div>
      </div>
    </div>
  </div>
};

export default UserEdit;