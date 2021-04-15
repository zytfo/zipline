import { backendService } from "../../../core/services/BackendService";

class PublicationsService {
  /**
   * Fetch publications
   * @param pageNumber
   * @param tickersToSearch
   * @param creator
   */
  getPublications(
    pageNumber: number,
    tickersToSearch: string[],
    creator: string
  ) {
    const params = {
      pageNumber,
      pageSize: 10,
      sortDirection: "DESC",
      tickers: tickersToSearch.join(", "),
      createdBy: creator,
    };
    return backendService.get(backendService.API + `publication/`, {
      params,
    });
  }

  /**
   * Get full full information about publication
   * @param id
   */
  getPublicationById(id: number) {
    return backendService.get(backendService.API + `publication/${id}`, {});
  }

  /**
   * Like publication
   * @param id
   */
  likePublication(id: number) {
    return backendService.post(
      backendService.API + `like/PUBLICATION/${id}`,
      {},
      {}
    );
  }

  /**
   * Show likes of publication
   * @param id
   */
  showLikes(id: number) {
    return backendService.get(
      backendService.API + `like/PUBLICATION/${id}`,
      {}
    );
  }

  /**
   * Publish new post
   * @param body
   */
  newPublication(body: any) {
    return backendService.post(
      backendService.API + `publication/create`,
      body,
      {}
    );
  }

  /**
   * Delete post
   * @param publicationId
   */
  deletePublication(publicationId: number) {
    return backendService.delete(
      backendService.API + `publication/delete/${publicationId}`,
      {}
    );
  }
}

export const publicationsService = new PublicationsService();
