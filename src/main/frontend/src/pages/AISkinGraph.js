import axios from 'axios';
import React, { useEffect, useState } from 'react';

const AIEyesGraph = () => {
  const [predictedData, setPredictedData] = useState([]);

  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('/admin/skinLog', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          console.log(response.status);
          console.log(response.data);

          setPredictedData(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }, [token]);
  // ...

  return (
    <div className="container" style={{ position: "relative" }}>
      <div className="row py-5">
        <div className='AI-description'>
          <h2>AI 피부 진단 데이터</h2>
          <div className=''>
            멍멍케어에 쌓인 AI 피부 진단 로그를 확인할 수 있습니다.
          </div>
        </div>
        <div className="col-12">
          <table id="example" className="table table-hover responsive nowrap" style={{ width: "100%" }}>
            <thead>
              <tr style={{ borderBottom: "solid 1px black" }}>
                <th>날짜</th>
                <th>견종</th>
                <th>메시지</th>
              </tr>
            </thead>
            <tbody>
              {predictedData.slice().reverse().map((log, index) => { // predictedData 배열을 복사하고 뒤집기
                const dateTime = log.split(' ');
                const date = dateTime.slice(0, 2).join(' ');
                const message = dateTime.slice(2, 6).join(' ');
                const info = dateTime.slice(7, 9).join(' ');
                const breed = dateTime.slice(10).join(' ');

                return (
                  <tr key={index}>
                    <td>{date}</td>
                    <td>{breed}</td>
                    <td>{message} {info}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
};

export default AIEyesGraph;