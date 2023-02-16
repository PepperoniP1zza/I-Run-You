import './parkinfo.css';
import axiosInstance from "../../../service/axiosInstance";
import { useState, useEffect } from 'react';
import './Comment.css';
import CommentItem from './CommentItem';
import { Button } from '@mui/material';
import useStore from './Store';
import { createTheme, ThemeProvider } from '@mui/material/styles';

interface Props {
    runScheduleIndex: number;
}

interface Comment {
    commentIndex: number;
    commentScheduleIndex: number;
    commentWriter: string;
    commentContent: string;
    commentDatetime: string;
    commentLike: number;
}


export default function ScheduleComment({ runScheduleIndex }: Props) {

    const [commentArray, setCommentArray] = useState<Comment[]>([]);
    const [commentContent,setCommentContent] = useState(String);
    const { parkInfoState } = useStore();
    const [flag, setFlag] = useState<boolean>(false);


    // 댓글 목록 불러오는 함수
    const getScheduleComment = async () => {
        await axiosInstance.get(
            "irunyou/comment/", {
            params: {
                schIdx: runScheduleIndex,
            }
        })
            .then((response) => {
                // if (!response.data.state) {
                //     return alert(response.data.message);
                // }
                setCommentArray(response.data.data);

            }).catch((error) => {
                alert(error.message);
            })
    };

    // 댓글 등록 함수
    const registComment = async () => {
        await axiosInstance.put(
            "irunyou/comment/",{
                commentScheduleIndex : runScheduleIndex,
                commentContent : commentContent
            }
        ).then((response) => {
            // setFlag(!flag);
            alert(response.data.message);
            setCommentContent('');
            setCommentArray(response.data.data);
        }).catch((error) => {
            alert(error.message);
        })
    }


    useEffect(() => {
        getScheduleComment();
    }, [runScheduleIndex, setCommentArray])
    // 댓글이 등록,수정,삭제될때마다 댓글 리스트 재 실행 -> 실시간으로 댓글 확인 가능


    return (
        <div className='comment-wraper'>
            <div className='comment-header-bar'>
                <div>전체 댓글</div>
                {commentArray ?
                (<div>{commentArray.length} 개</div>):
                (<div>0 개</div>)}
            </div>
            <div className='comment-read'></div>
            {commentArray && commentArray.length !== 0 ?
                commentArray.map((commentArray: any) => (
                    <CommentItem key={commentArray.commentIndex} comment={commentArray} 
                    setCommentArray={setCommentArray}/>
                ))
                : (
                    <div className='comment-empty'>댓글이 없습니다.</div>
                )
            }
            <div className='comment-write-container'>
                <div className='comment-input-container'>
                    <textarea 
                    id="CtextArea"
                    placeholder='타인의 권리를 침해하거나 명예를 훼손하는 댓글은 운영원칙 및 관련 법률에 제재를 받을 수 있습니다.'
                    value={commentContent}
                    onChange={(e) => setCommentContent(e.target.value)}
                    ></textarea>
                </div>
                <div className='comment-write-button-div'>
                <Button variant="contained" disableElevation color='success'
                    style={{
                        width: "90px",
                        height:"30px",
                        fontSize:"12px",
                        color : "white"
                    }}
                    onClick={()=>registComment()}>
                        댓글등록
                    </Button>
                </div>
            </div>
        </div>
    )
}