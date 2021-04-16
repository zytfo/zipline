import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import {
  Card,
  Col,
  Row,
  Image,
  Button,
  Modal,
  Select,
  notification,
  Tooltip,
} from "antd";
import { marketplaceService } from "../../services/MarketplaceService";
import { backendService } from "../../../../core/services/BackendService";
import { ItemPrice, MarketplaceContainer } from "./MarketStyles";
import { walletService } from "../../../Wallets/services/WalletService";
import auth from "../../../../core/services/AuthService";
const { Meta } = Card;
const { Option } = Select;

const buyingNotification = {
  message: "Transaction Pending",
  description: "You will be notified when the purchase goes through.",
  duration: 30,
};

const buyingSuccess = {
  message: "Transaction Finished",
  description: "Transaction was successfully completed.",
  duration: 30,
};

const buyingError = {
  message: "Transaction Failed",
  description: "Something went wrong and transaction was failed.",
  duration: 30,
};

const Market = ({ positions, publicationPositions }: any) => {
  const [openPositions, setOpenPositions] = useState<any>([]);
  const [wallets, setWallets] = useState<any>([]);

  const [visible, setVisible] = useState<boolean>(false);
  const [nftToBuy, setNftToBuy] = useState<any>(null);

  const [chosenWallet, setChosenWallet] = useState<any>(null);
  const [buyingLoading, setBuyingLoading] = useState<boolean>(false);

  const metadata = auth.getMetadata();

  useEffect(() => {
    walletService
      .getWallets()
      .then((response) => {
        setWallets(response.data.data);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  }, []);

  useEffect(() => {
    if (publicationPositions) {
      setOpenPositions(publicationPositions);
    } else {
      marketplaceService
        .getOpenPositions(positions)
        .then((response) => {
          setOpenPositions(response.data.data);
        })
        .catch((error) => backendService.errorHandler(error.response));
    }
  }, []);

  const cancelBuying = () => {
    setVisible(false);
    setNftToBuy(null);
  };

  const buyNft = () => {
    setBuyingLoading(true);
    setVisible(false);
    setNftToBuy(null);

    notification.info(buyingNotification);
    marketplaceService
      .executeTrade(nftToBuy.tradeId, chosenWallet)
      .then(() => {
        const index = openPositions.findIndex((elem) => {
          return elem.tradeId === nftToBuy.tradeId;
        });
        const newArray = [...openPositions];
        newArray.splice(index, 1);
        setOpenPositions(newArray);
        setBuyingLoading(false);
        notification.success(buyingSuccess);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
        notification.error(buyingError);
      });
  };

  const closeTrade = (tradeId: number) => {
    marketplaceService
      .deleteTrade(tradeId)
      .then(() => {
        const index = openPositions.findIndex((elem) => {
          return elem.tradeId === tradeId;
        });
        const newArray = [...openPositions];
        newArray.splice(index, 1);
        setOpenPositions(newArray);
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  const returnActions = (position) => {
    if (metadata.id === position.creatorUserId) {
      return [
        <ItemPrice>
          <Tooltip
            placement="top"
            title={
              (position.weiPrice / 1000000000000000000).toFixed(20) + " BNB"
            }
          >
            {(position.weiPrice / 1000000000000000000).toFixed(4)}
            {"... "}
            {publicationPositions ? "" : "BNB"}
          </Tooltip>
        </ItemPrice>,
        <Button
          onClick={() => {
            setVisible(true);
            setNftToBuy(position);
          }}
          size="small"
          type="link"
          block
          disabled={!position.isOpen}
        >
          BUY
        </Button>,
        <Button
          onClick={() => {
            closeTrade(position.tradeId);
          }}
          size="small"
          type="link"
          block
          disabled={!position.isOpen}
        >
          CLOSE
        </Button>,
      ];
    } else
      return [
        <ItemPrice>
          <Tooltip
            placement="top"
            title={
              (position.weiPrice / 1000000000000000000).toFixed(20) + " BNB"
            }
          >
            {(position.weiPrice / 1000000000000000000).toFixed(4)}
            {"... "}
            {publicationPositions ? "" : "BNB"}
          </Tooltip>
        </ItemPrice>,
        <Button
          onClick={() => {
            setVisible(true);
            setNftToBuy(position);
          }}
          size="small"
          type="link"
          block
        >
          BUY
        </Button>,
      ];
  };

  return (
    <MarketplaceContainer onClick={(e) => e.stopPropagation()}>
      <Row gutter={[16, 16]}>
        {openPositions.map((position) => (
          <Col span={publicationPositions ? 6 : 8} key={position.tradeId}>
            <Card
              hoverable
              cover={
                <Image
                  style={{ objectFit: "cover" }}
                  height={publicationPositions ? 100 : 300}
                  alt={"name"}
                  src={position.nft.imageUrl}
                />
              }
              actions={returnActions(position)}
            >
              <Meta
                title={position.nft.name}
                description={
                  publicationPositions ? "" : position.nft.description
                }
              />
            </Card>
          </Col>
        ))}
        <Modal
          visible={visible}
          title="Buy NFT"
          onCancel={cancelBuying}
          footer={[
            <Button key="back" onClick={cancelBuying}>
              Cancel
            </Button>,
            <Button
              onClick={buyNft}
              key="submit"
              type="primary"
              disabled={!chosenWallet}
              loading={buyingLoading}
            >
              Submit
            </Button>,
          ]}
        >
          <Select
            placeholder="Select Wallet"
            size="large"
            style={{ width: "100%" }}
            value={chosenWallet}
            onChange={(value) => setChosenWallet(value)}
          >
            {wallets.map((wallet) => (
              <Option value={wallet.walletId} key={wallet.walletId}>
                {wallet.name}
              </Option>
            ))}
          </Select>
        </Modal>
      </Row>
    </MarketplaceContainer>
  );
};

export default withRouter(Market);
