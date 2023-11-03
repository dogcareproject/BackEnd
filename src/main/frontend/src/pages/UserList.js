import React, { useEffect, useState } from "react";
import axios from "axios";
import UserItem from "../components/UserItem";

const UserList = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('/getMemberList', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        setData(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error(error);
        setLoading(false);
      });
  }, []);

  return (
    <div className="UserList">
      {loading ? (
        <p>데이터를 불러오는 중...</p>
      ) : (
        <div>
          {data.map((user, index) => (
            <div key={user.id} className="UerList-Card">
              <UserItem {...user} />
              {index % 3 === 2 && <div className="Clearfix" />}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default UserList;
