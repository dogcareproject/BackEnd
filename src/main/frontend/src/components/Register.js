import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import axios from "axios";

const Register = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [errorMessage, setErrorMessage] = useState("");


  const navigation = useNavigate();

  const onUserIdHandler = (e) => {
    setUserId(e.target.value);
  }
  const onPasswordHandler = (e) => {
    setPassword(e.target.value);
  }
  const onConfirmPasswordHandler = (e) => {
    setConfirmPassword(e.target.value);
  }
  const onNameHandler = (e) => {
    setName(e.target.value);
  }
  const onEmailHandler = (e) => {
    setEmail(e.target.value);
  }


  const onSubmitHandler = (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setErrorMessage("비밀번호가 일치하지 않습니다.");
      return;
    }

    axios.post('/adminRegister', {
      account: userId,
      password: password,
      name: name,
      email: email,
    })
      .then(response => {
        if (response.status === 200) {
          setData();
        }
        setLoading(false);
      })
      .catch(error => {
        console.error(error);
        setLoading(false);
      });
    navigation('/login');
  };


  return (
    <div className="Register">
      <form onSubmit={onSubmitHandler}>
        <label>ID</label>
        <input type='text' value={userId} onChange={onUserIdHandler} />
        <label>Password</label>
        <input type='password' value={password} onChange={onPasswordHandler} />
        <label>Confirm Password</label>
        <input type='password' value={confirmPassword} onChange={onConfirmPasswordHandler} />
        <label>Name</label>
        <input type='text' value={name} onChange={onNameHandler} />
        <label>Email</label>
        <input type='email' value={email} onChange={onEmailHandler} />
        <br />
        <button type="submit">회원가입</button>
      </form>
    </div>
  )
};

export default Register;