import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";


const UserItem = ({ id, account, name, email }) => {
  const navigation = useNavigate();

  const [dayCount, setDayCount] = useState("");

  const goUserDetail = (e) => {
    e.preventDefault();
    navigation(`/userDetail/${id}`);
  }

  const dayCountChange = (e) => {
    setDayCount(e.currentTarget.value);
  }

  const token = localStorage.getItem('token');

  const onUserBanHandler = (e) => {
    e.preventDefault();
    axios.post('/admin/banMember', {
      id: id,
      bantime: dayCount,
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          console.log(response.status);
        }
      })
      .catch(error => {
        console.error(error);
        console.log(id);
      })
  }

  const onUserDeleteHandler = (e) => {
    e.preventDefault();

    if (window.confirm('확인을 누르면 회원이 탈퇴됩니다.')) {
      axios.post('/admin/withdrawal', {
        id: id,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      })
        .then(response => {
          if (response.status === 200) {
            console.log(response.status);
            window.location.reload();
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }

  return <div className="UserItem">
    <div className="login-box">
      <h2>{account}</h2>
      <form>
        <div onClick={goUserDetail}>
          <div className="user-box">
            <b>아이디</b>
            <input type="text" value={account} required="" disabled />
          </div>
          <div className="user-box">
            <b>이름</b>
            <input type="text" value={name} required="" disabled />
          </div>
          <div className="user-box">
            <b>이메일</b>
            <input type="text" value={email} required="" disabled />
          </div>
        </div>
        <div>
          <a href={`/userEdit/${id}`}>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            수정하기
          </a>
          <a href="" onClick={onUserDeleteHandler}>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            회원 탈퇴
          </a>
        </div>
      </form>
      <form onSubmit={onUserBanHandler}>
        <div className="user-box">
          <input id="input1" type="text" placeholder="정지 일수" onChange={dayCountChange} />
        </div>
        <button id="btn1" type="submit">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          회원 정지
        </button>
      </form>
    </div>
  </div>
};

export default UserItem;