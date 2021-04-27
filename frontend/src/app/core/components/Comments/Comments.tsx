import React, {
  FunctionComponent,
  useEffect,
  useState,
} from "react";
import { withRouter } from "react-router-dom";
import moment from "moment";
import { Comment, Tooltip, Avatar, Button, Input, Col } from "antd";
import {
  HeartOutlined,
  HeartTwoTone,
  SmileOutlined,
} from "@ant-design/icons/lib";
import {
  CommentButtonCol,
  CommentContainer,
  LikesCounter,
  LoadMoreButton,
  NewCommentRow,
} from "./CommentsStyles";
import { commentsService } from "./services/CommentsService";
import { backendService } from "../../services/BackendService";
import { CommentItem } from "./interfaces/Comment";
import Highlighter from "react-highlight-words";
const { TextArea } = Input;

const Comments: FunctionComponent<any> = ({ postId, postType, ...props }) => {
  const [inputValue, setInputValue] = useState<string>("");
  const [content, setContent] = useState<CommentItem[]>([]);
  const [endOfContent, setEndOfContent] = useState<boolean>(false);
  const [pageNumber, setPageNumber] = useState<number>(0);
  const [reverted, setReverted] = useState<boolean>(false);

  useEffect(() => {
    loadComments();
  }, []);

  const loadComments = () => {
    const sort = reverted ? "DESC" : "ASC";
    commentsService
      .getComments(pageNumber, postType, postId, sort)
      .then((response) => {
        if (reverted) {
          const revertedArray = response.data.data.reverse();
          setContent((oldContent) => [...revertedArray, ...oldContent]);
        } else {
          setContent((oldContent) => [...oldContent, ...response.data.data]);
        }

        if (response.data.data.length < 10) {
          setEndOfContent(true);
        }
        setPageNumber(pageNumber + 1);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const loadCommentsReverted = () => {
    commentsService
      .getComments(0, postType, postId, "DESC")
      .then((response) => {
        const revertedArray = response.data.data.reverse();
        setContent(revertedArray);

        if (response.data.data.length < 10) {
          setEndOfContent(true);
        }
        setPageNumber(1);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const postComment = () => {
    commentsService
      .postComment(inputValue, postId, postType)
      .then(() => {
        setInputValue("");
        setReverted(true);
        setEndOfContent(false);
        loadCommentsReverted();
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const likeComment = (id: number) => {
    commentsService
      .likeComment(id)
      .then(() => {
        const index = content.findIndex((element: CommentItem) => {
          return element.commentId === id;
        });
        let newContent = [...content];

        newContent[index].selfLiked = !newContent[index].selfLiked;
        newContent[index].likesCount += newContent[index].selfLiked ? 1 : -1;

        setContent(newContent);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const tickerClick = (event) => {
    event.stopPropagation();
    const tick = event.target.textContent;
    props.history.push(`/publications?t=${tick}`);
  };

  const getTicker = ({ children }) => {
    return (
      <span
        style={{
          color: "#1890ff",
          textDecoration: "underline",
          background: "none",
          cursor: "pointer",
        }}
        onClick={tickerClick}
      >
        {children}
      </span>
    );
  };

  const renderComment = (commentsToRender: CommentItem[]) => {
    return commentsToRender.map((comment, index) => (
      <Comment
        key={index}
        actions={[
          <Tooltip title="Like">
            <span onClick={() => likeComment(comment.commentId)}>
              {comment.selfLiked ? (
                <HeartTwoTone twoToneColor="#eb2f96" />
              ) : (
                <HeartOutlined />
              )}
              <LikesCounter>{comment.likesCount}</LikesCounter>
            </span>
          </Tooltip>,
        ]}
        author={<a>{comment.author}</a>}
        avatar={<Avatar alt={comment.author} icon={<SmileOutlined />} />}
        content={
          <Highlighter
            searchWords={comment?.tickers || []}
            autoEscape={true}
            textToHighlight={comment?.message || ""}
            highlightTag={getTicker}
          />
        }
        datetime={
          <Tooltip title={moment(comment.created).format("LLL")}>
            <span>{moment(comment.created).fromNow()}</span>
          </Tooltip>
        }
      />
    ));
  };

  return (
    <CommentContainer>
      {!endOfContent && reverted && (
        <LoadMoreButton type="link" block onClick={loadComments}>
          Load Previous
        </LoadMoreButton>
      )}
      {renderComment(content)}
      {!endOfContent && !reverted && (
        <LoadMoreButton type="link" block onClick={loadComments}>
          Load Next
        </LoadMoreButton>
      )}
      <NewCommentRow>
        <Col span={20}>
          <TextArea
            size="large"
            autoSize={{ minRows: 1, maxRows: 6 }}
            placeholder={"Write comment..."}
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
          />
        </Col>
        <CommentButtonCol span={4}>
          <Button block type="primary" size="large" onClick={postComment}>
            Send
          </Button>
        </CommentButtonCol>
      </NewCommentRow>
    </CommentContainer>
  );
};

export default withRouter(Comments);
