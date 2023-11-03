import { BrowserRouter, Routes, Route } from "react-router-dom";

import './App.css';

import MyHeader from './components/MyHeader';
import Nav from './components/Nav';
import UserList from './pages/UserList';
import UserEdit from './pages/UserEdit';
import GraphPage from './pages/GraphPage'
import AIModel from './pages/AIModel';
import Home from "./pages/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import PostEdit from "./pages/PostEdit";
import Post from "./pages/Post";
import User from "./pages/User";
import { AuthProvider } from "./components/AuthContext";
import FindAuth from "./pages/FindAuth";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <div className="App">
          <MyHeader />
          <Nav />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/userList" element={<UserList />} />
            <Route path="/userDetail/:id" element={<User />} />
            <Route path="/userEdit/:id" element={<UserEdit />} />
            <Route path="/findInfo" element={<FindAuth />} />
            <Route path="/GraphPage" element={<GraphPage />} />
            <Route path="/postDetail/:id" element={<Post />} />
            <Route path="/postEdit/:id" element={<PostEdit />} />
            <Route path="/aimodel" element={<AIModel />} />
          </Routes>
        </div>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
