package com.example.squick.security.converter;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.stream.Collectors;

public class CognitoRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String COGNITO_GROUPS = "cognito:groups";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        JSONArray cognitoGroups = (JSONArray) jwt.getClaims().get(COGNITO_GROUPS);

        if (cognitoGroups == null) {
            cognitoGroups = new JSONArray();
        }

        cognitoGroups.add("ADMIN");

        return cognitoGroups.stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


}
