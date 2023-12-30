package com.tokenvalidator.app.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.nimbusds.jwt.*;
import com.tokenvalidator.app.model.Token;

@Service
public class TokenService implements ITokenService {

    private static final Set<String> ROLES_WITH_PERMISSION = new HashSet<String>(
            Arrays.asList("Admin", "Member", "External"));

    @Override
    public Boolean ValidateToken(Token token) {

        try {
            // 1. Deve ser um JWT válido
            JWT jwtString = JWTParser.parse(token.getValue());
            JWTClaimsSet jwtClaimsSet = jwtString.getJWTClaimsSet();

            // 2. Deve conter apenas 3 claims (Name, Role e Seed)
            if (jwtClaimsSet.getClaims().size() != 3 ||
                    jwtClaimsSet.getClaim("Role") == null ||
                    jwtClaimsSet.getClaim("Seed") == null ||
                    jwtClaimsSet.getClaim("Name") == null) {
                return false;
            }

            // 3. A claim Name não pode ter carácter de números
            String name = jwtClaimsSet.getClaim("Name").toString();
            if (name.matches(".*\\d.*"))
                return false;

            // 4. A claim Role deve conter apenas 1 dos três valores (Admin, Member e
            // External)
            String role = jwtClaimsSet.getClaim("Role").toString();
            if (!ROLES_WITH_PERMISSION.contains(role))
                return false;

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        // 5. A claim Seed deve ser um número primo.
        // 6. O tamanho máximo da claim Name é de 256 caracteres.

        return true;
    }

}
