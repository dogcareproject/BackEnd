import React from "react";
import { Link } from "react-router-dom";

const MyHeader = () => {

  return (
    <div className="MyHeader">
      <h1>
        <img src="/img/11.png" style={{ width: "40px", height: "40px" }} />
        <Link className="navbarMain" to={'/'}>
          멍멍케어
        </Link>
      </h1>

    </div>
  );
};

export default MyHeader;
