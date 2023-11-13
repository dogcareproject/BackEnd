import React, { useEffect, useState } from 'react';
import Highcharts from 'highcharts';
import axios from 'axios';
import { Link } from 'react-router-dom';

const AvgWeight = () => {
  const [avgWalkData, setAvgWalkData] = useState([]);
  // const [memberWalkData, setMemberWalkData] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('token');

    axios.get('/user/pet/walkAVGByBreed', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        if (response.status === 200) {
          setAvgWalkData(response.data);
          console.log(response.data);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  useEffect(() => {
    if (!avgWalkData.length) {
      return;
    }

    const colors = [
      Highcharts.getOptions().colors[0],
      Highcharts.getOptions().colors[1],
    ];

    Highcharts.chart('container', {
      chart: {
        type: 'line', // 선 그래프
      },

      colors,

      title: {
        text: '평균 산책 시간 및 거리 차트',
      },

      xAxis: {
        categories: avgWalkData.map(entry => entry.breed),
        title: {
          text: '견종',
        },
      },

      yAxis: [
        {
          title: {
            text: '평균 산책 시간',
          },
          labels: {
            format: '{value} 분',
          },
        },
        {
          title: {
            text: '평균 산책 거리',
          },
          labels: {
            format: '{value} km',
          },
          opposite: true,
        },
      ],

      plotOptions: {
        line: {
          marker: {
            enabled: true,
          },
        },
        column: {
          pointPadding: 0.1,
          borderWidth: 0,
        },
      },

      series: [
        {
          name: '평균 산책 시간',
          data: avgWalkData.map(entry => ({
            name: entry.breed,
            y: entry.averageWalkTime,
            color: colors[0],
          })),
        },
        {
          name: '평균 산책 거리',
          data: avgWalkData.map(entry => ({
            name: entry.breed,
            y: entry.averageWalkDistance,
            color: colors[1],
          })),
          yAxis: 1,
        },
      ],
    });
  }, [avgWalkData]);

  return (
    <div>
      <div className="dropdown">
        <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
          드롭다운 버튼
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
