package com.daar.persistence.jdbc.auth;

import com.daar.core.model.auth.Credential;
import com.daar.core.port.out.auth.CredentialRepository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcCredential implements CredentialRepository {

    private final Connection cn;

    public JdbcCredential(Connection cn) {
        this.cn = cn;
    }


    @Override
    public Credential insert(Credential credential){
        Optional<String> error = validCredential(credential);
        if (error.isPresent()) {
            System.out.println("Insertion stopped: " + error.get());
            return null;
        }

        String sql = """
            INSERT INTO credentials (id, user_id, type, identifier, secret, expires_at)
                            VALUES (?, ?, ?, ?, ?, ?, ?)
            ;
        """;

        try (PreparedStatement stmt = cn.prepareStatement(sql)) {
            UUID id = credential.getId() != null ? credential.getId() : UUID.randomUUID();
            stmt.setObject(1, id);
            stmt.setObject(2, credential.getUserId());
            stmt.setString(3, credential.getType().name());

            if (credential.getIdentifier() != null) {
                stmt.setString(4, credential.getIdentifier());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            if (credential.getSecret() != null) {
                stmt.setString(5, credential.getSecret());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

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

        String sql = """
            UPDATE credentials
            SET identifier = ?, secret = ?, updated_at = ?, expires_at = ?
            WHERE id = ?
        """;

        try (PreparedStatement stmt = cn.prepareStatement(sql)) {
            stmt.setString(1, cr.getIdentifier());
            stmt.setString(2, cr.getSecret());
            stmt.setTimestamp(3, Timestamp.from(Instant.now()));
            stmt.setTimestamp(4, cr.getExpiresAt() != null ? Timestamp.from(cr.getExpiresAt()) : null);
            stmt.setObject(5, cr.getId());

            int updatedRows = stmt.executeUpdate();
            if (updatedRows == 0){
                throw new SQLException("modification echouée");
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
        try (PreparedStatement stmt = cn.prepareStatement(sql)) {
            stmt.setObject(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    credentials.add(credentialTemplate(rs));
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur", e);
        }
        return credentials;
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
        if (createdTs != null) {
            credential.setCreatedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updated_at");
        if (updatedTs != null) {
            credential.setUpdatedAt(updatedTs.toInstant());
        }

        Timestamp expiresTs = rs.getTimestamp("expires_at");
        if (expiresTs != null) {
            credential.setExpiresAt(expiresTs.toInstant());
        }

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
                // aucun champ obligatoire
                break;
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
