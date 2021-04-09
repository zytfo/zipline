export interface CommentItem {
  commentId: number;
  author: string;
  created: Date;
  likesCount: number;
  message: string;
  postId: number;
  postType: string;
  selfLiked: boolean;
  tickers: string[];
  updated: Date | null;
}
