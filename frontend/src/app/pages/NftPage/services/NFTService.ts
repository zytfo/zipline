import { backendService } from "../../../core/services/BackendService";

class NFTService {
  getNFT() {
    return backendService.get(backendService.API + `nfts/all`, {});
  }

  createNFT(body: any) {
    return backendService.post(backendService.API + `nfts/create`, body, {});
  }
}

export const nftService = new NFTService();
