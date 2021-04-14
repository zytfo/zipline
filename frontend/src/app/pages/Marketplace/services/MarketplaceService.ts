import { backendService } from "../../../core/services/BackendService";

class MarketplaceService {
  magicNumber = 1000000000000000000;

  getOpenPositions(destination: string) {
    return backendService.get(backendService.API + `market/trades/${destination}`, {});
  }

  newOrder(nftId: number, price: number) {
    const newPrice = price * this.magicNumber;
    return backendService.post(
      backendService.API + `market/trades/open/${nftId}/${newPrice.toFixed()}`,
      {},
      { }
    );
  }

  executeTrade(tradeId: number, walletId: number) {
    return backendService.post(
      backendService.API + `market/trades/${tradeId}/execute/${walletId}`,
      {},
      { }
    );
  }

  deleteTrade(tradeId: number) {
    return backendService.delete(
      backendService.API + `market/trades/${tradeId}/cancel`,
      {}
    );
  }
}

export const marketplaceService = new MarketplaceService();
