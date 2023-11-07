const HomeInquiryList = ({ title, content }) => {



  return <div>
    <div className="home-inquiry-list">
      <table class="table">
        <tbody>
          <tr>
            {/* <th scope="row">1</th> */}
            <td className="titlePosition">{title}</td>
            <td className="contentPosition">{content}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
}

export default HomeInquiryList;