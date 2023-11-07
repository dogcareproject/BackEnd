import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import { useAuth } from "./AuthContext";

const Login = () => {
  const { login } = useAuth();

  const navigation = useNavigate();

  const [loading, setLoading] = useState(false);

  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [loginSuccess, setLoginSuccess] = useState(false);
  const [loginFailed, setLoginFailed] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");


  const onUserIdHandler = (e) => {
    setUserId(e.currentTarget.value);
  }
  const onPasswordHandler = (e) => {
    setPassword(e.currentTarget.value);
  }

  const onSubmitHandler = (e) => {
    e.preventDefault();
    login();

    if (userId === "" || password === "") {
      window.confirm("아이디 또는 비밀번호를 입력해주세요.")
      setLoginFailed(true);
      return;
    }
    axios.post('/login', {
      account: userId,
      password: password,
      headers: { 'X-Requested-With': 'XMLHttpRequest' }
    })
      .then(response => {
        if (response.status === 200) {
          const accessToken = response.data.token;
          localStorage.setItem('token', accessToken);
          console.log(response);
          setLoginSuccess(true);
          setLoginFailed(false);
          navigation('/');
        } else {
          setLoginFailed(true);
          window.confirm("로그인에 실패하였습니다. 아이디와 비밀번호를 확인하세요.");
          setLoading(false);
        }
      })
      .catch(error => {
        console.error(error);
        setLoginFailed(true);
        setErrorMessage("서버 오류가 발생했습니다. 나중에 다시 시도하세요.");
        setLoading(false);
        navigation('/login');
      });
  }

  return (
    <div className="Login">
      <div className="Login-container">
        <form onSubmit={onSubmitHandler}>
          <h2>로그인</h2>
          <div>
            <input
              type="text"
              name="logemail"
              placeholder="아이디"
              id="logemail"
              autoComplete="off"
              onChange={onUserIdHandler} />
            <i className="input-icon uil uil-at"></i>
          </div>
          <div>
            <input
              type="password"
              name="logpass"
              placeholder="비밀번호"
              id="logpass"
              autoComplete="off"
              onChange={onPasswordHandler} />
          </div>
          <button type="submit">로그인</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
