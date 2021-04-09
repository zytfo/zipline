import styled from "styled-components";
import {Divider, Input, Layout, Tag} from "antd";
const { Search } = Input;

export const NewsSearch = styled(Search)`
  margin-bottom: 8px;
  width: 100%;
`;

export const NewsLayout = styled(Layout)`
  margin: 0 0 24px 0;
`;

export const NewsContainer = styled.div`
`;

export const NewsItem = styled.div`
  padding: 15px 20px;
  margin-bottom: 15px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 1px 0 0 #dce1e6, 0 0 0 1px #dce1e6;
`;

export const ActionArea = styled.div`
  cursor: pointer;
`;

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

export const TagContainer = styled.div`
  max-width: 450px;
  text-align: end;
`;

export const NewsTag = styled(Tag)`
  margin-bottom: 8px;
  cursor: pointer;
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

export const CommentDivider = styled(Divider)`
  margin: 12px 0;
`;
