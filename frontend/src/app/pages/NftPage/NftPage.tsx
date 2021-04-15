import React, { useEffect, useState } from "react";
import {useLocation, withRouter} from "react-router-dom";
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
  Typography, Upload,
} from "antd";
import {BankOutlined, UploadOutlined} from "@ant-design/icons/lib";
import { nftService } from "./services/NFTService";
import { backendService } from "../../core/services/BackendService";
import { walletService } from "../Wallets/services/WalletService";
const { Option } = Select;
const { Title, Paragraph } = Typography;

const emptyRequest = (result: any) => {
  setTimeout(() => {
    result.onSuccess("ok");
  }, 0);
};

const NftPage = () => {
  const [nfts, setNfts] = useState<any[]>([]);
  const [noNFts, setNoNfts] = useState<boolean>(false);

  const [newNft, setNewNft] = useState<boolean>(false);

  const [loading, setLoading] = useState<boolean>(false);

  const [userWallets, setUserWallets] = useState<any[]>([]);
  const queryService = new URLSearchParams(useLocation().search);

  useEffect(() => {
    const param = queryService.get("");
    if (param === "create") {
      setNewNft(true);
    }
  }, []);

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

    const formData = new FormData();
    formData.append('image', values.image[0].originFileObj, values.image[0].name);
    const body = {
      ...values,
      image: formData
    };

    nftService
      .createNFT(body)
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

  const uploadFile = e => {
    return e && e.fileList;
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
              name="image"
              valuePropName="fileList"
              getValueFromEvent={uploadFile}
              extra="Upload Image"
              rules={[{ required: true, message: "Please upload file." }]}
            >
              <Upload maxCount={1} listType="picture-card" name="logo" customRequest={emptyRequest}>
                <UploadOutlined />
              </Upload>
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
