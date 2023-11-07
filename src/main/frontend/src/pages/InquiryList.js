import React, { useEffect, useState } from "react";
import axios from "axios";
import { List } from "antd";
import { useNavigate } from "react-router-dom";

const InquiryList = () => {
  const navigation = useNavigate();

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const [searchTerm, setSearchTerm] = useState("");
  const [searchResult, setSearchResult] = useState([]);

  const handleSearch = () => {
    const filteredData = data.filter((item) => {
      return item.title.includes(searchTerm);
    });
    setSearchResult(filteredData);
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    axios
      .get("/admin/getInquiries", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error(error);
        setLoading(false);
      });
  }, []);

  const displayData = searchResult.length > 0 ? searchResult : data;

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
        className="InquiryList"
        itemLayout="vertical"
        size="large"
        pagination={{
          onChange: (page) => {
            console.log(page);
          },
          pageSize: 4,
        }}
        dataSource={displayData}
        renderItem={(item) => (
          <List.Item
            key={item.inquiryId}
            actions={[
            ]}
          >
            <List.Item.Meta
              title={<a onClick={() => navigation(`/inquiryDetail/${item.inquiryId}`)}>{item.title}</a>}
              description={item.content}
            />
          </List.Item>
        )}
      />
    </div>
  );
};

export default InquiryList;
