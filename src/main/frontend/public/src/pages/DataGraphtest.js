import React, { useEffect } from 'react';
import Highcharts from 'highcharts';
import axios from 'axios';

const DataGraph = () => {
  useEffect(() => {
    Highcharts.setOptions({
      colors: ['rgba(5,141,199,0.5)', 'rgba(80,180,50,0.5)', 'rgba(237,86,27,0.5)'],
    });

    const series = [
      {
        name: '체중',
        id: 'weight',
        marker: {
          symbol: 'circle',
        },
      },
      {
        name: '산책',
        id: 'walk',
        marker: {
          symbol: 'triangle',
        },
      },

    ];

    const getData = (data, sportName) => {
      const temp = [];
      data.forEach((elm) => {
        if (elm.sport === sportName && elm.weight > 0 && elm.height > 0) {
          temp.push([elm.height, elm.weight]);
        }
      });
      return temp;
    };

    const token = localStorage.getItem('token');
    axios.post('/', {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {

        if (response.ok) {
          const data = response.json();

          series.forEach((s) => {
            s.data = getData(data, s.id);
          });

          Highcharts.chart('container', {
            chart: {
              type: 'scatter',
              zoomType: 'xy',
            },
            title: {
              text: '사용자 반려견 데이터 관리',
              align: 'left',
            },
            subtitle: {
              text: "",
              align: 'left',
            },
            xAxis: {
              title: {
                text: 'Height',
              },
              labels: {
                format: '{value} m',
              },
              startOnTick: true,
              endOnTick: true,
              showLastLabel: true,
            },
            yAxis: {
              title: {
                text: 'Weight',
              },
              labels: {
                format: '{value} kg',
              },
            },
            legend: {
              enabled: true,
            },
            plotOptions: {
              scatter: {
                marker: {
                  radius: 2.5,
                  symbol: 'circle',
                  states: {
                    hover: {
                      enabled: true,
                      lineColor: 'rgb(100,100,100)',
                    },
                  },
                },
                states: {
                  hover: {
                    marker: {
                      enabled: false,
                    },
                  },
                },
                jitter: {
                  x: 0.005,
                },
              },
            },
            tooltip: {
              pointFormat: 'Height: {point.x} m <br/> Weight: {point.y} kg',
            },
            series,
          });
        } else {
          console.error('Failed to fetch data.');
        }
      })
      .catch(error => {
        console.error(error);
      })
  }, []);

  return <div className="DataGraph" id="container" />;
};

export default DataGraph;