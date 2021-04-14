import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import {
  ActionSet,
  NewNFTContainer,
  NFTContainer,
  NFTItem,
} from "./NftPageStyles";
import {
  Select,
  Button,
  Input,
  Result,
  Skeleton,
  Form,
  Image,
  Typography,
} from "antd";
import { BankOutlined } from "@ant-design/icons/lib";
import { nftService } from "./services/NFTService";
import { backendService } from "../../core/services/BackendService";
import { walletService } from "../Wallets/services/WalletService";
const { Option } = Select;
const { Title, Paragraph } = Typography;

const NftPage = () => {
  const [nfts, setNfts] = useState<any[]>([]);
  const [noNFts, setNoNfts] = useState<boolean>(false);

  const [newNft, setNewNft] = useState<boolean>(false);

  const [loading, setLoading] = useState<boolean>(false);

  const [userWallets, setUserWallets] = useState<any[]>([]);

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

  useEffect(() => {
    walletService
      .getWallets()
      .then((response) => {
        setUserWallets(response.data.data);
      })
      .catch((error) => {
        backendService.errorHandler(error.response);
      });
  }, []);

  const createNewNFT = (values) => {
    setLoading(true);
    nftService
      .createNFT(values)
      .then((response) => {
        setLoading(false);
        setNfts((oldContent) => [response.data.data, ...oldContent]);
        setNewNft(false);
        if (noNFts) {
          setNoNfts(false);
        }
      })
      .catch((error) => backendService.errorHandler(error.response));
  };

  return (
    <NFTContainer>
      {nfts.length === 0 && !noNFts && (
        <Skeleton paragraph={{ rows: 3 }} active />
      )}
      {noNFts && (
        <Result
          icon={<BankOutlined />}
          title="Seems like you don't have NFTs"
          subTitle="Create it now!"
          extra={[
            <Button
              size="large"
              type="primary"
              key="new-nft"
              onClick={() => setNewNft(true)}
            >
              Create NFT
            </Button>,
          ]}
        />
      )}
      {nfts.length !== 0 && (
        <ActionSet>
          <Button
            size="large"
            type="primary"
            key="new-wallet"
            onClick={() => {
              setNewNft(true);
            }}
          >
            Create NFT
          </Button>
        </ActionSet>
      )}
      {newNft && (
        <NewNFTContainer>
          <Form onFinish={createNewNFT}>
            <Form.Item
              name="name"
              rules={[
                { required: true, message: "Please input your NFT name." },
              ]}
            >
              <Input size="large" placeholder="NFT Name" />
            </Form.Item>
            <Form.Item
              name="description"
              rules={[{ required: true, message: "Please input description." }]}
            >
              <Input size="large" placeholder="Description" />
            </Form.Item>
            <Form.Item
              name="imageUrl"
              rules={[{ required: true, message: "Please input image url." }]}
            >
              <Input size="large" placeholder="File URL" />
            </Form.Item>
            <Form.Item name="externalLink">
              <Input size="large" placeholder="External Link" />
            </Form.Item>
            <Form.Item
              name="walletId"
              rules={[
                { required: true, message: "Please select your wallet." },
              ]}
            >
              <Select
                placeholder="Select Wallet"
                size="large"
                style={{ width: "100%" }}
              >
                {userWallets.map((wallet) => (
                  <Option value={wallet.walletId} key={wallet.walletId}>
                    {wallet.name}
                  </Option>
                ))}
              </Select>
            </Form.Item>
            <Form.Item>
              <Button
                size="large"
                type="primary"
                htmlType="submit"
                loading={loading}
              >
                Create NFT
              </Button>
            </Form.Item>
          </Form>
        </NewNFTContainer>
      )}
      {nfts.map((nft) => (
        <NFTItem key={nft.nftId}>
          <Title level={5}>{nft.name}</Title>
          <Paragraph>{nft.description}</Paragraph>
          {nft.imageUrl && <Image alt={nft.name} src={nft.imageUrl} />}
        </NFTItem>
      ))}
    </NFTContainer>
  );
};

export default withRouter(NftPage);
