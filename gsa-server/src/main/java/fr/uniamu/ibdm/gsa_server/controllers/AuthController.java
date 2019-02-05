package fr.uniamu.ibdm.gsa_server.controllers;

import fr.uniamu.ibdm.gsa_server.requests.JSONResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public JSONResponse<String> login() {
        JSONResponse<String> response = new JSONResponse<>(RequestStatus.SUCCESS, "coucou");

        return response;
    }

}
