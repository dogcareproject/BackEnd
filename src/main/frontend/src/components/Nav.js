import { Link } from 'react-router-dom';
import React from 'react';

import '../App.css';

const Nav = () => {
  return <div>
    <div className="navbar">
      <Link className="navbarMenu" to={'/userList'}><b>사용자 목록</b></Link>
      <Link className="navbarMenu" to={'/GraphPage'}><b>데이터 관리</b></Link>
      <Link className="navbarMenu" to={'/aiModel'}><b>AI Model</b></Link>
      <Link className='navbarMenu' to={'/'}><b>문의 관리</b></Link>
    </div>
  </div>
};


export default Nav;