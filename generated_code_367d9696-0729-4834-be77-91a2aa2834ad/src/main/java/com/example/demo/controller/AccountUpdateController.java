package com.example.demo.controller;

import com.example.demo.dto.UpdateAccountUpdateRequestDto;
import com.example.demo.service.AccountUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Account Update Management", description = "APIs for updating account and customer information")
@RequestMapping("/api/accounts")
public class AccountUpdateController {

    private final AccountUpdateService accountUpdateService;

    @Operation(summary = "Update account and customer data", description = "Update account and customer information by account ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account and customer updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{accountId}/update")
    public ResponseEntity<String> updateAccountAndCustomer(
            @PathVariable Long accountId,
            @Valid @RequestBody UpdateAccountUpdateRequestDto request) {
        log.info("Updating account and customer data for account ID: {}", accountId);
        String response = accountUpdateService.updateAccount(accountId, request);
        return ResponseEntity.ok(response);
    }
}
