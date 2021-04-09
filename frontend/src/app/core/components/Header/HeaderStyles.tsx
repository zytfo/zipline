import styled from "styled-components";
import {Layout} from "antd";
const { Header } = Layout;

export const Logo = styled.span`
  font-size: 32px;
  color: #fff;
  letter-spacing: 3px;
  font-weight: bold;
  float: left;
  margin: 0 24px 0 0;
`;

export const OTUSHeader = styled(Header)`
  box-shadow: 0 2px 8px #f0f1f2;
`;
