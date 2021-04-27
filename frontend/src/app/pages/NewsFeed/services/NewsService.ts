import { backendService } from "../../../core/services/BackendService";

class NewsService {
  getNews(pageNumber: number, tagsToSearch: string[], content: string) {
    const params = {
      pageNumber,
      pageSize: 10,
      sortDirection: "DESC",
      tags: tagsToSearch.join(", "),
      content: content,
    };
    return backendService.get(backendService.API + `news/`, {
      params,
    });
  }

  getNewsById(id: number) {
    return backendService.get(backendService.API + `news/${id}`, {});
  }

  likeNews(id: number) {
    return backendService.post(backendService.API + `like/NEWS/${id}`, {}, {});
  }
}

export const newsService = new NewsService();
