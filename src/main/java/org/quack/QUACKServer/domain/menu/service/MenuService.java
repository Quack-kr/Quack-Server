package org.quack.QUACKServer.domain.menu.service;


import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.menu.repository.MenuRepository;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final PhotosS3Repository photosS3Repository;

    public List<GetReviewMenusResponse> getMenusForReview(Long restaurantId) {
        List<GetReviewMenusResponse> reviewMenus = menuRepository.getReviewMenus(restaurantId);

        List<GetReviewMenusResponse> results = new ArrayList<>();

        for (GetReviewMenusResponse reviewMenu : reviewMenus) {

            String imagePath = null;
            if (reviewMenu.menuImagePath() != null) {
                imagePath = photosS3Repository.convertPath(
                        PhotosFileDto.builder().keyName(reviewMenu.menuImagePath()).build());

            }

            GetReviewMenusResponse response = GetReviewMenusResponse.of(
                    reviewMenu.menuName(),
                    reviewMenu.menuCategory(),
                    reviewMenu.menuPrice(),
                    imagePath
            );

            results.add(response);
        }

        return results;
    }
}
