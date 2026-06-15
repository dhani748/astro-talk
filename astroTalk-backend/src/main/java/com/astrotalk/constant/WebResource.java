package com.astrotalk.constant;

import lombok.experimental.UtilityClass;

/**
 * Centralizes all API endpoint constants and whitelisted paths used across the application.
 * Provides a single source of truth for URL patterns used in controllers and security configuration.
 */
@UtilityClass
public class WebResource {

    public static final String BASE_URL = "/api";
    public static final String AUTH = BASE_URL + "/auth";
    public static final String USERS = BASE_URL + "/users";
    public static final String ASTROLOGERS = BASE_URL + "/astrologers";
    public static final String CONSULTATIONS = BASE_URL + "/consultations";
    public static final String CHAT = BASE_URL + "/chat";
    public static final String PAYMENTS = BASE_URL + "/payments";
    public static final String WALLET = BASE_URL + "/wallet";
    public static final String REVIEWS = BASE_URL + "/reviews";
    public static final String NOTIFICATIONS = BASE_URL + "/notifications";
    public static final String ADMIN = BASE_URL + "/admin";
    public static final String WS_ENDPOINT = "/ws";

    public static final String ID_PATH = "/{id}";
    public static final String ME = "/me";
    public static final String PROFILE = "/profile";
    public static final String STATUS_TOGGLE = "/status/toggle";
    public static final String TOP = "/top";
    public static final String AVAILABILITY = "/{id}/availability";
    public static final String START = "/start";
    public static final String END = "/end/{id}";
    public static final String ACTIVE = "/active";
    public static final String HISTORY = "/history";
    public static final String MESSAGES = "/{consultationId}/messages";
    public static final String READ = "/{consultationId}/read";
    public static final String UNREAD_COUNT = "/{consultationId}/unread-count";
    public static final String CREATE_ORDER = "/create-order";
    public static final String VERIFY = "/verify";
    public static final String BALANCE = "/balance";
    public static final String TRANSACTIONS = "/transactions";
    public static final String ADD = "/add";
    public static final String DASHBOARD = "/dashboard";
    public static final String PENDING = "/pending";
    public static final String REVENUE = "/revenue";
    public static final String ADJUST = "/wallet/adjust";
    public static final String VERIFY_ASTROLOGER = "/astrologers/{id}/verify";
    public static final String REJECT_ASTROLOGER = "/astrologers/{id}/reject";
    public static final String USERS_ADMIN = "/users";
    public static final String ASTROLOGERS_ADMIN = "/astrologers";
    public static final String REVIEWS_ADMIN = "/reviews/{id}";

    public static final String REGISTER = "/register";
    public static final String REGISTER_ASTROLOGER = "/register/astrologer";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT = "/logout";
    public static final String FORGET = "/forget";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String USER_LOCK = "/user-lock/{userId}";
    public static final String GOOGLE = "/google";
    public static final String ASTROLOGER_DETAIL = "/{id}";
    public static final String ASTRologer_REVIEWS = "/astrologer/{id}";

    public static final String[] AUTH_WHITELIST = {
            AUTH + "/**",
            "/swagger-ui/**", "/swagger-ui.html",
            "/api-docs/**", "/v3/api-docs/**",
            WS_ENDPOINT + "/**",
            ASTROLOGERS + TOP,
            ASTROLOGERS + ASTROLOGER_DETAIL,
    };
}
