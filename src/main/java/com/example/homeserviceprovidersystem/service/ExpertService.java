package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.dto.expert.*;
import com.example.homeserviceprovidersystem.dto.password.ChangePasswordRequest;
import com.example.homeserviceprovidersystem.entity.Expert;
import com.example.homeserviceprovidersystem.security.AuthenticationRequest;
import com.example.homeserviceprovidersystem.security.AuthenticationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExpertService {
    ExpertResponse save(MultipartFile multipartFile, ExpertRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Expert save(Expert expert);

    ExpertSummaryResponse expertConfirmation(ExpertRequestWithEmail request);

    String changePassword(ChangePasswordRequest request);

    Expert findById(Long id);

    Expert findByEmail(String email);

    List<ExpertSummaryResponse> findAllDisableExperts();

    List<ExpertSummaryResponse> findAll();

    List<ExpertSummaryResponse> findExpertsByDynamicSearch(ExpertSummaryRequest request);

    String verifyToken(String token);
}
