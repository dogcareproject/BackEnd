import React, { useEffect, useState } from 'react';
import { Avatar, Button, List, Skeleton, Pagination } from 'antd';
import axios from "axios";
import { useNavigate, useParams } from 'react-router-dom';

const count = 5; // 한 페이지에 표시할 항목 수

const App = () => {
  const navigation = useNavigate();

  const [initLoading, setInitLoading] = useState(true);
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [list, setList] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResult, setSearchResult] = useState([]);
  const [dayCount, setDayCount] = useState("");
  const [currentPage, setCurrentPage] = useState(1);

  const token = localStorage.getItem('token');

  const dayCountChange = (e) => {
    setDayCount(e.currentTarget.value);
  }

  const onUserBanHandler = (id) => {
    axios.post('/admin/banMember', {
      id: id,
      bantime: dayCount,
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          console.log(response.status);
          navigation('/');
        }
      })
      .catch(error => {
        console.error(error);
        console.log(id);
      })
  }

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
            navigation('/');
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }

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
      <div className='search-input'>
        <input
          type="text"
          placeholder="검색어를 입력하세요"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={handleSearch}><i class="bi bi-search"></i>&nbsp;&nbsp;검색</button>
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
                  <input id="input12" type="text" placeholder="정지 일수" onChange={dayCountChange} />
                  <span>&nbsp;&nbsp;</span>
                  <button onClick={() => onUserBanHandler(item.id)}><i class="bi bi-x-circle"></i>&nbsp;&nbsp;회원 정지</button>&nbsp; |
                  <span>&nbsp;&nbsp;</span>
                  <button onClick={() => onUserDeleteHandler(item.id)}><i class="bi bi-trash"></i>&nbsp;&nbsp;회원 강제 탈퇴</button>
                </div>
              </div>
            ]}
          >
            <Skeleton avatar title={false} loading={item.loading} active>
              <List.Item.Meta
                title={<a href="https://ant.design">{item.name?.last}</a>}
                description={`아이디: ${item.account}, 이메일: ${item.email}`}
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
    </div>
  );
};

export default App;
