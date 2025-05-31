package org.quack.QUACKServer.domain.menu.service;


import static org.quack.QUACKServer.domain.menu.domain.QMenu.menu;
import static org.quack.QUACKServer.domain.menu.domain.QMenuCategory.menuCategory;
import static org.quack.QUACKServer.domain.photos.domain.QPhotos.photos;

import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.menu.repository.MenuRepository;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.springframework.stereotype.Service;

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
            if (reviewMenu.getMenuImagePath() != null) {
                imagePath = photosS3Repository.convertPath(
                        PhotosFileDto.builder().keyName(reviewMenu.menuImagePath()).build());

            }

            GetReviewMenusResponse response = GetReviewMenusResponse.of(
                    reviewMenu.getMenuName(),
                    reviewMenu.getMenuCategory(),
                    reviewMenu.getMenuPrice(),
                    imagePath
            );

            results.add(response);
        }

        return results;
    }
}
