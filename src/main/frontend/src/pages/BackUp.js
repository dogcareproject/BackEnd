import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const BackUp = () => {
  const navigation = useNavigate();
  const token = localStorage.getItem('token');
  const [logData, setLogData] = useState([]);

  useEffect(() => {
    axios.get('/admin/DBLog', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          // 날짜로 정렬
          const sortedLogData = response.data.sort((a, b) => {
            const dateA = new Date(a.split(' ')[0]);
            const dateB = new Date(b.split(' ')[0]);
            return dateA - dateB;
          });

          setLogData(sortedLogData.reverse());
          console.log(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }, [token]);

  const onBackUpHandler = () => {
    axios.post('/admin/BackUp', {}, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          console.log(response.status);
          console.log(logData);
        }
      })
      .catch(error => {
        console.error(error);
      });
    navigation('/backup', { replace: true });
  }

  return (
    <div className="BackUp">
      <div>
        <div className='BackUp-description'>
          <h2>데이터 백업</h2>
          <div className='descripition'>
            멍멍케어에 쌓인 데이터를 백업하여 저장할 수 있습니다.
          </div>
        </div>
        <button onClick={onBackUpHandler} className="backupBtn"><i class="bi bi-box-arrow-in-down"></i>&nbsp;&nbsp;데이터 백업</button>
        <div className="container" style={{ position: "relative" }}>
          <div className="row py-5">
            <div className="col-12">
              <table id="example" className="table table-hover responsive nowrap" style={{ width: "100%" }}>
                <thead>
                  <tr style={{ borderBottom: "solid 1px black" }}>
                    <th>날짜/시간</th>
                    <th>메시지</th>
                  </tr>
                </thead>
                <tbody>
                  {logData.map((log, index) => {
                    // 홀수 인덱스는 건너뛰기
                    if (index % 2 === 1) {
                      return null;
                    }

                    const nextLog = logData[index + 1];
                    if (!nextLog) {
                      return null;
                    }

                    const dateTime = log.split(' ');
                    const date = dateTime.slice(0, 2).join(' ');
                    const time = dateTime.slice(3, 5).join(' ');

                    const nextDateTime = nextLog.split(' ');
                    const nextInfo = nextDateTime.slice(2).join(' ');

                    return (
                      <tr key={index}>
                        <td>{date}</td>
                        <td>{time} {nextInfo}</td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default BackUp;
