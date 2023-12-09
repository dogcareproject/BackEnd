import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";


const User = () => {
  const token = localStorage.getItem('token');

  const { id } = useParams();
  const navigation = useNavigate();

  const [originalUserId, setOriginalUserId] = useState("");
  const [originalPassword, setOriginalPassword] = useState("");
  const [originalName, setOriginalName] = useState("");
  const [originalEmail, setOriginalEmail] = useState("");

  const [pets, setPets] = useState([]);

  const [petName, setPetName] = useState("");
  // const [petWeight, setPetWeight] = useState("");
  // const [petGender, setPetGender] = useState("");
  // const [petAge, setPetAge] = useState("");
  // const [petBreed, setPetBreed] = useState("");

  const [isUser, setIsUser] = useState(true);

  useEffect(() => {
    axios.get('/getMemberList', {
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

    axios.get('/user/pet/pets', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        const petData = response.data.find(pet => parseInt(pet.id) === parseInt(id));
        if (petData) {
          setPetName(petData.name);
          // setPets(petData);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }, [id]);


  const resetClass = (element, classname) => {
    element.classList.remove(classname);
  };

  const handleSignupClick = () => {
    let form = document.getElementsByClassName('form')[0];
    // resetClass(form, 'signin');
    // resetClass(form, 'reset');
    form.classList.add('signup');
    setIsUser(true);
  };

  const handleSigninClick = () => {
    let form = document.getElementsByClassName('form')[0];
    // resetClass(form, 'signup');
    // resetClass(form, 'reset');
    form.classList.add('signin');
    setIsUser(false);
    navigation(`/pet/${id}`);
  };

  return <div>
    <div>
      <div>
        <div onClick={handleSignupClick}>사용자 정보</div>
        <div onClick={handleSigninClick}>강아지 정보</div>
      </div>
      <div></div>
      <div>
        <>
          <div>
            <input type="text" placeholder={originalUserId} disabled />
          </div>
          <div>
            <input type="text" placeholder={originalName} disabled />
          </div>
          <div>
            <input type="text" placeholder={originalEmail} disabled />
          </div>
        </>
      </div>
    </div>
  </div>
};

export default User;