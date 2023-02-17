import React, { useEffect, useState, useContext } from "react";

import MenuLogo from "../Mainmenu/MenuLogo"
import "./Noticeboard.css";
import { Button } from "@mui/material";
import NoticeWriteAdmin from "./NoticeWriteAdmin";
import { Navigate, useNavigate } from "react-router";
import path from "path";
import NoticeItemList from "./NoticeItemList";

import LogoImg from '../../../assets/logo2.png';

export default function NoticeHeader() {

    const isAdmin = useState(true);

    const movePage = useNavigate();

    return (
        <>
            <div onClick={() => { movePage("/MainPage") }} style={{ cursor: "pointer" }}>
                <div className="menu-logo">
                <img className="logo-img" src={ LogoImg } alt="" />
                <div className="logo-txt" >I Run You</div>
                <i className="fa-solid fa-bars-staggered"></i>
                <h1 className="notice-logo-text">공지사항</h1>
            </div>
            </div>
            <div className="notice-information">
                <div className="notice-information-text">
                    <div>공지사항</div>
                    <div>I RUN YOU의 공지사항 게시판 입니다.</div>
                </div>
                {isAdmin && <Button color="success" onClick={() => { movePage("/Notice/Admin/Write") }} style={{ padding: "0" }}>공지 작성</Button>}
            </div>
        </>
    )

}