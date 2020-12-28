package com.test.m281220;

public class SocketManagerResponseDTO {
    public static final String MESSAGE_GET = "MESSAGE_GET";
    public static final String MESSAGE_SEND = "MESSAGE_SEND";
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String USER_SMS = "USER_SMS";
    public static final String USER_REGISTER = "USER_REGISTER";
    public static final String CHAT_GET = "CHAT_GET";
    public static final String USER_SEARCH = "USER_SEARCH";
    public static final String CONTACT_GET = "CONTACT_GET";
    public static final String CONTACT_GET_REQUESTS = "CONTACT_GET_REQUESTS";
    public static final String AVATAR_GET = "AVATAR_GET";
    public static final String CONTACT_SEARCH = "CONTACT_SEARCH";
    public static final String AVATAR_SET = "AVATAR_SET";
    public static final String CONTACT_DELETE = "CONTACT_DELETE";
    public static final String CONTACT_ADD = "CONTACT_ADD";
    public static final String GROUP_GET = "GROUP_GET";
    public static final String GROUP_NEW = "GROUP_NEW";
    public static final String GROUP_SEARCH = "GROUP_SEARCH";
    public static final String MESSAGE_GET_GROUP = "MESSAGE_GET_GROUP";
    public static final String FILE_GET = "FILE_GET";


    String typeResponse;
    String message;

    public SocketManagerResponseDTO(String typeResponse, String message) {
        this.typeResponse = typeResponse;
        this.message = message;
    }

    public String getTypeResponse() {
        return typeResponse;
    }

    public String getMessage() {
        return message;
    }


}
