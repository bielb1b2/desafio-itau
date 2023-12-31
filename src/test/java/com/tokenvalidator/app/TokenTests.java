package com.tokenvalidator.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import com.tokenvalidator.app.model.Token;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tokenvalidator.app.services.TokenService;

@SpringBootTest
class TokenTests {

	private TokenService _tokenService;

	@Autowired
	public TokenTests(TokenService tokenService) {
		this._tokenService = tokenService;
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "AAAA", "ey", "1.2.3", "__" })
	void TokenTestInvalidStringOnService(String input) throws Exception {
		// AAA
		// Arrange
		Token testToken = new Token(input);

		// Act
		Boolean action = _tokenService.ValidateToken(testToken);

		// Arrange
		assertThat(action).isFalse();
	}

	static Stream<Arguments> provideDataValidName() throws Exception {
		TokenTextFixture tokenTextFixture = new TokenTextFixture();
		List<String> validName = tokenTextFixture.validNameToken();
		return Stream.iterate(0, i -> i + 1)
				.limit(validName.size())
				.map(i -> Arguments.of(validName.get(i), i));
	}

	@ParameterizedTest
	@MethodSource("provideDataValidName")
	void TokenTestValidName(String input) throws Exception {
		// AAA
		// Arrange
		Token testToken = new Token(input);

		// Act
		Boolean action = _tokenService.ValidateToken(testToken);

		// Arrange
		assertThat(action).isTrue();
	}

	static Stream<Arguments> provideDataInvalidName() throws Exception {
		TokenTextFixture tokenTextFixture = new TokenTextFixture();
		List<String> invalidNames = tokenTextFixture.invalidNameToken();
		return Stream.iterate(0, i -> i + 1)
				.limit(invalidNames.size())
				.map(i -> Arguments.of(invalidNames.get(i), i));
	}

	@ParameterizedTest
	@MethodSource("provideDataInvalidName")
	void TokenTestInvalidName(String input) throws Exception {
		// AAA
		// Arrange

		Token testToken = new Token(input);

		// Act
		Boolean action = _tokenService.ValidateToken(testToken);

		// Arrange
		assertThat(action).isFalse();
	}

	@Test
	void TokenTestNameGreaterThan256() throws Exception {
		// AAA
		// Arrange
		TokenTextFixture tokenTextFixture = new TokenTextFixture();
		String invalidName = tokenTextFixture.generateNameGreaterThan256();
		Token testToken = new Token(invalidName);

		// Act
		Boolean action = _tokenService.ValidateToken(testToken);

		// Arrange
		assertThat(action).isFalse();
	}

	static Stream<Arguments> provideDataInvalidRole() throws Exception {
		TokenTextFixture tokenTextFixture = new TokenTextFixture();
		List<String> invalidRole = tokenTextFixture.invalidRoleToken();
		return Stream.iterate(0, i -> i + 1)
				.limit(invalidRole.size())
				.map(i -> Arguments.of(invalidRole.get(i), i));
	}

	@ParameterizedTest
	@MethodSource("provideDataInvalidRole")
	void TokenTestInvalidRole(String input) throws Exception {
		// AAA
		// Arrange
		Token testToken = new Token(input);

		// Act
		Boolean action = _tokenService.ValidateToken(testToken);

		// Arrange
		assertThat(action).isFalse();
	}

	static Stream<Arguments> provideDataInvalidSeed() throws Exception {
		TokenTextFixture tokenTextFixture = new TokenTextFixture();
		List<String> invalidSeed = tokenTextFixture.invalidSeed();
		return Stream.iterate(0, i -> i + 1)
				.limit(invalidSeed.size())
				.map(i -> Arguments.of(invalidSeed.get(i), i));
	}

	@ParameterizedTest
	@MethodSource("provideDataInvalidSeed")
	void TokenTestInvalidSeed(String input) throws Exception {
		// AAA
		// Arrange
		Token testToken = new Token(input);

		// Act
		Boolean action = _tokenService.ValidateToken(testToken);

		// Arrange
		assertThat(action).isFalse();
	}
}
