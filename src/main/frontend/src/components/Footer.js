import React from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const ImageSlider = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
  };

  const slideStyle = {
    margin: '10px', // 좌우 간격 조절
  };

  const imgStyle = {
    width: '150px', // 이미지 폭 100%
    height: '130px', // 이미지 높이 100%
    borderRadius: '8px',
    objectFit: 'cover', // 이미지가 잘리지 않고 비율 유지
  };

  return (
    <div className='Footer'>
      <Slider {...settings}>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/1.png"} alt="Image 1" style={imgStyle} />
        </div>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/2.png"} alt="Image 2" style={imgStyle} />
        </div>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/3.png"} alt="Image 3" style={imgStyle} />
        </div>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/4.png"} alt="Image 4" style={imgStyle} />
        </div>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/5.png"} alt="Image 5" style={imgStyle} />
        </div>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/6.png"} alt="Image 6" style={imgStyle} />
        </div>
        <div style={slideStyle}>
          <img src={process.env.PUBLIC_URL + "/img/7.png"} alt="Image 7" style={imgStyle} />
        </div>
      </Slider>
      <div className='Footer-description'>멍멍케어와 함께 행복한 반려 생활을 즐겨보세요.</div>
    </div>
  );
}

export default ImageSlider;
