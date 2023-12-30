package com.tokenvalidator.app.services;

import com.tokenvalidator.app.model.Token;

public interface ITokenService {
    Boolean ValidateToken(Token token);
}
