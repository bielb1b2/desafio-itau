package com.tokenvalidator.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenTextFixture {

    private static final String SECRET_KEY = "Hr9kJt26DhuKvWOpXPpfVDZWb1rHYFZuyctCHRjQpf33LWqgfQ1JQ1OMog/aM/0O";

    public static String criarJwt(String seed, String name, String role) throws Exception {

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("Seed", seed)
                .claim("Name", name)
                .claim("Role", role)
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .keyID("itau-test")
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        JWSSigner signer = new MACSigner(SECRET_KEY);
        signedJWT.sign(signer);

        String jwtString = signedJWT.serialize();

        return jwtString;
    }

    public String generateNameGreaterThan256() {
        Faker faker = new Faker();
        String bigName = faker.lorem().characters(400);
        return bigName;
    }

    public static String primeGenerate(int maxLimit) {
        Random random = new Random();
        int numeroPrimo;
        do {
            numeroPrimo = random.nextInt(maxLimit) + 1;
        } while (!isPrime(numeroPrimo));
        return Integer.toString(numeroPrimo);
    }

    public static String notPrimeGenerate(int maxLimit) {
        Random random = new Random();
        int numeroPrimo;
        do {
            numeroPrimo = random.nextInt(maxLimit) + 1;
        } while (isPrime(numeroPrimo));
        return Integer.toString(numeroPrimo);
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public List<String> validNameToken() throws Exception {
        Faker faker = new Faker();
        List<String> tokenList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String name = faker.name().fullName();
            String seed = primeGenerate(9999);
            String role = "Admin";
            String tokenString = criarJwt(seed, name, role);
            tokenList.add(tokenString);
        }

        return tokenList;
    }

    public List<String> invalidNameToken() throws Exception {
        Faker faker = new Faker();
        List<String> tokenList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String name = faker.name().fullName() + "5";
            String seed = primeGenerate(9999);
            String role = "Admin";
            String tokenString = criarJwt(seed, name, role);
            tokenList.add(tokenString);
        }

        return tokenList;
    }

    public List<String> invalidRoleToken() throws Exception {
        Faker faker = new Faker();
        List<String> tokenList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String name = faker.name().fullName();
            String seed = primeGenerate(9999);
            String role = "No Role";
            String tokenString = criarJwt(seed, name, role);
            tokenList.add(tokenString);
        }

        return tokenList;
    }

    public List<String> invalidSeed() throws Exception {
        Faker faker = new Faker();
        List<String> tokenList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String name = faker.name().fullName();
            String seed = notPrimeGenerate(9999);
            String role = "Admin";
            String tokenString = criarJwt(seed, name, role);
            tokenList.add(tokenString);
        }

        return tokenList;
    }
}
