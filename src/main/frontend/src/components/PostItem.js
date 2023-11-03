import React from "react";
import { useNavigate } from "react-router-dom";

export const PostItemStateContext = React.createContext();

const PostItem = ({ id, name, content, title, body }) => {
  const navigation = useNavigate();

  const goPostDetail = () => {
    navigation(`/postDetail/${id}`);
  }
  const goPostEdit = () => {
    navigation(`/postEdit/${id}`);
  }

  // title => name, body => content
  return (
    <PostItemStateContext.Provider value={{ id, name, content, title, body }}>
      <div>
        <div className="PostItem">
          <div className="PostItem-content">
            <div onClick={goPostDetail}><b>사용자: </b> {id}</div>
            <div onClick={goPostDetail}><b>title:</b> {title}</div>
            <div onClick={goPostDetail}><b>content:</b> {body}</div>
          </div>
          <button className="btn btn-outline-danger" onClick={goPostEdit}>
            수정하기
          </button>
        </div>
        <hr />
      </div>
    </PostItemStateContext.Provider>
  )
};

export default PostItem;