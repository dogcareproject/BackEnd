import React, { useState } from "react";
import PostItem from "./PostItem";

const sortOptionList = [
  { value: "latest", name: "최신 순" },
  { value: "oldest", name: "오래된 순" },
];

const ControlMenu = React.memo(({ value, onChange, optionList }) => {
  return (
    <select className="ControlMenu" value={value} onChange={(e) => onChange(e.target.value)}>
      {optionList.map((it, idx) => <option key={idx} value={it.value}>{it.name}</option>)}
    </select>
  );
});

const PostList = ({ postList }) => {
  const [sortType, setSortType] = useState("latest");

  const getProcessPostList = () => {
    const compare = (a, b) => {
      if (sortType === "latest") {
        // return parseInt(b.date) - parseInt(a.date);
        return parseInt(b.id) - parseInt(a.id);
      } else {
        // return parseInt(a.date) - parseInt(b.date);
        return parseInt(a.id) - parseInt(b.id);
      }
    }

    const copyList = JSON.parse(JSON.stringify(postList));

    const filteredAndSortedList = copyList.filter((item) => {
      return true;
    }).sort(compare);
    return filteredAndSortedList;
  }

  return (
    <div className="PostList">
      <div className="menu_wrapper">
        <ControlMenu
          value={sortType}
          onChange={setSortType}
          optionList={sortOptionList}
        />
        <div className="PostList-Card">
          {getProcessPostList().map((post, index) => (
            <div className="PostItem-Row" key={post.id}>
              <PostItem key={post.id} {...post} />
              {index % 3 === 2 && <div className="Clearfix" />} {/* Add clearfix div after every 3 items */}
            </div>
          ))}
        </div>
      </div>
    </div>
  )
};

PostList.defaultProps = {
  postList: []
};

export default PostList;