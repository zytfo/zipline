import { backendService } from "../../../core/services/BackendService";

class NFTService {
  getNFT() {
    return backendService.get(backendService.API + `nfts/all`, {});
  }

  createNFT(body: any) {
    const headers = { "Content-Type": `multipart/form-data; boundary=${body.image._boundary}` };
    return backendService.post(
      backendService.API + `nfts/create?description=${body.description}&name=${body.name}&walletId=${body.walletId}`,
      body.image,
      { headers }
    );
  }
}

export const nftService = new NFTService();
