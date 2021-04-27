import styled from "styled-components";
import {Button, Divider} from "antd";

export const UserCardContainer = styled.div`
  background-color: #fff;
  border-radius: 3px;
  box-shadow: 0 0 20px rgba(0,0,0,0.08);
`;

export const UserBlockContainer = styled.div`
  position: sticky;
  top: 12px;
  margin-top: 24px;
`;

export const MainInfo = styled.div`
  padding: 15px 20px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const Username = styled.div`
  margin-top: 12px;
  font-size: 1.6rem;
`;

export const Email = styled.div`
  font-size: 1.2rem;
  color: rgba(0, 0, 0, 0.6);
`;

export const UserCardDivider = styled(Divider)`
  margin: 24px 0 0;
`;

export const BlocksDivider = styled(Divider)`
  margin: 12px 0;
`;

export const LogOutButton = styled(Button)`
  width: 100%;
`;
