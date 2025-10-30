package com.daar.adapter.out.jdbc;

import com.daar.core.port.out.auth.KeyCloakRepository;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
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
    private final String realm;

    private final String clientId;
    private final String clientSecret;
    private final String serverUrl;

    public JdbcKeyCloak(String serverUrl, String realm, String clientId,
                        String clientSecret, String adminUsername, String adminPassword) {
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    @Override
    public String createUser(String firstname, String lastname, String phone) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(phone);
        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        newUser.setEnabled(true);

            keycloak.realm(realm).users().create(newUser);

        return  keycloak.realm(realm).users().search(phone).get(0).getId();



    }

    @Override
    public boolean updateUser(String keyCloakId, String firstname, String lastname, String phone) {
        try{
            UserRepresentation userUpated = new UserRepresentation();
            userUpated.setUsername(phone);
            userUpated.setFirstName(firstname);
            userUpated.setLastName(lastname);

            UsersResource ur = keycloak.realm(realm).users();
                ur.get(keyCloakId).update(userUpated);
                return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(String keyCloakId) {
        try {
            keycloak.realm(realm).users().get(keyCloakId).remove();
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

            keycloak.realm(realm)
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

            var users = keycloak.realm(realm).users().search(contact);

            if(users.isEmpty()) return false;

            String keyCloakId = users.get(0).getId();
            keycloak.realm(realm)
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
    public String login(String username, String password) {
        Keycloak kcUser = KeycloakBuilder.builder()
                .serverUrl(this.serverUrl)
                .realm(this.realm)           // ton realm normal
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        return kcUser.tokenManager().getAccessTokenString();
    }


    @Override
    public boolean logout(String keycloakId) {
        try {
            keycloak.realm(realm).users().get(keycloakId).logout();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String refreshToken(String refreshToken) {
        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("grant_type", "refresh_token");
        form.param("client_id", clientId);
        form.param("client_secret", clientSecret);
        form.param("refresh_token", refreshToken);

        Response response = client.target(tokenUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form));

        String json = response.readEntity(String.class);
        // TODO : parser le JSON pour récupérer "access_token"
        return json;
    }

}
