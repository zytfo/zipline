import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import {ItemPrice, MarketplaceContainer} from "./MarketplaceStyles";
import { marketplaceService } from "./services/MarketplaceService";
import { backendService } from "../../core/services/BackendService";
import {Card, Col, Row, Image, Button} from "antd";
const { Meta } = Card;

const Marketplace = () => {
  const [openPositions, setOpenPositions] = useState<any>([]);

  useEffect(() => {
    marketplaceService
      .getOpenPositions()
      .then((response) => {
        setOpenPositions([
          ...response.data.data,
          ...response.data.data,
          ...response.data.data,
          ...response.data.data,
          ...response.data.data,
        ]);
      })
      .catch((error) => backendService.errorHandler(error.response));
  }, []);

  return (
    <MarketplaceContainer>
      <Row gutter={[16, 16]}>
        {openPositions.map((position, i) => (
          <Col span={8} key={i}>
            <Card
              hoverable
              cover={
                <Image
                  alt={"name"}
                  src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
                />
              }
              actions={[
                <ItemPrice>1000 BNB</ItemPrice>,
                <Button size="small" type="link" block>BUY</Button>,
              ]}
            >
              <Meta
                title="НФТ Девушки"
                description="продам"
              />
            </Card>
          </Col>
        ))}
      </Row>
    </MarketplaceContainer>
  );
};

export default withRouter(Marketplace);
