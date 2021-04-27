export interface PublicationObject {
  content: string;
  created: Date;
  createdBy: string;
  publicationId: number;
  selfLiked: boolean;
  tickers: string[];
  updated: Date | null;
  likesCount: number;
  tradeIds?: any;
  marketTradeDTOs?: any;
}
