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
            // 6. O tamanho máximo da claim Name é de 256 caracteres.
            String name = jwtClaimsSet.getClaim("Name").toString();
            if (name.matches(".*\\d.*") || name.length() > 256)
                return false;

            // 4. A claim Role deve conter apenas 1 dos três valores (Admin, Member e
            // External)
            String role = jwtClaimsSet.getClaim("Role").toString();
            if (!ROLES_WITH_PERMISSION.contains(role))
                return false;

            // 5. A claim Seed deve ser um número primo.
            int seed = Integer.parseInt(jwtClaimsSet.getClaim("Seed").toString());
            if (isPrime(seed) == false)
                return false;

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private Boolean isPrime(int numberToCheck) {
        if (numberToCheck <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(numberToCheck); i++) {
            if (numberToCheck % i == 0) {
                return false;
            }
        }
        return true;
    }

}
