import styled from "styled-components";
import { Layout } from "antd";
const { Header } = Layout;

export const Logo = styled.div`
  float: left;
  cursor: pointer;

  img {
    height: 52px;
    width: auto;
  }
`;

export const OTUSHeader = styled(Header)`
  box-shadow: 0 2px 8px #f0f1f2;
`;
