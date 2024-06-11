package com.rakbank.notificationms.controller;

import com.rakbank.notificationms.model.CustomResponse;
import com.rakbank.notificationms.model.NotificationRequest;
import com.rakbank.notificationms.model.NotificationResponse;
import com.rakbank.notificationms.service.NotificationService;
import com.rakbank.notificationms.utils.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @Operation(summary = "Add or create Notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification created Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @PostMapping("/v1/notification")
    public ResponseEntity<CustomResponse<NotificationResponse>> createNotification(@RequestBody @Valid NotificationRequest notificationRequest, HttpServletRequest request) {
        CustomResponse<NotificationResponse> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS,
                notificationService.createNotification(notificationRequest), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get all Notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications fetched Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid filed value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
    })
    @GetMapping("/v1/notifications")
    public ResponseEntity<CustomResponse<List<NotificationResponse>>> getAllNotifications(HttpServletRequest request) {
        CustomResponse<List<NotificationResponse>> response = new CustomResponse<>(200, Constant.REQUEST_SUCCESS,
                notificationService.getAllNotifications(), request.getServletPath());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
