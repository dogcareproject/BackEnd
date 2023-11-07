import { Link, useNavigate } from 'react-router-dom';
import React from 'react';
import { useAuth } from "./AuthContext";


import '../App.css';

const Nav = () => {
  const navigation = useNavigate();
  const { isLoggedIn, logout } = useAuth();

  const handleLogout = () => {
    const confirmed = window.confirm("로그아웃하시겠습니까?");
    if (confirmed) {
      logout();
      localStorage.clear();
      navigation('/');
    }
  };

  return <div className="navbar">
    <div>
      <Link className="navbarMenu" to={'/userList'}><b>사용자 목록</b></Link>
      <Link className="navbarMenu" to={'/GraphPage'}><b>데이터 관리</b></Link>
      <Link className="navbarMenu" to={'/aiModel'}><b>AI Model</b></Link>
      <Link className='navbarMenu' to={'/inquiryList'}><b>문의 관리</b></Link>
      <div className="logBtn">
        {isLoggedIn ? (
          <button onClick={handleLogout}>로그아웃</button>
        ) : (
          <Link to={'/login'}>
            <button>로그인</button>
          </Link>
        )}
      </div>
    </div>
  </div>
};


export default Nav;