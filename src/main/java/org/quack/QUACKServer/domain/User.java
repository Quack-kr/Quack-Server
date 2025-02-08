package org.quack.QUACKServer.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import org.quack.QUACKServer.domain.common.BaseEntity;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;

/**
 * providerType: (카카오, 네이버, 애플 등) 소셜 로그인 타입
 * roleType: (일반유저, 사장님) 권한/역할
 */
@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 생성자 선언 후, @Builder 붙이기
// 아래 애노테이션은 임시로 작성
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // PK

    private String providerType; // Enum type으로 변경
    private String email;
    private String nickname;
    private String roleType; // Enum type으로 변경
    private String profileImage;


    // == 연관관계 매핑 예시 == //
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Inquiry> inquiries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SavedRestaurant> savedRestaurants = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Review> reviews = new ArrayList<>();

    public void updateUserProfile (UpdateUserInfoRequest updateUserInfoRequest) {
        if (updateUserInfoRequest.getNickname() != null) {
            this.nickname = updateUserInfoRequest.getNickname();
        }

        if (updateUserInfoRequest.getProfileImage() != null) {
            this.profileImage = updateUserInfoRequest.getProfileImage();
        }
    }

}
