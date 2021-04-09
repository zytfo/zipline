import styled from "styled-components";
import {
  DeleteOutlined,
  EditOutlined,
  HeartOutlined,
  HeartTwoTone,
} from "@ant-design/icons/lib";
import { Divider, Popconfirm } from "antd";

export const AuthorContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: -12px 0 12px;
`;

export const AuthorTitle = styled.div`
  color: #787c7e;
  font-size: 12px;
`;

export const DateInfo = styled.div`
  color: #787c7e;
  font-size: 12px;
`;

export const LowerMenuContainer = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
`;

export const Likes = styled.div`
  display: flex;
  align-items: center;
  cursor: pointer;
`;

export const LikesCount = styled.div`
  padding-left: 12px;
  font-size: 16px;
`;

export const LikedIcon = styled(HeartTwoTone)`
  font-size: 22px;
`;

export const UnlikeIcon = styled(HeartOutlined)`
  font-size: 22px;
  opacity: 0.5;

  &:hover {
    opacity: 1;
  }
`;

export const EditIcon = styled(EditOutlined)`
  font-size: 22px;
  opacity: 0.5;
  margin-right: 18px;

  &:hover {
    opacity: 1;
  }
`;

export const DeleteIcon = styled(DeleteOutlined)`
  font-size: 22px;
  opacity: 0.5;

  &:hover {
    opacity: 1;
  }
`;

export const CommentDivider = styled(Divider)`
  margin: 12px 0;
`;

export const ConfirmDeletion = styled(Popconfirm)``;
