export interface NewsObject {
  content: string;
  created: string;
  description: string;
  imageUrl: string;
  likesCount: number
  newsId: number
  source: string;
  tags: string[];
  title: string;
  selfLiked: boolean;
}
