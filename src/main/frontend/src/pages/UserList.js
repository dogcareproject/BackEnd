import React, { useEffect, useRef, useState } from 'react';
import { Avatar, Button, List, Skeleton, Pagination } from 'antd';
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const count = 7; // 한 페이지에 표시할 항목 수

const UserList = () => {
  const navigation = useNavigate();
  const [initLoading, setInitLoading] = useState(true);
  const [data, setData] = useState([]);
  const [list, setList] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResult, setSearchResult] = useState([]);
  const [dayCount, setDayCount] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedUser, setSelectedUser] = useState(null);
  const [selectedUserPets, setSelectedUserPets] = useState("");

  const token = localStorage.getItem('token');

  const cardRef = useRef(null);
  const overlayRef = useRef(null);

  const handleOutsideClick = (e) => {
    if (overlayRef.current && !overlayRef.current.contains(e.target) && !cardRef.current.contains(e.target)) {
      setSelectedUser(null);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleOutsideClick);
    return () => {
      document.removeEventListener("mousedown", handleOutsideClick);
    };
  }, []);

  const handleCardClose = () => {
    setSelectedUser(null);
  };


  const onUserDeleteHandler = (id) => {
    if (window.confirm('확인을 누르면 회원이 탈퇴됩니다.')) {
      axios.post('/admin/withdrawal', {
        id: id,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        }
      })
        .then(response => {
          if (response.status === 200) {
            console.log(response.status);
            navigation('/userList');
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }

  const onUserClick = (user) => {
    setSelectedUser(user);
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('/getMemberList', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        setInitLoading(false);
        setData(response.data);
        setList(response.data);
      })
      .catch(error => {
        console.error(error);
        setInitLoading(false);
      });
  }, []);

  const memberId = selectedUser?.id

  useEffect(() => {
    const token = localStorage.getItem('token');
    // memberId를 params로 전달
    axios.get('/user/pet/pets', {
      params: {
        memberId: memberId,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        setInitLoading(false);
        setSelectedUserPets(response.data);
      })
      .catch(error => {
        console.error(error);
        setInitLoading(false);
      });
  }, [selectedUser?.id, memberId]);

  const renderPetInfo = () => {
    if (!selectedUserPets) {
      return <p>로딩 중...</p>;
    }

    if (selectedUserPets.length === 0) {
      return <p>해당 사용자의 펫 정보가 없습니다.</p>;
    }

    return (
      <div>
        <ol class="large-numbers">
          {selectedUserPets.map((pet) => (
            <li key={pet.id}>
              <p>이름: {pet.name}</p>
              <p>나이: {pet.age}</p>
              <p>몸무게: {pet.weight}</p>
              <p>견종: {pet.breed}</p>
            </li>
          ))}
        </ol>
      </div>
    );
  };

  const handleSearch = () => {
    const filteredData = data.filter((item) => {
      return item.account.includes(searchTerm);
    });
    setSearchResult(filteredData);
  };

  const onPageChange = (page) => {
    setCurrentPage(page);
  };

  const startIndex = (currentPage - 1) * count;
  const endIndex = startIndex + count;

  const displayList = searchResult.length > 0 ? searchResult.slice(startIndex, endIndex) : list.slice(startIndex, endIndex);

  return (
    <div>
      <div className='Graph'>
        <h2>사용자 목록</h2>
        <div className='descripition'>
          멍멍케어 사용자들의 정보를 확인할 수 있습니다.
        </div>
      </div>
      <div className='search-input'>
        <input
          type="text"
          placeholder="검색어를 입력하세요"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={handleSearch}><i className="bi bi-search"></i>&nbsp;&nbsp;검색</button>
      </div>
      <List
        className="demo-loadmore-list"
        loading={initLoading}
        itemLayout="horizontal"
        dataSource={displayList}
        renderItem={(item) => (
          <List.Item
            actions={[
              <div>
                <div>
                  <button onClick={() => onUserDeleteHandler(item.id)} style={{ fontSize: "20px" }}><i className="bi bi-trash"></i>&nbsp;&nbsp;회원 강제 탈퇴</button>
                </div>
              </div>
            ]}
          >
            <Skeleton avatar title={false} loading={item.loading} active>
              <List.Item.Meta
                onClick={() => onUserClick(item)} // 추가된 부분
                title={<a>{item.name?.last}</a>}
                description={
                  <a className="user-info">
                    <div>{item.account}</div>
                    <div>{item.email}</div>
                  </a>
                }
              />
            </Skeleton>
          </List.Item>
        )}
      />
      <Pagination
        className='pagination'
        current={currentPage}
        total={searchResult.length > 0 ? searchResult.length : list.length}
        pageSize={count}
        onChange={onPageChange}
      />

      {selectedUser && (
        <div className="user-card" ref={cardRef} style={{ cursor: "pointer" }}>
          <h2>"{selectedUser.account}"님의 반려견 정보</h2>
          <>
            <div className="overlay" ref={overlayRef}></div>
            {renderPetInfo()}
            <button className="close-btn" onClick={handleCardClose}><i class="bi bi-x-square"></i>&nbsp;&nbsp;닫기</button>
          </>
        </div>
      )}
    </div>
  );
};

export default UserList;
