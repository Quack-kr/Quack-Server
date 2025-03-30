package org.quack.QUACKServer.demo.controller;

import static org.springframework.http.HttpStatus.*;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.dto.inquiry.InquiriesResponse;
import org.quack.QUACKServer.demo.dto.inquiry.InquiryRegisterRequest;
import org.quack.QUACKServer.demo.service.InquiryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/inquiries")
    public ResponseEntity<Page<InquiriesResponse>> getMyInquiries(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long userId = Long.valueOf(principal.getName());

        return ResponseEntity
                .status(OK)
                .body(inquiryService.getMyInquiries(userId, page, size));
    }

    @PostMapping("/inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(Principal principal, @PathVariable Long inquiryId) {
        Long userId = Long.valueOf(principal.getName());
        inquiryService.deleteInquiry(userId, inquiryId);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/inquiries")
    public ResponseEntity<Void> registerInquiry(Principal principal,
                                                @Valid @RequestBody InquiryRegisterRequest request) {
        Long userId = Long.valueOf(principal.getName());
        inquiryService.registerInquiry(userId, request);

        return ResponseEntity.status(CREATED).build();
    }



}
