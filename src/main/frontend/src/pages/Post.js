// import { useContext, useEffect, useState } from "react";
// import { useNavigate, useParams } from "react-router-dom";
// import { PostStateContext } from "./Community";
// import { PostItemStateContext } from "../components/PostItem";

// const Post = () => {
//   const { id } = useParams();
//   const navigation = useNavigate();
//   const PostItemValue = useContext(PostItemStateContext);
//   const postList = useContext(PostStateContext);
//   const [data, setData] = useState();



//   console.log(PostItemValue);

//   useEffect(() => {
//     if (PostItemValue && PostItemValue.length >= 1) {
//       const targetPost = PostItemValue.find((it) => parseInt(it.id) === parseInt(id));
//       if (targetPost) {
//         setData(targetPost);
//       } else {
//         alert('없는 게시글입니다.');
//         navigation('/', { replace: true });
//       }
//     }
//   }, [id, PostItemValue]);

//   if (!data) {
//     return <div className="PostPage">로딩중입니다...</div>
//   }

//   return <div className="PostPage">
//     <div className="Post">
//       <section>
//         <h2>제목: {data.title}</h2>
//         <h3>작성자: {data.name}</h3>
//         <div>
//           <p>{data.content}</p>
//         </div>
//         <button onClick={() => navigation(`/edit/${data.id}`)}>수정하기</button>
//       </section>
//     </div>
//   </div>
// };

// export default Post;