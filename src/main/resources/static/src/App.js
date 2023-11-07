import { BrowserRouter, Routes, Route } from "react-router-dom";

import './App.css';

import MyHeader from './components/MyHeader';
import Nav from './components/Nav';
import UserList from './pages/UserList';
import UserEdit from './pages/UserEdit';
import GraphPage from './pages/GraphPage'
import AIModel from './pages/AIModel';
import Login from "./components/Login";
import Inquiry from "./pages/Inquiry";
import InquiryList from "./pages/InquiryList";
import User from "./pages/User";
import { AuthProvider } from "./components/AuthContext";
import Pet from "./pages/Pet";
import InquiryAnswer from "./pages/InquiryAnswer";
import Home from "./pages/Home";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <div className="App">
          <MyHeader />
          <Nav />
          {/* <Home /> */}
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/userList" element={<UserList />} />
            <Route path="/userDetail/:id" element={<User />} />
            <Route path="/userEdit/:id" element={<UserEdit />} />
            <Route path="/pet/:id" element={<Pet />} />
            <Route path="/GraphPage" element={<GraphPage />} />
            <Route path="/inquiryList" element={<InquiryList />} />
            <Route path="/inquiryDetail/:id" element={<Inquiry />} />
            <Route path="/inquiryAnswer/:id" element={<InquiryAnswer />} />
            <Route path="/aimodel" element={<AIModel />} />
          </Routes>
        </div>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
