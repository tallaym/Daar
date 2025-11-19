package com.daar.adapter.out.jdbc;

import com.daar.core.domain.port_out.KeycloakTokenDTO;
import com.daar.core.domain.port_out.auth.KeyCloakRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


public class JdbcKeyCloak implements KeyCloakRepository {

    private final Keycloak keycloak;
    private final KeyCloakConfig config;


    public JdbcKeyCloak(KeyCloakConfig config) {
        this.config = config;
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(config.serverUrl())
                .realm("master")
                .clientId(config.clientId())
                .clientSecret(config.clientSecret())
                .username(config.adminUsername())
                .password(config.adminPassword())
                .build();
    }

    @Override
    public String createKeycloakUser(String firstname, String lastname, String phone) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(phone);
        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        newUser.setEmail(phone + "@example.com");
        newUser.setEnabled(false);

            keycloak.realm(config.realm()).users().create(newUser);

        return  keycloak.realm(config.realm()).users().search(phone).get(0).getId();



    }

    @Override
    public boolean updateKeycloakUser(String keyCloakId, String firstname, String lastname, String phone, String email) {
        try{
            UserRepresentation userUpdated = new UserRepresentation();
            userUpdated.setUsername(phone);
            userUpdated.setFirstName(firstname);
            userUpdated.setLastName(lastname);
            userUpdated.setEmail(email);

            UsersResource ur = keycloak.realm(config.realm()).users();
                ur.get(keyCloakId).update(userUpdated);
                return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteKeycloakUser(String keyCloakId) {
        try {
            keycloak.realm(config.realm()).users().get(keyCloakId).remove();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changePassword(String keyCloakId, String newPassword) {
        try{
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setValue(newPassword);
            cred.setTemporary(false);

            keycloak.realm(config.realm())
                    .users()
                    .get(keyCloakId)
                    .resetPassword(cred);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(String contact) {
        try{

            var users = keycloak.realm(config.realm()).users().search(contact);

            if(users.isEmpty()) return false;

            String keyCloakId = users.get(0).getId();
            keycloak.realm(config.realm())
                    .users()
                    .get(keyCloakId)
                    .executeActionsEmail(List.of("UPDATE_PASSWORD"));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public KeycloakTokenDTO login(String username, String password) {
        Keycloak kcUser = KeycloakBuilder.builder()
                .serverUrl(config.serverUrl())
                .realm(config.realm())           // ton realm normal
                .clientId(config.clientId())
                .clientSecret(config.clientSecret())
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        AccessTokenResponse token =  kcUser.tokenManager().getAccessToken();

        return new KeycloakTokenDTO(
                token.getToken(),
                token.getRefreshToken(),
                token.getExpiresIn(),
                token.getRefreshExpiresIn()
        );
    }


    @Override
    public boolean logout(String keycloakId) {
        try {
            keycloak.realm(config.realm()).users().get(keycloakId).logout();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public KeycloakTokenDTO refreshToken(String refreshToken) {
        String tokenUrl = config.serverUrl() + "/realms/" + config.realm() + "/protocol/openid-connect/token";

        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("grant_type", "refresh_token");
        form.param("client_id", config.clientId());
        form.param("client_secret", config.clientSecret());
        form.param("refresh_token", refreshToken);

        Response response = client.target(tokenUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form));

        String json = response.readEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();

        try{
            JsonNode node = mapper.readTree(json);

            return new KeycloakTokenDTO(
                    node.get("access_token").asText(),
                    node.get("refresh_token").asText(),
                    node.get("expires_in").asLong(),
                    node.get("refresh_expires_in").asLong()
            );
        }catch(Exception e){
            throw new RuntimeException("Failed to parse token response", e);

        }
    }

}
