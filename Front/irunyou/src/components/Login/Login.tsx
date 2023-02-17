import "./Login.css";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";


// 작성자 : 최예정
// 파일의 역할 : id, password 찾기 html
// 작성날짜 : 2023-01-13

// 업데이트 작성자 : 최예정
// 업데이트 날짜 : 2023-01-30

// 홍지혜 2023-02-01
// 로그인 -> 토큰 로컬스토리지 저장

// 홍지혜 2023-02-06
// 아이디 저장 -> 쿠키에 이메일 정보 저장

export default function Login() {

    const [userEmail, setUserEmail] = useState<string>('');
    const [userPassword, setUserPassword] = useState<string>('');

    const [isRemember, setIsRemember] = useState(false);
    const [cookie, setCookie, removeCookie] = useCookies(['rememberEmail']);

    const movePage = useNavigate();

    const LoginAction = () => {

        return axios.post("http://localhost:4040/auth/login", {
            userEmail,
            userPassword
        }).then((response) => {
            
                const responseMessage = response.data.message;
                const tokendata = response.data.data;

                if (!response.data.status) {
                    alert(responseMessage);
                    movePage("/Login");

                } else {
                    localStorage.setItem('token', tokendata.token);
                    localStorage.setItem('expiration', "" + tokendata.expiration);
                    alert(responseMessage);
                    movePage("/MainPage", {state : {userNickname : tokendata.userNickname}});
                }

            }).catch((error) => {
                alert(error.message);
                movePage("/Login");
            })
    }

    const rememberEmail = (e : any) => {
        if(e.target.checked) {
            setIsRemember(true)
            setCookie('rememberEmail',userEmail,{maxAge : 2000});
        } else {
            setIsRemember(false)
            removeCookie('rememberEmail');
        }
    }

    // 모든 페이지가 이전으로 가도 토큰은 저장되어 있으나
    // 보이는 화면 상 로그인이 풀려있는 것처럼 보임

    useEffect(()=> {
        if(localStorage.getItem('token')) {
            window.location.href="/MainPage";
        }

        if(cookie.rememberEmail !== undefined) {
            setUserEmail(cookie.rememberEmail);
            setIsRemember(true);
        }
    },[]);
    
    // 문경원 2023 02 09
    // 아이디 비밀번호 치고 엔터쳤을 경우 로그인이 되는 기능
    const handleOnClick = (e : any) => {
        LoginAction();
    }
    
    const handleOnKeyPress = (e : any) => {
        if(e.key === 'Enter') {
            handleOnClick(e.handleOnKeyPress);
        }
    }

    // 카카오 로그인
    // const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`
    
    // const KaKaoLogin = () => {
    //     window.location.href = KAKAO_AUTH_URL;
    // }

    return (
        <div className="login-container">
            {/* 로그인 전체 form */}
            <div id="input-form" className="form-contant">
                <h1 className="login-form">LOGIN</h1>
                {/* 아이디(이메일), 비밀번호 입력창 */}
                <fieldset>
                    <div className="input-border">
                        <input className="email-input login-input" type="text" placeholder="아이디(이메일)" value={cookie.rememberEmail} required onChange={(e) => setUserEmail(e.target.value)} />
                        <input className="password-input login-input" type="password" onKeyPress={handleOnKeyPress} placeholder="비밀번호(8~16자 숫자, 영문, 특수문자)" required onChange={(e) => setUserPassword(e.target.value)} />
                    </div>
                </fieldset>
                <div className="box">
                    <input className="id-save" type="checkbox" id="checkid" name="checkid" onChange={(e) => rememberEmail(e)} checked={isRemember}/>
                    <label htmlFor="checkid"> 아이디 저장</label>
                </div>
                {/* 아이디(이메일), 비밀번호 찾는 링크 */}
                <div className="email-password-find">
                    <Link to="/IDPW">아이디 / 비밀번호 찾기</Link>
                </div>
                {/* 간편 로그인
                <div className="other-login">

                    <div className="line"></div>
                    <div className="other-login-text">간편 로그인</div>
                    <div className="line"></div>
                </div> */}
                {/* <div className="kakao-login">
                    <img onClick={KaKaoLogin} className="kakao-login-img" src={kakao} />
                </div> */}
                {/* 로그인, 회원가입 각 버튼 */}
                <div className="login-signup-button">
                        <button type="button" className="login-button button" onClick={() => LoginAction()}>로그인</button>
                    <Link to="/Signup">
                        <button className="signup-button button" >회원가입</button>
                    </Link>
                </div>
            </div>
        </div>
    );
}