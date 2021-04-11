import { backendService } from "../../../core/services/BackendService";

class WalletService {
  getWallets() {
    return backendService.get(backendService.API + `wallets/all`, {});
  }

  newWallet(content: string) {
    const params = {
      message: content,
    };
    return backendService.post(
      backendService.API + `wallets/create`,
      {} ,
      { params }
    );
  }

  deleteWallet(id: number) {
    return backendService.delete(
      backendService.API + `wallets/${id}`,
      {}
    );
  }

  exportWallet(id: number) {
    return backendService.post(
      backendService.API + `wallets/export/${id}`,
      {},
      {}
    );
  }

  importWallet(data) {
    return backendService.post(
      backendService.API + `wallets/import`,
      data,
      {}
    );
  }
}

export const walletService = new WalletService();
