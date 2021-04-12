import styled from "styled-components";
import {Layout} from "antd";
const { Header } = Layout;

export const Logo = styled.span`
  font-size: 28px;
  color: #F0B90B;
  font-family: 'Amaranth', sans-serif;
  font-weight: 700;
  float: left;
  margin: 0 24px 0 0;
`;

export const OTUSHeader = styled(Header)`
  box-shadow: 0 2px 8px #f0f1f2;
`;
