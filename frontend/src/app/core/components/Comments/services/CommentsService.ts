import { backendService } from "../../../services/BackendService";

class CommentsService {
  getComments(pageNumber: number, postType: string, postId: number, sort: string) {
    const params = {
      pageNumber,
      pageSize: 10,
      postId,
      postType,
      sortDirection: sort,
    };
    return backendService.get(backendService.API + `comment/`, {
      params,
    });
  }

  likeComment(id: number) {
    return backendService.post(
      backendService.API + `like/COMMENT/${id}`,
      {},
      {}
    );
  }

  postComment(message: string, postId: number, postType: string) {
    const params = {
      message,
    };

    return backendService.post(
      backendService.API + `comment/create/${postType}/${postId}`,
      {},
      { params }
    );
  }
}

export const commentsService = new CommentsService();
