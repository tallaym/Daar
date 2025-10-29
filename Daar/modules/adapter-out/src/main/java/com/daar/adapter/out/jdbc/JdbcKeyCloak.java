package com.daar.adapter.out.jdbc;

import com.daar.core.port.out.auth.KeyCloakRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public class JdbcKeyCloak implements KeyCloakRepository {

    private final Keycloak keycloak;
    private final String realm;

    public JdbcKeyCloak(String serverUrl, String realm, String clientId, String clientSecret, String adminUsername, String adminPassword) {
        this.realm = realm;
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
}
