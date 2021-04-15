import styled, { css } from "styled-components";
import {DeleteTwoTone, ExportOutlined} from "@ant-design/icons/lib";
import {Popconfirm} from "antd";

export const WalletsContainer = styled.div`
  margin: 24px 0;
`;

export const NewWalletContainer = styled.div`
  display: flex;
  margin-bottom: 15px;
`;

export const WalletName = styled.div`
  font-weight: bold;
  font-size: 18px;
`;

export const ActionSet = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 15px;

  Button {
    margin-left: 6px;
  }
`;

export const WalletActions = styled.div`
  display: none;
  position: absolute;
  top: 18px;
  right: 0;
`;

export const Wallet = styled.div<{
  visible: boolean;
}>`
  cursor: pointer;
  padding: 15px 20px;
  margin-bottom: 15px;
  background-color: #fff;
  border-radius: 3px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);
  position: relative;
  &:hover ${WalletActions} {
    display: block;
  }
  
  ${(props: any) =>
  props.visible &&
  css`
      ${WalletActions} {
        display: block;
      }

      ${DeleteIcon} {
        opacity: 1;
      }
    `}
`;

export const DeleteIcon = styled(DeleteTwoTone)`
  font-size: 22px;
  opacity: 0.5;
  margin-right: 18px;
  cursor: pointer;

  &:hover {
    opacity: 1;
  }
`;

export const ExportIcon = styled(ExportOutlined)`
  font-size: 22px;
  opacity: 0.5;
  margin-right: 18px;
  cursor: pointer;

  &:hover {
    opacity: 1;
  }
`;

export  const PrivateKeyContent = styled.div`
  display: flex;
  justify-content: center;
  
  Button {
    margin-left: 6px;
  }
`;

export const ImportWalletContainer = styled.div`
  display: flex;
  flex-direction: column;
  
  Input, Button {
    margin-bottom: 15px;
  }
`;

export const ConfirmDeletion = styled(Popconfirm)``;
