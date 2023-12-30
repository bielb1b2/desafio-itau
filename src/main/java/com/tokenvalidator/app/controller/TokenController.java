package com.tokenvalidator.app.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokenvalidator.app.model.Token;
import com.tokenvalidator.app.services.TokenService;

@RestController
public class TokenController {

	@PostMapping(value = "/validate")
	public String validate(@RequestBody Token token) {

		TokenService tokenService = new TokenService();
		Boolean validate = tokenService.ValidateToken(token);

		return validate.toString();
	}
}
