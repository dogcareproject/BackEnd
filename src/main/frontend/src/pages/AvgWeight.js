import React, { useEffect, useState } from 'react';
import Highcharts from 'highcharts';
import axios from 'axios';
import { Link } from 'react-router-dom';

const AvgWeight = () => {
  const [avgWeightData, setAvgWeightData] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('token');

    axios.get('/user/pet/weightByBreed', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          setAvgWeightData(response.data);
          console.log(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  useEffect(() => {
    if (!avgWeightData.length) {
      return;
    }

    // 각 견종에 대한 최대 체중, 최소 체중, 평균 체중 계산
    const categories = avgWeightData.map(entry => entry[0]);
    const maxWeights = avgWeightData.map(entry => entry[1]);
    const minWeights = avgWeightData.map(entry => entry[3]);
    const avgWeights = avgWeightData.map(entry => entry[2]);

    // 차트 생성
    Highcharts.chart('container', {
      chart: {
        type: 'column'
      },
      title: {
        text: '체중 통계 차트'
      },
      xAxis: {
        categories,
        title: {
          text: '견종'
        }
      },
      yAxis: {
        title: {
          text: '체중'
        }
      },
      plotOptions: {
        column: {
          pointPadding: 0.1,
          borderWidth: 0,
          dataLabels: {
            enabled: true,
            format: '{point.y:.2f} kg'
          }
        }
      },
      series: [
        {
          name: '최대 체중',
          data: maxWeights,
        },
        {
          name: '평균 체중',
          data: avgWeights.map(weight => parseFloat(weight.toFixed(2))),
        },
        {
          name: '최소 체중',
          data: minWeights,
        },
      ],
    });
  }, [avgWeightData]);

  return (
    <div>
      <div className='Graph'>
        <h2>반려견의 체중 및 산책 관련 데이터</h2>
        <div className='description' style={{ position: "relative", left: "10%" }}>
          반려견의 체중 및 산책 관련 데이터를 확인할 수 있습니다.
        </div>
      </div>
      <div className="dropdown">
        <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
          데이터 그래프
        </button>
        <ul className="dropdown-menu">
          <li><Link to={'/AvgWeight'}>체중 데이터 관리</Link></li>
          <li><Link to={'/walkData'}>산책 거리 데이터 관리</Link></li>
        </ul>
      </div>
      <div className="DataGraph" id="container" />
    </div>
  );
};

export default AvgWeight;
