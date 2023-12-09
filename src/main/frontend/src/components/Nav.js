import React, { useEffect, useRef, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from "./AuthContext";
import axios from 'axios';

const Nav = () => {
  const navigation = useNavigate();
  const { isLoggedIn, logout } = useAuth();


  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState(""); // 새로운 state 추가
  const [showPasswordChangeCard, setShowPasswordChangeCard] = useState(false);
  const [passwordMatchError, setPasswordMatchError] = useState(false); // 일치하지 않을 때의 에러 상태 추가

  const overlayRef = useRef(null);
  const cardRef = useRef(null);

  const handleOutsideClick = (e) => {
    if (
      overlayRef.current &&
      !overlayRef.current.contains(e.target) &&
      cardRef.current &&
      !cardRef.current.contains(e.target)
    ) {
      setShowPasswordChangeCard(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleOutsideClick);

    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, []);

  const handleLogout = () => {
    const confirmed = window.confirm("로그아웃하시겠습니까?");
    if (confirmed) {
      logout();
      localStorage.clear();
      navigation('/');
    }
  };

  const onPasswordHandler = (e) => {
    setPassword(e.target.value);
  }
  const onConfirmPasswordHandler = (e) => {
    setConfirmPassword(e.target.value);
  };

  const onChangePwdHandler = (e) => {
    e.preventDefault();

    // 비밀번호와 확인 비밀번호가 일치하는지 확인
    if (password !== confirmPassword) {
      setPasswordMatchError(true);
      return;
    }

    const token = localStorage.getItem('token');
    axios.put('/admin/pwdChange', {
      id: 3,
      password: password,
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          console.log("정보 수정");
          navigation('/');
        }
      })
      .catch(error => {
        console.error(error);
      });

    // Reset password and confirm password states
    setPassword("");
    setConfirmPassword("");
    setPasswordMatchError(false);

    // Hide the password change card
    setShowPasswordChangeCard(false);
  };


  const togglePasswordChangeCard = () => {
    setShowPasswordChangeCard(!showPasswordChangeCard);
  }

  return (
    <div>
      <nav>
        <ul class="menu">
          <li>
            <Link to={isLoggedIn ? '/userList' : '/login'}><i class="fa fa-user"></i>사용자 목록</Link>
          </li>
          <li>
            <Link to={!isLoggedIn && '/login'}><i class="fa fa-tags"></i>데이터 관리</Link>
            <ul class="sub-menu">
              <li>
                <Link to={isLoggedIn ? '/graph' : '/login'}>반려견 평균 데이터</Link>
              </li>
              <li>
                <Link to={isLoggedIn ? '/backup' : '/login'}>데이터 백업</Link>
              </li>
            </ul>
          </li>
          <li>
            <Link to={!isLoggedIn && '/login'}><i class="fa fa-tags"></i>AI Model</Link>
            <ul class="sub-menu">
              <li>
                <Link to={isLoggedIn ? '/aieyesgraph' : '/login'}>AI 안구 진단 데이터</Link>
              </li>
              <li>
                <Link to={isLoggedIn ? '/aiskingraph' : '/login'}>AI 피부 진단 데이터</Link>
              </li>
            </ul>
          </li>
          <li>
            <Link to={isLoggedIn ? '/inquiryList' : '/login'}><i class="fa fa-bullhorn"></i>문의 하기</Link>
          </li>
        </ul>
      </nav>
      <div className="logBtn">
        {isLoggedIn && (
          <>
            <button onClick={togglePasswordChangeCard}>비밀번호 변경 |</button>
            <button onClick={handleLogout}>로그아웃</button>
          </>
        )}
        {!isLoggedIn && (
          <Link to="/login">
            <button>로그인</button>
          </Link>
        )}
      </div>
      {showPasswordChangeCard && (
        <>
          <div className="overlay" ref={overlayRef}></div>
          <div className="user-card" ref={cardRef}>
            <h2>비밀번호 변경</h2>
            <form onSubmit={onChangePwdHandler}>
              <label htmlFor="password">새로운 비밀번호:</label>
              <input
                type="password"
                className='changepwd-input'
                id="password"
                value={password}
                onChange={onPasswordHandler}
                required
              />
              <br />
              <label htmlFor="confirmPassword">비밀번호 확인:</label>
              <input
                type="password"
                className='changepwd-input'
                id="confirmPassword"
                value={confirmPassword}
                onChange={onConfirmPasswordHandler}
                required
              />
              {passwordMatchError && (
                <p className="error-message">비밀번호가 일치하지 않습니다.</p>
              )}
              <br />
              <button type="submit"><i class="bi bi-person-badge-fill"></i>&nbsp;&nbsp;변경</button>
            </form>
            <button id='close-btn' className="close-btn" onClick={togglePasswordChangeCard}>
              <i className="bi bi-x-square"></i>&nbsp;&nbsp;닫기
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default Nav;
