import styled, { css } from "styled-components";
import { Divider, Popconfirm, Typography } from "antd";
import {
  DeleteTwoTone,
  EditOutlined,
  HeartOutlined,
  HeartTwoTone,
} from "@ant-design/icons/lib";
const { Paragraph } = Typography;

export const AuthorContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
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

export const ActionSet = styled.div`
  display: none;
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

export const PublicationItem = styled.div<{
  isAuthor: boolean;
  visible: boolean;
}>`
  padding: 15px 20px;
  margin-bottom: 15px;
  background-color: #fff;
  border-radius: 3px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);

  ${(props: any) =>
    props.isAuthor &&
    css`
      &:hover ${ActionSet} {
        display: block;
      }
    `}

  ${(props: any) =>
    props.visible &&
    css`
      ${ActionSet} {
        display: block;
      }

      ${DeleteIcon} {
        opacity: 1;
      }
    `}
`;

export const ActionArea = styled.div`
  cursor: pointer;
`;

export const PublicationDivider = styled(Divider)`
  margin: 12px 0;
`;

export const PublicationContent = styled(Paragraph)`
  margin-top: 12px;
  
  ul, li {
    list-style: none !important;
    margin: 0 !important;
    padding: 0 !important;
  }
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

export const DeleteIcon = styled(DeleteTwoTone)`
  font-size: 22px;
  opacity: 0.5;

  &:hover {
    opacity: 1;
  }
`;

export const ConfirmDeletion = styled(Popconfirm)``;
