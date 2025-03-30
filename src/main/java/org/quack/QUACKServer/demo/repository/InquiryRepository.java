package org.quack.QUACKServer.demo.repository;

import org.quack.QUACKServer.demo.domain.Inquiry;
import org.quack.QUACKServer.demo.dto.inquiry.InquiriesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT new org.quack.QUACKServer.demo.dto.inquiry.InquiriesResponse(" +
            "i.inquiryId, i.title, i.content, i.createdDate, " +
            "ir.content, ir.createdDate, i.replyStatus" +
            ") " +
            "FROM Inquiry i " +
            "LEFT JOIN i.inquiryReply ir " +
            "WHERE i.user.userId = :userId " +
            "ORDER BY i.createdDate DESC")
    Page<InquiriesResponse> findInquiriesByUserId(Long userId, Pageable pageable);

    void deleteByUser_UserIdAndInquiryId(Long userId, Long inquiryId);

}
