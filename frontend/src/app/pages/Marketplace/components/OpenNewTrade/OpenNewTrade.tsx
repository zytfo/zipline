import React, { useEffect, useState } from "react";
import { Link, withRouter } from "react-router-dom";
import { nftService } from "../../../NftPage/services/NFTService";
import { backendService } from "../../../../core/services/BackendService";
import { NewOrderContainer, NFTInfo, NFTItem } from "./OpenNewOrderStyles";
import {Button, Form, Image, Input, notification, Result, Typography} from "antd";
import { BankOutlined } from "@ant-design/icons/lib";
import { marketplaceService } from "../../services/MarketplaceService";
const { Title, Paragraph } = Typography;

const nftNotification = {
  message: "Your NFT is being placed",
  description: "You will be notified when the placing goes through.",
  duration: 30,
};

const nftNotificationSuccess = {
  message: "Your NFT was placed",
  description: "Placing was successfully completed.",
  duration: 30,
};

const nftNotificationError = {
  message: "Your NFT was NOT placed",
  description: "Something went wrong and placing was failed.",
  duration: 30,
};

const OpenNewTrade = () => {
  const [nfts, setNfts] = useState<any[]>([]);
  const [noNFts, setNoNfts] = useState<boolean>(false);

  useEffect(() => {
    nftService
      .getNFT()
      .then((response) => {
        setNfts(response.data.data);
        if (response.data.data.length === 0) {
          setNoNfts(true);
        }
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  }, []);

  const placeOrder = (id: number, price: string) => {
    notification.info(nftNotification);
    marketplaceService
      .newOrder(id, parseFloat(price))
      .then(() => {
        const index = nfts?.findIndex((elem) => {
          return elem.nftId === id;
        });
        const newArray = [...nfts];
        newArray.splice(index, 1);
        setNfts(newArray);
        notification.success(nftNotificationSuccess);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
        notification.error(nftNotificationError);
      });
  };

  return (
    <NewOrderContainer>
      {nfts.map((nft) => (
        <NFTItem key={nft.nftId}>
          {nft.imageUrl && (
            <Image
              height={100}
              width={100}
              alt={nft.name}
              src={nft.imageUrl}
              style={{ objectFit: "cover" }}
            />
          )}
          <NFTInfo>
            <div>
              <Title level={5}>{nft.name}</Title>
              <Paragraph>{nft.description}</Paragraph>
            </div>
            <Form
              layout="inline"
              onFinish={(values) => placeOrder(nft.nftId, values.price)}
            >
              <Form.Item
                name="price"
                rules={[{ required: true, message: "Please enter the Price" }]}
              >
                <Input suffix="BNB" placeholder="Price" />
              </Form.Item>
              <Form.Item>
                <Button type="primary" htmlType="submit">
                  Place
                </Button>
              </Form.Item>
            </Form>
          </NFTInfo>
        </NFTItem>
      ))}
      {noNFts && (
        <Result
          icon={<BankOutlined />}
          title="Seems like you don't have NFTs"
          subTitle="Create it now!"
          extra={[
            <Button size="large" type="primary" key="new-nft">
              <Link to={`/nft?=create`}>Create NFT </Link>
            </Button>,
          ]}
        />
      )}
    </NewOrderContainer>
  );
};

export default withRouter(OpenNewTrade);
