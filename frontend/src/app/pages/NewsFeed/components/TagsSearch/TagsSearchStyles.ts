import styled from "styled-components";
import { Input, Tag } from "antd";

export const TagInput = styled(Input)`
  width: 100px;
  margin-right: 8px;
  vertical-align: top;
  margin-bottom: 8px;
`;

export const EditTag = styled(Tag)`
  user-select: none;
  margin-bottom: 8px;
`;

export const AddTag = styled(Tag)`
  background: #fff;
  border-style: dashed;
  cursor: pointer;
  margin-bottom: 8px;
`;
