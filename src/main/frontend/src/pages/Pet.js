import { useNavigate } from "react-router-dom";

const Pet = ({ pet_id, name, weight, gender, age, breed }) => {
  const navigation = useNavigate();

  return <div>
    <>
      <div className="form-element">
        <input type="text" placeholder={name} disabled />
      </div>
      <div className="form-element">
        <input type="text" placeholder={weight} disabled />
      </div>
      <div className="form-element">
        <input type="text" placeholder={gender} disabled />
      </div>
      <div className="form-element">
        <input type="text" placeholder={age} disabled />
      </div>
      <div className="form-element">
        <input type="text" placeholder={breed} disabled />
      </div>
      <div className="form-element">
        <button id="submit-btn" onClick={() => navigation(`/userEdit/${pet_id}`)}>수정하기</button>
      </div>
    </>
  </div>
};

export default Pet;