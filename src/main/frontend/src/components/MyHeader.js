import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "./AuthContext";

const MyHeader = () => {
  const { isLoggedIn, logout } = useAuth();

  const handleLogout = () => {
    const confirmed = window.confirm("로그아웃하시겠습니까?");
    if (confirmed) {
      logout();
      window.location.reload();
      localStorage.clear();
    }
  };

  return (
    <div className="MyHeader">
      <h1>
        <Link className="navbarMain" to={'/'}>
          멍멍케어
        </Link>
      </h1>
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
  );
};

export default MyHeader;
