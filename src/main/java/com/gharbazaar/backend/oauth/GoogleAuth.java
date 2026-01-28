package com.gharbazaar.backend.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gharbazaar.backend.dto.OAuthUser;
import com.gharbazaar.backend.exception.OAuthException;
import com.gharbazaar.backend.exception.UnverifiedEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleAuth {
    private final RestTemplate restTemplate;

    @Value("${oauth.google.clientId}")
    private String clientId;

    @Value("${oauth.google.clientSecret}")
    private String clientSecret;

    @Value("${oauth.google.redirectUri}")
    private String redirectUri;


    private OAuthRes getAccessToken(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", redirectUri);
        map.add("grant_type", "authorization_code");
        map.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(map, headers);

        try {
            OAuthRes oAuthRes = restTemplate.postForObject("https://oauth2.googleapis.com/token", body, OAuthRes.class);

            if (oAuthRes == null) throw new OAuthException("Error Getting Access Token: OAuth Res is null");

            if (!oAuthRes.scope().contains("profile") || !oAuthRes.scope().contains("email")) {
                throw new OAuthException("Error Getting Access Token: Invalid Scope");
            }

            return oAuthRes;
        } catch (RestClientException e) {
            System.out.println("OAuth Exception While getting IdToken: " + e.getMessage());
            throw new OAuthException("Error Getting Access Token: ", e);
        }
    }

    public OAuthUser getOAuthUser(final String code) {
        OAuthRes oAuthRes = getAccessToken(code);
        System.out.println("Scopes: " + oAuthRes.scope());
        final String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + oAuthRes.idToken();

        try {
            OAuthUser user = restTemplate.getForObject(url, OAuthUser.class);

            if (user == null) throw new OAuthException("Error Getting User Data: OAuth User is null");

            if (!user.verified()) throw new UnverifiedEmail("Email is not verified");

            if (user.name() == null || user.email() == null)
                throw new OAuthException("Invalid Scope name or email is null");

            return user;
        } catch (RestClientException e) {
            System.out.println("OAuth Exception While getting user info: " + e.getMessage());
            throw new OAuthException("Error Getting Access Token: ", e);
        }
    }

    private record OAuthRes(@JsonProperty("access_token") String accessToken,
                            @JsonProperty("id_token") String idToken,
                            @JsonProperty("expires_in") long expiresIn,
                            @JsonProperty("refresh_token") String refreshToken,
                            String scope) {
    }
}