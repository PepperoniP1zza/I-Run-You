import React, { useEffect, useState, useContext} from "react";
import axios, { AxiosResponse } from "axios";

import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { Pagination, PaginationItem } from "@mui/material";
import {Button} from "@mui/material";
import TokenContext from "../../../service/TokenContext";
import { useNavigate } from "react-router";

export default function NoticeItemList() {
    const [noticeList, setNoticeList] = useState<any[]>([]);
    const [totalElements, setTotalElements] = useState(1);
    const [totalPages, setTotlaPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);
    
    const AdminContext = useContext(TokenContext);

    const movePage = useNavigate();
    const onPageChange = (e: React.ChangeEvent<unknown>, page: number) => {
        setCurrentPage(page);
        getNoticeList(page);
    }
    
    const clickModifyNotice = () => {
    
    }

    const deleteNotice = async(index : number) => {
        
        let isDelete = confirm("정말 삭제하시겠습니까?")
 
        if(isDelete) {
            await axios
            .delete("http://localhost:4040/irunyou/notice", {
                params : {
                    noticeIndex : index
                }
            }).then((response) => {
               if(!response.data.status) {
                 return  alert(response.data.message);
               } 
               alert(response.data.message);
               window.location.reload();
            }).catch((error) => {
                alert(error.message)
            });
        } 

    }

    const getNoticeList = async (page: number) => {
        await axios
            .get(`http://localhost:4040/irunyou/notice?page=${page}`, {
                headers : {
                    Authorization: `Bearer ${window.localStorage.getItem('token')}`
                }
            })
            .then((response) => {
                const noticeList = response.data.data.data;
                setTotalElements(response.data.data.pageInfoDto.totalElements);
                setTotlaPages(response.data.data.pageInfoDto.totalPages);
                setNoticeList(noticeList);
            })
            .catch((error) => { });

    };


    useEffect(() => {
        getNoticeList(1);
    }, []);

    return (
        <>
            <div className="notice-key-container">
                <div>번호</div>
                <div>제목</div>
            </div>
            <div className="notice-list-container">
                {noticeList &&
                    noticeList.map((notice: any, index) => (
                        <Accordion>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon />}
                                aria-controls="panel1a-content"
                                id="panel1a-header"
                            >
                                <div className="noticeboard-item-container">
                                    <div className="notice-board-number">{(index + 1) + (currentPage - 1) * (totalPages + 1)}</div>
                                    <div className="noticeboard-title">{notice.noticeTitle}</div>
                                </div>
                            </AccordionSummary>
                            <AccordionDetails>
                                <div className="notice-content-container">
                                    <div className="notice-content">{notice.noticeContent}</div>
                                </div>
                                {AdminContext.isAdmin && <Button color="success" onClick={()=>{deleteNotice(notice.noticeIndex)}} style={{padding : "0 20px"}}>공지 삭제</Button>}  
                                {AdminContext.isAdmin && <Button color="success" onClick={()=>{clickModifyNotice()}} style={{padding : "0 20px"}}>공지 수정</Button>}  
                            </AccordionDetails>
                        </Accordion>
                    ))}
            </div>
            <Pagination
                count={totalPages}
                page={currentPage}
                onChange={onPageChange}
                size="medium"
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    padding: "15px 0",
                }}
                renderItem={(item) => (
                    <PaginationItem {...item} sx={{ fontSize: 12 }} />
                )}
            />
        </>
    );
}