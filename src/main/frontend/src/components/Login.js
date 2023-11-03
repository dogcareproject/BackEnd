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

  // const [data, setData] = useState([]);
  // const [regiUserId, setRegiUserId] = useState("");
  // const [regiPassword, setRegiPassword] = useState("");
  // const [confirmPassword, setConfirmPassword] = useState("");
  // const [name, setName] = useState("");
  // const [email, setEmail] = useState("");

  // const [loginPage, setLoginPage] = useState(false);


  const onUserIdHandler = (e) => {
    setUserId(e.currentTarget.value);
  }
  const onPasswordHandler = (e) => {
    setPassword(e.currentTarget.value);
  }


  // const onRegiUserIdHandler = (e) => {
  //   setRegiUserId(e.target.value);
  // }
  // const onRegiPasswordHandler = (e) => {
  //   setRegiPassword(e.target.value);
  // }
  // const onConfirmPasswordHandler = (e) => {
  //   setConfirmPassword(e.target.value);
  // }
  // const onNameHandler = (e) => {
  //   setName(e.target.value);
  // }
  // const onEmailHandler = (e) => {
  //   setEmail(e.target.value);
  // }

  // const isDuplicateId = (memberList, newId) => {
  //   return memberList.some(member => member.account === newId);
  // }

  const onSubmitHandler = (e) => {
    e.preventDefault();
    login();

    if (userId === "" || password === "") {
      window.alert("아이디 또는 비밀번호를 입력해주세요.")
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
          window.alert("로그인에 실패하였습니다. 아이디와 비밀번호를 확인하세요.");
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

  // const onRegiSubmitHandler = (e) => {
  //   e.preventDefault();
  //   if (regiPassword !== confirmPassword) {
  //     setErrorMessage("비밀번호가 일치하지 않습니다.");
  //     return;
  //   }

  //   axios.post('/adminRegister', {
  //     account: regiUserId,
  //     password: regiPassword,
  //     name: name,
  //     email: email,
  //   })
  //     .then(response => {
  //       if (response.status === 200) {
  //         setData(response.data);
  //         setLoginPage(true);
  //         window.location.reload();
  //       }
  //       setLoading(false);
  //     })
  //     .catch(error => {
  //       console.error(error);
  //       setLoading(false);
  //     });
  // };

  // const onRegiSubmitHandler = (e) => {
  //   e.preventDefault();
  //   if (regiPassword !== confirmPassword) {
  //     setErrorMessage("비밀번호가 일치하지 않습니다.");
  //     return;
  //   }
  //   const token = localStorage.getItem('token');

  //   axios.get('/getMemberList', {
  //     headers: {
  //       Authorization: `Bearer ${token}`,
  //     }
  //   })
  //     .then(response => {
  //       const memberList = response.data;
  //       if (isDuplicateId(memberList, regiUserId)) {
  //         window.confirm("이미 사용 중인 아이디입니다.")
  //       } else {
  //         axios.post('/adminRegister', {
  //           account: regiUserId,
  //           password: regiPassword,
  //           name: name,
  //           email: email,
  //         })
  //           .then(response => {
  //             if (response.status === 200) {
  //               setData(response.data);
  //               setLoginPage(true);
  //               window.location.reload();
  //             }
  //             setLoading(false);
  //           })
  //           .catch(error => {
  //             console.error(error);
  //             setLoading(false);
  //           });
  //       }
  //     })
  //     .catch(error => {
  //       console.error(error);
  //       setLoading(false);
  //     });
  // };

  return (
    <div className="section">
      <div className="container">
        <div className="row full-height justify-content-center">
          <div className="col-12 text-center align-self-center py-5">
            <div className="section pb-5 pt-5 pt-sm-2 text-center">
              <label htmlFor="reg-log"></label>
              <div className="card-3d-wrap mx-auto">
                <div className="card-3d-wrapper">
                  <div className="card-front">
                    <div className="center-wrap">
                      <div className="section text-center">
                        <h4 className="mb-4 pb-3">Log In</h4>
                        <form onSubmit={onSubmitHandler}>
                          <div className="form-group">
                            <input
                              type="text"
                              name="logemail"
                              className="form-style"
                              placeholder="Your ID"
                              id="logemail"
                              autoComplete="off"
                              onChange={onUserIdHandler} />
                            <i className="input-icon uil uil-at"></i>
                          </div>
                          <div className="form-group mt-2">
                            <input
                              type="password"
                              name="logpass"
                              className="form-style"
                              placeholder="Your Password"
                              id="logpass"
                              autoComplete="off"
                              onChange={onPasswordHandler} />
                            <i className="input-icon uil uil-lock-alt"></i>
                          </div>
                          <button type="submit" className="btn mt-4">로그인</button>
                        </form>
                        <p className="mb-0 mt-4 text-center">
                          <a href="/findInfo" className="link">Forgot your info?</a>
                        </p>
                      </div>
                    </div>
                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
