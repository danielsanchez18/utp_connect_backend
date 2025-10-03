package com.nova.team.utp_connect_backend.services;

import com.nova.team.utp_connect_backend.enities.User;

public interface AuthService {

    String login(String email, String password);

    User currentUser(String token);

}