import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const FindAuth = () => {
  const navigation = useNavigate();

  const [email, setEmail] = useState("");
  const [userId, setUserId] = useState("");
  const [data, setData] = useState("");

  const onEmailHandler = (e) => {
    setEmail(e.currentTarget.value);
  }

  const onUserIdHandler = (e) => {
    setUserId(e.currentTarget.value);
  }

  const resetClass = (element, classname) => {
    element.classList.remove(classname);
  };

  const handleFindPwdClick = () => {
    let form = document.getElementsByClassName('form')[0];
    resetClass(form, 'signup');
    resetClass(form, 'reset');
    form.classList.add('signin');
  };

  const handleFindIDClick = () => {
    let form = document.getElementsByClassName('form')[0];
    resetClass(form, 'signup');
    resetClass(form, 'signin');
    form.classList.add('reset');
    document.getElementById("submit-btn").innerText = "Find";
  };

  const onSubmitHandler = (e) => {
    e.preventDefault();
    axios.post('/Find/Account', {
      email: email,
    })
      .then(response => {
        if (response.status === 200) {
          console.log(response.status);
          console.log(response.data);
          window.confirm(`아이디는 ${response.data}입니다.`);
          setData(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      })

    axios.post('/Find/Pwd', {
      email: email,
      account: userId,
    })
      .then(response => {
        if (response.status === 200) {
          console.log(response.status);
          console.log(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      })
  }

  return <div>
    <div className="form signup">
      <div className="form-header">
        <div className="show-signin" onClick={handleFindPwdClick}>비밀번호 찾기</div>
        <div className="show-reset" onClick={handleFindIDClick}>아이디 찾기</div>
      </div>
      <form onSubmit={onSubmitHandler}>
        <div className="arrow"></div>
        <div className="form-elements">
          <div className="form-element">
            <input
              type="email"
              placeholder="이메일"
              onChange={onEmailHandler}
            />
          </div>
          <div className="form-element">
            <input
              type="text"
              placeholder="아이디"
              onChange={onUserIdHandler}
            />
          </div>
        </div>
        <div className="">
          <button id="submit-btn">Find</button>
        </div>
      </form>
    </div>
  </div>
}

export default FindAuth;