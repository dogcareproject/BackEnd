import { saveAs } from 'file-saver';


const Home = () => {
  const downloadAppFile = () => {
    const appFilePath = process.env.PUBLIC_URL + '/app/dogcare.apk'; // 앱 파일 경로
    const appFileName = '멍멍케어.apk'; // 다운로드될 파일 이름

    // 파일을 다운로드합니다.
    saveAs(appFilePath, appFileName);
  };

  return <div className="HomeBgd">

    <img className="homeImg" src={process.env.PUBLIC_URL + "/img/ajd.png"} style={{ width: "200px", height: "300px" }} />
    <div className="Home">

      <h2>나의 반려견을 위한 <br />맞춤 케어 서비스</h2>
      <p>
        무심코 지나치게 되는 강아지 피부와 안구의 이상 징후,<br />
        사진을 찍어 AI로 집에서 진단을 받아보세요.
      </p>
      <p>멍멍케어와 함께 행복한 반려 생활을 즐겨보세요.</p>
      <button onClick={downloadAppFile} className='downbtn' style={{ fontSize: "20px" }}>멍멍케어 다운로드</button>
    </div>
    <img className="homeImg2" src={process.env.PUBLIC_URL + "/img/44.png"} style={{ width: "180px", height: "250px" }} />
  </div>
}

export default Home;