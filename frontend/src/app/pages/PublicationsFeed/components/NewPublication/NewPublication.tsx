import React, {FunctionComponent, useEffect, useState} from "react";
import { withRouter } from "react-router";
import { NewPublicationContainer, PublishButton } from "./NewPublicationStyles";
import {Image, Input, Select} from "antd";
import {marketplaceService} from "../../../Marketplace/services/MarketplaceService";
import {backendService} from "../../../../core/services/BackendService";
const { TextArea } = Input;
const { Option } = Select;

const NewPublication: FunctionComponent<any> = ({ onPublish }) => {
  const [newPubFocused, setNewPubFocused] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const [chosenTrades, setChosenTrades] = useState<any[]>([]);

  const [trades, setTrades] = useState<any[]>([]);

  useEffect(() => {
    marketplaceService
      .getOpenPositions("all_open")
      .then((response) => {
        setTrades(response.data.data);
      })
      .catch((error) => backendService.errorHandler(error.response));
  }, []);

  return (
    <NewPublicationContainer>
      <TextArea
        size="large"
        rows={newPubFocused ? 4 : 1}
        placeholder={"Do you want to share something?"}
        onFocus={() => setNewPubFocused(true)}
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
      />

      {newPubFocused && (
        <>
          <Select
            showSearch
            mode="multiple"
            placeholder="Select NFTs"
            size="large"
            style={{ width: "100%", marginTop: "12px"}}
            value={chosenTrades}
            onChange={(value) => setChosenTrades(value)}
            filterOption={(input, option) => {
              const index = trades.findIndex((elem) => {
                return elem.tradeId === option?.value;
              });
              return trades[index].nft.name.toLowerCase().indexOf(input.toLowerCase()) >= 0;
            }}
          >
            {trades.map((trade) => (
              <Option value={trade.tradeId} key={trade.tradeId} >
                <div style={{display: "flex"}}>
                  <Image
                    height={50}
                    width={50}
                    alt={trade.nft.name}
                    src={trade.nft.imageUrl}
                    style={{ objectFit: "cover" }}
                  />
                  <span style={{marginLeft: "15px", fontWeight: "bold"}}>
                    {trade.nft.name}
                  </span>
                </div>
              </Option>
            ))}
          </Select>
          <PublishButton
            type="primary"
            size="large"
            onClick={() => {
              onPublish(inputValue, chosenTrades);
              setNewPubFocused(false);
              setInputValue("");
            }}
          >
            Publish
          </PublishButton>
        </>
      )}
    </NewPublicationContainer>
  );
};

export default withRouter(NewPublication);
