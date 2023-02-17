import './App.css';
import { Routes, Route } from "react-router-dom";

import LoginView from './components/Login/Login';
import SignupView from './components/Login/Signup';
// import from "module";

import IDPWView from './components/Login/IDPW';
import EMverifyView from './components/Login/EMverify';
import EmailView from './components/Login/Email';
import SUSCView from './components/Login/SUSC';
import Noticeboard from './components/MenuComp/Notice/Noticeboard';
import KakaoLogin from './components/Login/KakaoLogin';

import KakaoMap from './components/KakaoMap';
import HomePage from './view/HomePage';
import MainPage from './view/MainPage';
import InfoModify from './components/MenuComp/Myinfo/InfoModify';
import FAQdetail from './components/FAQ/FAQdetail';
import NoticeWriteAdmin from './components/MenuComp/Notice/NoticeWriteAdmin';
import NoticeModifyAdmin from './components/MenuComp/Notice/NoticeModifyAdmin';
import axiosInstance from '././service/axiosInstance';

import UserDelete from './components/MenuComp/Myinfo/UserDelete';
import { useState } from 'react';

// 작성자 : 최예정
// 파일의 역할 : 링크 연결, 백 -> 프론트 GET
// 작성날짜 : 2023-01-25

// 업데이트 작성자 : 최예정
// 업데이트 날짜 : 2023-01-26
// 업데이트 작성자 : 유열림
// 업데이트 내용 : 라우트 추가
// 업데이트 날짜 : 2023-01-30

function App() {
  
  // eclips -> vscode (GET)
  // useEffect(() => {
  //   axios.get('http//localhost:4040/irunyou/').then((response) => {
  //     const data = response.data;
  //     const result = data.result;
  //     if (!result) {
  //       alert(data.message)
  //     }
  //   })
  // }, []);

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/MainPage" element={<MainPage />}></Route>
        <Route path="/Signup" element={<SignupView />}  />
        <Route path="/Login" element={<LoginView />}  />
        <Route path="/IDPW" element={<IDPWView />}  />
        <Route path="/EMverify" element={<EMverifyView />}  />
        <Route path="/Email" element={<EmailView />} />
        <Route path="/SUSC" element={<SUSCView />} />
        <Route path="/Login/kakao" element={<KakaoLogin />} />

        <Route path="/FAQdetail" element={<FAQdetail />} />

        <Route path="/InfoModify" element={<InfoModify />} />

        <Route path="/Notice" element={<Noticeboard />}/>
        <Route path="/Notice/Admin/Write" element={<NoticeWriteAdmin/>}/>
        <Route path="/Notice/Admin/Modify" element={<NoticeModifyAdmin/>}/>
        
        <Route path="/UserDelete" element={<UserDelete/>}/>

        {/* <Route path="*" element={<NotFound />} /> */}
        {/* <Route path='/' element={< Autehntication />}/> */}
        <Route  />
      </Routes>
    </div>
  );
}

export default App;
