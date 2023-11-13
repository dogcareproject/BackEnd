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

    const categories = avgWeightData.map(entry => entry[0]);
    const maxWeights = avgWeightData.map(entry => entry[1]);
    const avgWeights = avgWeightData.map(entry => entry[2]);
    const minWeights = avgWeightData.map(entry => entry[3]);

    const colors = [
      Highcharts.getOptions().colors[0],
      Highcharts.getOptions().colors[1],
      Highcharts.getOptions().colors[2],
    ];

    Highcharts.chart('container', {
      chart: {
        type: 'column'
      },
      colors,
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
          color: colors[0],
        },
        {
          name: '평균 체중',
          data: avgWeights,
          color: colors[1],
        },
        {
          name: '최소 체중',
          data: minWeights,
          color: colors[2],
        },
      ],
    });
  }, [avgWeightData]);

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
