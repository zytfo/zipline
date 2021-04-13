import { backendService } from "../../../core/services/BackendService";

class MarketplaceService {
  getOpenPositions() {
    return backendService.get(backendService.API + `market/trades/all_open`, {});
  }

  newWallet(content: string) {
    const params = {
      walletName: content,
    };
    return backendService.post(
      backendService.API + `market/trades/all_open`,
      {} ,
      { params }
    );
  }
}

export const marketplaceService = new MarketplaceService();
