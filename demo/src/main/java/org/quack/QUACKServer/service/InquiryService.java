package org.quack.QUACKServer.service;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.Inquiry;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.dto.inquiry.InquiriesResponse;
import org.quack.QUACKServer.dto.inquiry.InquiryRegisterRequest;
import org.quack.QUACKServer.repository.InquiryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {

    private final UserService userService;
    private final InquiryRepository inquiryRepository;

    public Page<InquiriesResponse> getMyInquiries(Long userId, int page, int size) {
        User user = userService.getUserOrException(userId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<InquiriesResponse> inquiries = inquiryRepository.findInquiriesByUserId(user.getUserId(), pageable);

        return inquiries;
    }

    public void deleteInquiry(Long userId, Long inquiryId) {
        User user = userService.getUserOrException(userId);
        inquiryRepository.deleteByUser_UserIdAndInquiryId(user.getUserId(), inquiryId);
    }


    public void registerInquiry(Long userId, InquiryRegisterRequest request) {
        User user = userService.getUserOrException(userId);
        inquiryRepository.save(Inquiry.createInquiry(user, request.title(), request.content()));
    }
}
