import React from 'react';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function Pet() {
  const { id } = useParams();
  const token = localStorage.getItem('token');
  const navigation = useNavigate();
  // const [petData, setPetData] = useState(null);
  const [petName, setPetName] = useState("");
  const [petWeight, setPetWeight] = useState("");
  const [petGender, setPetGender] = useState("");
  const [petAge, setPetAge] = useState("");
  const [petBreed, setPetBreed] = useState("");

  useEffect(() => {
    axios.get(`/user/pet/pets`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        const petData = response.data.find(pet => parseInt(pet.id) === parseInt(id));
        setPetName(petData.name);
        setPetWeight(petData.weight);
        setPetGender(petData.gender);
        setPetAge(petData.petAge);
        setPetBreed(petData.breed);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [id]);


  const resetClass = (element, classname) => {
    element.classList.remove(classname);
  };

  const handleSignupClick = () => {
    let form = document.getElementsByClassName('form')[0];
    resetClass(form, 'signin');
    resetClass(form, 'reset');
    form.classList.add('signup');
    navigation(`/userDetail/${id}`);
  };

  const handleSigninClick = () => {
    let form = document.getElementsByClassName('form')[0];
    resetClass(form, 'signup');
    resetClass(form, 'reset');
    form.classList.add('signin');
    navigation(`/pet/${id}`);
  };

  return (
    <div>
      <div>
        <div onClick={handleSignupClick}>사용자 정보</div>
        <div onClick={handleSigninClick}>강아지 정보</div>
      </div>
      <div >
        <div>
          <input type="text" placeholder={petName} disabled />
        </div>
        <div>
          <input type="text" placeholder={petWeight} disabled />
        </div>
        <div>
          <input type="text" placeholder={petAge} disabled />
        </div>
      </div>
    </div>
  );
}

export default Pet;
