import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import {
  ActionSet,
  ConfirmDeletion,
  DeleteIcon,
  ExportIcon,
  ImportWalletContainer,
  NewWalletContainer,
  PrivateKeyContent,
  Wallet,
  WalletActions,
  WalletName,
  WalletsContainer,
} from "./WalletsStyles";
import { walletService } from "./services/WalletService";
import { backendService } from "../../core/services/BackendService";
import {
  Button,
  Input,
  message,
  Modal,
  Result,
  Skeleton,
  Spin,
  Typography,
} from "antd";
import {
  CopyOutlined,
  QuestionCircleOutlined,
  WalletOutlined,
} from "@ant-design/icons/lib";
const { Paragraph } = Typography;

const Wallets = () => {
  const [wallets, setWallets] = useState<any[]>([]);
  const [noWallets, setNoWallets] = useState<boolean>(false);
  const [newWallet, setNewWallet] = useState<boolean>(false);
  const [walletName, setWalletName] = useState<string>("");

  const [visibleExport, setVisibleExport] = useState<boolean>(false);
  const [privateKey, setPrivateKey] = useState<string>("");

  const [importForm, setImportForm] = useState<boolean>();
  const [importPrivateKey, setImportPrivateKey] = useState<string>("");

  const [visibleWalletDetails, setVisibleWalletDetails] = useState<boolean>(
    false
  );
  const [walletData, setWalletData] = useState<any>(null);
  const [walletWithdrawals, setWalletWithdrawals] = useState<any>(null);
  const [walletBalance, setWalletBalance] = useState<any>(null);
  const [withdrawalLoading, setWithdrawalLoading] = useState<boolean>(false);

  const [visible, setVisible] = useState<boolean>(false);

  useEffect(() => {
    walletService
      .getWallets()
      .then((response) => {
        setWallets(response.data.data);
        if (response.data.data.length === 0) {
          setNoWallets(true);
        }
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  }, []);

  useEffect(() => {
    if (walletData) {
      getBalanceAndWithdrawals(walletData);
    }
  }, [walletData]);

  const getBalanceAndWithdrawals = (wallet) => {
    walletService
      .getBalance(wallet.walletId)
      .then((response) => {
        setWalletBalance(response.data.data);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
    walletService
      .getWithdrawals(wallet.walletId)
      .then((response) => {
        setWalletWithdrawals(response.data.data);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  };

  const createNewWallet = () => {
    walletService
      .newWallet(walletName)
      .then((response) => {
        setWallets((oldContent) => [response.data.data, ...oldContent]);
        setNewWallet(false);
        setWalletName("");
        if (noWallets) {
          setNoWallets(false);
        }
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  const deleteWallet = (walletId: number) => {
    walletService
      .deleteWallet(walletId)
      .then(() => {
        const index = wallets?.findIndex((elem) => {
          return elem.walletId === walletId;
        });
        const newArray = [...wallets];
        newArray.splice(index, 1);
        setWallets(newArray);
        if (newArray.length === 0) {
          setNoWallets(true);
        }
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  const exportWallet = (walletId: number) => {
    setVisibleExport(true);
    setPrivateKey("");
    walletService
      .exportWallet(walletId)
      .then((response) => {
        setPrivateKey(response.data.data.privateKey);
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  const copyToClipboard = () => {
    navigator.clipboard.writeText(privateKey);
    message.success("Copied!");
  };

  const importWallet = () => {
    const data = {
      privateKey: importPrivateKey,
      walletName: walletName,
    };
    walletService
      .importWallet(data)
      .then((response) => {
        setWallets((oldContent) => [...oldContent, response.data.data]);
        setImportPrivateKey("");
        setWalletName("");
        setImportForm(false);
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  const closeWalletDetails = () => {
    setVisibleWalletDetails(false);
    setWalletData(null);
    setWalletWithdrawals(null);
    setWalletBalance(null);
  };

  const openWalletDetails = (wallet: any) => {
    setVisibleWalletDetails(true);
    setWalletData(wallet);
  };

  const withdrawWallet = () => {
    setWithdrawalLoading(true);
    walletService
      .withdrawWallet(walletData.walletId)
      .then(() => {
        setWalletBalance(null);
        setWalletWithdrawals(null);
        setWithdrawalLoading(false);
        getBalanceAndWithdrawals(walletData);
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  return (
    <WalletsContainer>
      {wallets.length === 0 && !noWallets && (
        <Skeleton paragraph={{ rows: 3 }} active />
      )}
      {noWallets && (
        <Result
          icon={<WalletOutlined />}
          title="Seems like you don't have wallets"
          subTitle="Create or import wallet and join our marketplace now!"
          extra={[
            <Button
              size="large"
              type="primary"
              key="new-wallet"
              onClick={() => {
                setImportForm(false);
                setNewWallet(true);
              }}
            >
              Create Wallet
            </Button>,
            <Button
              size="large"
              type="primary"
              key="import-wallet"
              onClick={() => {
                setNewWallet(false);
                setImportForm(true);
              }}
            >
              Import Wallet
            </Button>,
          ]}
        />
      )}
      {wallets.length !== 0 && (
        <ActionSet>
          <Button
            size="large"
            type="primary"
            key="new-wallet"
            onClick={() => {
              setImportForm(false);
              setNewWallet(true);
            }}
          >
            Create Wallet
          </Button>
          <Button
            size="large"
            type="primary"
            key="import-wallet"
            onClick={() => {
              setNewWallet(false);
              setImportForm(true);
            }}
          >
            Import Wallet
          </Button>
        </ActionSet>
      )}
      {newWallet && (
        <NewWalletContainer>
          <Input
            size="large"
            value={walletName}
            onChange={(event) => setWalletName(event.target.value)}
            placeholder="Wallet Name"
          />
          <Button
            size="large"
            type="primary"
            onClick={createNewWallet}
            style={{ marginLeft: "24px" }}
          >
            Create
          </Button>
        </NewWalletContainer>
      )}
      {importForm && (
        <ImportWalletContainer>
          <Input
            size="large"
            value={importPrivateKey}
            onChange={(event) => setImportPrivateKey(event.target.value)}
            placeholder="Private Key"
          />
          <Input
            size="large"
            value={walletName}
            onChange={(event) => setWalletName(event.target.value)}
            placeholder="Wallet Name"
          />
          <Button size="large" type="primary" onClick={importWallet}>
            Import
          </Button>
        </ImportWalletContainer>
      )}
      {wallets.map((wallet) => (
        <Wallet
          onBlur={() => setVisible(false)}
          visible={visible}
          key={wallet.walletId}
          onClick={() => openWalletDetails(wallet)}
        >
          <WalletName>{wallet.name}</WalletName>
          <div>Address: 0x{wallet.address}</div>
          <WalletActions onClick={(e) => e.stopPropagation()}>
            <ExportIcon
              onClick={(e) => {
                e.stopPropagation();
                exportWallet(wallet.walletId);
              }}
            />
            <ConfirmDeletion
              placement="top"
              title={"Delete this wallet?"}
              icon={<QuestionCircleOutlined style={{ color: "red" }} />}
              onConfirm={() => {
                deleteWallet(wallet.walletId);
              }}
              okText="Yes"
              cancelText="No"
            >
              <DeleteIcon
                twoToneColor="red"
                onClick={(e) => {
                  e.stopPropagation();
                  setVisible(true);
                }}
              />
            </ConfirmDeletion>
          </WalletActions>
        </Wallet>
      ))}
      <Modal
        visible={visibleExport}
        title="Export Wallet"
        onCancel={() => setVisibleExport(false)}
        footer={[
          <Button key="close" onClick={() => setVisibleExport(false)}>
            Close
          </Button>,
        ]}
      >
        <PrivateKeyContent>
          {privateKey === "" ? (
            <Spin size="large" />
          ) : (
            <>
              <Input value={`${privateKey.substr(0, 4)}********`} readOnly />
              <Button type="primary" onClick={copyToClipboard}>
                <CopyOutlined /> Copy to Clipboard
              </Button>
            </>
          )}
        </PrivateKeyContent>
      </Modal>
      <Modal
        visible={visibleWalletDetails}
        title="Wallet Details"
        onCancel={closeWalletDetails}
        footer={[
          <Button key="back" onClick={closeWalletDetails}>
            Cancel
          </Button>,
        ]}
      >
        {walletData ? (
          <Paragraph> Address: 0x{walletData.address}</Paragraph>
        ) : (
          <Spin size="small" />
        )}
        <Paragraph>
          Balance:{" "}
          {walletBalance || walletBalance === 0 ? (
            `${(walletBalance / 1000000000000000000).toFixed(20)} BNB`
          ) : (
            <Spin size="small" />
          )}
        </Paragraph>
        <Paragraph>
          Withdrawals:{" "}
          {walletWithdrawals || walletWithdrawals === 0 ? (
            <>
              {walletWithdrawals / 1000000000000000000} BNB
              <div>
                <Button
                  loading={withdrawalLoading}
                  type="primary"
                  onClick={withdrawWallet}
                  disabled={walletWithdrawals === 0}
                >
                  Withdraw
                </Button>
              </div>
            </>
          ) : (
            <Spin size="small" />
          )}
        </Paragraph>
      </Modal>
    </WalletsContainer>
  );
};

export default withRouter(Wallets);
