package com.daar.adapter.out.jdbc.auth;

import com.daar.core.domain.model.auth.Credential;
import com.daar.core.port.out.auth.CredentialRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.*;

public class JdbcCredential implements CredentialRepository {

    private final DataSource dataSource;

    public JdbcCredential(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Credential insert(UUID userId, Credential.CredentialType type, String identifier, String secret, Instant expiresAt){

        Credential credential = new Credential(userId,type,identifier, BCrypt.hashpw(secret, BCrypt.gensalt()),expiresAt);

        String sql = """
            INSERT INTO credentials (id, user_id, type, identifier, secret, expires_at)
                            VALUES (?, ?, ?, ?, ?, ?)
            ;
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setObject(1, credential.getId());
            stmt.setObject(2, credential.getUserId());
            stmt.setString(3, credential.getType().name());
            stmt.setString(4, credential.getIdentifier());
            stmt.setString(5, credential.getSecret());
            stmt.setTimestamp(6, credential.getExpiresAt() != null ? Timestamp.from(credential.getExpiresAt()) : null);

            int insertedRows = stmt.executeUpdate();
            if(insertedRows == 0){
                throw new SQLException("Échec de l'insertion, aucune ligne affectée.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return credential;
    }

    @Override
    public Credential update(Credential cr) {
        if (cr.getId() == null || cr.getUserId() == null) {
            throw new IllegalArgumentException("Cannot update credential without ID");
        }

        cr.setSecret(BCrypt.hashpw(cr.getSecret(), BCrypt.gensalt()));

        String sql = """
            UPDATE credentials
            SET secret = ?, updated_at = ?, expires_at = ?
            WHERE identifier = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setString(1, cr.getSecret());
            stmt.setTimestamp(2, Timestamp.from(Instant.now()));
            stmt.setTimestamp(3, cr.getExpiresAt() != null ? Timestamp.from(cr.getExpiresAt()) : null);
            stmt.setObject(4, cr.getIdentifier());

            int updatedRows = stmt.executeUpdate();
            if (updatedRows == 0){
                throw new SQLException("Modification échouée");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cr;
    }

    @Override
    public List<Credential> findByUserId(UUID userId) {
        String sql = "SELECT * FROM credentials WHERE user_id = ?";
        List<Credential> credentials = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setObject(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    credentials.add(credentialTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des credentials pour l'utilisateur", e);
        }

        return credentials;
    }

    @Override
    public Credential findByIdentifier(String identifier) {
        String sql = """
        SELECT id, user_id, type, identifier, secret
        FROM credentials
        WHERE identifier = ?
    """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement stmt = cn.prepareStatement(sql)) {

            stmt.setString(1, identifier);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Credential cr = new Credential();
                    cr.setId(UUID.fromString(rs.getString("id")));
                    cr.setUserId(UUID.fromString(rs.getString("user_id")));
                    cr.setType(Credential.CredentialType.valueOf(rs.getString("type")));
                    cr.setIdentifier(rs.getString("identifier"));
                    cr.setSecret(rs.getString("secret"));
                    return cr;
                } else {
                    throw new RuntimeException("Utilisateur inconnu");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Credential credentialTemplate(ResultSet rs) throws SQLException {
        Credential credential = new Credential();

        credential.setId((UUID) rs.getObject("id"));
        credential.setUserId((UUID) rs.getObject("user_id"));

        String typeStr = rs.getString("type");
        if (typeStr != null) {
            credential.setType(Credential.CredentialType.valueOf(typeStr));
        }

        credential.setIdentifier(rs.getString("identifier"));
        credential.setSecret(rs.getString("secret"));

        Timestamp createdTs = rs.getTimestamp("created_at");
        if (createdTs != null) credential.setCreatedAt(createdTs.toInstant());
        Timestamp updatedTs = rs.getTimestamp("updated_at");
        if (updatedTs != null) credential.setUpdatedAt(updatedTs.toInstant());
        Timestamp expiresTs = rs.getTimestamp("expires_at");
        if (expiresTs != null) credential.setExpiresAt(expiresTs.toInstant());

        return credential;
    }

    private Optional<String> validCredential(Credential credential) {
        switch (credential.getType()) {
            case PASSWORD:
                if (credential.getIdentifier() == null || credential.getSecret() == null) {
                    return Optional.of("identifiant et mdp requis pour cette clé d'accès");
                }
                break;
            case OTP_SEED:
                if (credential.getSecret() == null) {
                    return Optional.of("mdp requis pour cette clé d'accès");
                }
                break;
            case PASSKEY:
                break; // aucun champ obligatoire
            case OAUTH:
                if (credential.getIdentifier() == null) {
                    return Optional.of("identifiant requis pour cette clé d'accès");
                }
                break;
            default:
                return Optional.of("clé d'accès non prise en compte");
        }
        return Optional.empty();
    }
}
