package com.daar.adapter.in.rest.response;

import java.util.List;

public class UsersListResponse {

    private List<UserResponse> users;

    public UsersListResponse(List<UserResponse> users) {
        this.users = users;
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
}
