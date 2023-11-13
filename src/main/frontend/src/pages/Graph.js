import { Link } from "react-router-dom";

const Graph = () => {
  return <div>
    <div className='Graph'>
      <h2>반려견의 체중 및 산책 관련 데이터</h2>
      <div className='descripition'>
        반려견의 체중 및 산책 관련 데이터를 확인할 수 있습니다.
      </div>
    </div>
    <div class="dropdown">
      <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        데이터 그래프
      </button>
      <ul class="dropdown-menu">
        <li><Link to={'/AvgWeight'}>체중 데이터 관리</Link></li>
        <li><Link to={'/walkData'}>산책 거리 데이터 관리</Link></li>
      </ul>
    </div>
    <img src="/img/linechart.gif"
      style={{
        width: "1200px",
        height: "500px",
        marginTop: "2%",
        marginLeft: "17%",
        borderRadius: "10px"
      }} />
  </div>
}

export default Graph;