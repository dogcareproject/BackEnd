const HomeUserList = ({ id, account, name, email }) => {

  return (
    <table class="table">
      <tbody>
        <tr>
          {/* <th scope="row">1</th> */}
          <td>{account}</td>
          <td>{name}</td>
          <td>{email}</td>
        </tr>
      </tbody>
    </table>
  );
}

export default HomeUserList;
