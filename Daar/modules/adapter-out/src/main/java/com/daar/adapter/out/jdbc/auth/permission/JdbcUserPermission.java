package com.daar.adapter.out.jdbc.auth.permission;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.domain.model.auth.permission.UserPermission;
import com.daar.core.port.out.auth.permission.UserPermRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserPermission implements UserPermRepository {

    private final DataSource dataSource;

    public JdbcUserPermission(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserPermission insert(User u, Perm p, Instant end, User agent) {
        String sql = """
            INSERT INTO UserPermissions (id, userId, permissionId, createdBy, expiresAt)
            VALUES (?, ?, ?, ?, ?)
        """;

        UserPermission up = new UserPermission(u.getId(), p.getId(), agent.getId(), end);

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, up.getId());
            ps.setObject(2, up.getUserId());
            ps.setObject(3, up.getPermissionId());
            ps.setObject(4, up.getCreatedBy());
            if (up.getExpiresAt() != null) {
                ps.setTimestamp(5, Timestamp.from(up.getExpiresAt()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Insertion échouée : aucune ligne affectée.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion d'une permission utilisateur", e);
        }

        return up;
    }

    @Override
    public UserPermission update(UserPermission up) {
        String sql = """
            UPDATE UserPermissions
               SET updatedAt = ?,
                   expiresAt = ?
             WHERE id = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.from(up.getUpdatedAt()));
            ps.setTimestamp(2, Timestamp.from(up.getExpiresAt()));
            ps.setObject(3, up.getId());

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Modification échouée");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return up;
    }

    @Override
    public void delete(UUID userId, UUID permissionId) {
        String sql = "DELETE FROM UserPermissions WHERE userId = ? AND permissionId = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);
            ps.setObject(2, permissionId);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Aucun match trouvé entre cet user et cette permission");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la permission de cet user", e);
        }
    }

    @Override
    public List<UserPermission> findUserPermissions(UUID userId) {
        String sql = "SELECT * FROM UserPermissions WHERE userId = ?";
        List<UserPermission> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(userPermissionTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des permissions de l'utilisateur", e);
        }

        return list;
    }

    @Override
    public List<UserPermission> findPermissionUses(UUID permissionId) {
        String sql = "SELECT * FROM UserPermissions WHERE permissionId = ?";
        List<UserPermission> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, permissionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(userPermissionTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs de la permission", e);
        }

        return list;
    }

    @Override
    public List<UserPermission> findAll() {
        String sql = "SELECT * FROM UserPermissions";
        List<UserPermission> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(userPermissionTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les permissions utilisateurs", e);
        }

        return list;
    }

    @Override
    public Optional<UserPermission> findUserPermission(UUID userId, UUID permissionId) {
        String sql = "SELECT * FROM UserPermissions WHERE userId = ? AND permissionId = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);
            ps.setObject(2, permissionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(userPermissionTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la permission utilisateur", e);
        }

        return Optional.empty();
    }

    private UserPermission userPermissionTemplate(ResultSet rs) throws SQLException {
        UserPermission up = new UserPermission();
        up.setId((UUID) rs.getObject("id"));
        up.setUserId((UUID) rs.getObject("userId"));
        up.setPermissionId((UUID) rs.getObject("permissionId"));

        Timestamp createdTs = rs.getTimestamp("assignedAt");
        if (createdTs != null) up.setAssignedAt(createdTs.toInstant());

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) up.setUpdatedAt(updatedTs.toInstant());

        Timestamp expiresTs = rs.getTimestamp("expiresAt");
        if (expiresTs != null) up.setExpiresAt(expiresTs.toInstant());

        up.setCreatedBy((UUID) rs.getObject("createdBy"));

        return up;
    }
}
