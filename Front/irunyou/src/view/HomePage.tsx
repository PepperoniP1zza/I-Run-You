// 첫 홈화면 (로그인이 안 된 상태)
import React from 'react';
import './views.css';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import Button from '@mui/material/Button';
import { Link } from '@mui/material';

const theme = createTheme({
  palette: {
    primary: {
      main: '#b6cf55',
    },
    secondary: {
      // 나중에 바꿀 색깔
      main: '#11cb5f',
    },
  },
});


export default function HomePage() {
  return (
    <div className="home-main-img">
      <div className="home-container">
        <div className="txt-container">
          <div className="home-title">I Run You는 함께 뛰는 문화를 만듭니다.</div>
          <br />
          <div className="home-desc">
            I Run You는 SNS기반의 러닝 스케줄 서비스로써<br />
            같이 달릴 친구를 찾아드립니다.<br />
            지금 I Run You에서 새로운 친구들과 함께 달려보세요!<br />
          </div>
          <div className="home-desc-detail"><br />
            😁이런 사람들이 사용하면 좋아요!<br />
             &nbsp;&nbsp;- 혼자서 뛰는 것이 심심하신 분!<br />
             &nbsp;&nbsp;- 달리기에 자신이 없거나 의지박약이신 분!<br />
             &nbsp;&nbsp;- 가벼운 운동으로 새로운 인연을 원하시는 분!<br />
          </div>
        <div className="home-btn-container">
          <ThemeProvider theme={theme}>
            <Button className="home-first-btn" 
              variant="contained" color="primary" size="large" 
                sx={{fontSize: "1.2vw", textAlign: "center", padding:0}}
                href="/Login"
              >로그인</Button>
            <Button className="home-first-btn" 
              variant="contained" color="primary" size="large" 
                sx={{fontSize: "1.2vw", textAlign: "center", padding:0}}
                href="/Signup"
              >회원가입</Button>
          </ThemeProvider>
        </div>
        </div>
      </div>
      <div className="home-footer">
        <div className="copyrights">© 2023. All rights reserved.</div>
        <div className="contact-us">Contact Us</div>
      </div>
    </div>
  )
}