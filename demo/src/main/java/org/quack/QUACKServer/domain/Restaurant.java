package org.quack.QUACKServer.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String restaurantName;

    private String representativeImage; // 대표이미지

    @Column(nullable = false)
    private String address; // 주소

    private String category; // 식당 카테고리 -> 다중 값 가능 ex) 일식, 초

    private String simpleDescription; // 식당 한줄 소개

    private String detailDescription; // 식당 상세 소개

    private String latitude; // 위도

    private String longitude; // 경도

    private String registrationNumber; // 사업자 등록번호

    private String ownerName; // 대표자

    private String representativePhone; // 대표자 번호

    private String registrationImage; // 사업자 등록증 초본

    private LocalDateTime registrationDate;


    // == 연관관계 == //
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RestaurantImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RestaurantOperating> operatingInfo = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<RestaurantFeature> features = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Menu> menus = new ArrayList<>();


    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Review> reviews = new ArrayList<>();


    //TODO : 컬럼 정리 (사업자 관련 정보를 분리할 지, 각 컬럼 별로 제약조건을 뭘 달지), 생성자 및 연관관계 편의 메서드 구현
}
