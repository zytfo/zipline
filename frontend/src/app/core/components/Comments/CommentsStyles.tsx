import styled from "styled-components";
import {Button, Col, Row} from "antd";

export const CommentContainer = styled.div`
  text-align: left;
  background-color: #fff;
  padding: 0 20px;
`;

export const LikesCounter = styled.span`
  margin-left: 4px;
`;

export const LoadMoreButton = styled(Button)`
  &:hover {
    background-color: #f8f8f8;
  }
`;

export const NewCommentRow = styled(Row)`
  margin-bottom: 20px;
`;

export const CommentButtonCol = styled(Col)`
  padding-left: 12px;
`;
