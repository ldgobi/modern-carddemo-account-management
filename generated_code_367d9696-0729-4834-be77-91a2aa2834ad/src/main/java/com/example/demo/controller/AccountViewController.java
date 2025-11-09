package com.example.demo.controller;

import com.example.demo.dto.AccountViewResponseDto;
import com.example.demo.service.AccountViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Account View Management", description = "APIs for viewing account details")
@RequestMapping("/api/accounts")
public class AccountViewController {

    private final AccountViewService accountViewService;

    @Operation(summary = "Get account view by ID", description = "Retrieve account view with customer details by account ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of account view"),
        @ApiResponse(responseCode = "400", description = "Invalid account ID"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{accountId}/view")
    public ResponseEntity<AccountViewResponseDto> getAccountView(@PathVariable Long accountId) {
        log.info("Fetching account view for account ID: {}", accountId);
        
        if (accountId == null || accountId <= 0) {
            log.warn("Invalid account ID provided: {}", accountId);
            throw new IllegalArgumentException("Invalid account ID");
        }
        
        AccountViewResponseDto response = accountViewService.getAccountView(accountId);
        return ResponseEntity.ok(response);
    }
}
