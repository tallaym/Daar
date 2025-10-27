package com.daar.adapter.out.jdbc.auth.permission;

import com.daar.core.domain.model.auth.permission.UseRole;
import com.daar.core.port.out.auth.permission.UseRoleRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUseRole implements UseRoleRepository {

    private final DataSource dataSource;

    public JdbcUseRole(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UseRole insert(UseRole ur) {
        String sql = """
            INSERT INTO UseRoles (id, userId, roleId, createdBy, expiresAt)
            VALUES (?, ?, ?, ?, ?)
        """;


        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, ur.getId());
            ps.setObject(2, ur.getUserId());
            ps.setObject(3, ur.getRoleId());
            ps.setObject(4, ur.getCreatedBy());
            if (ur.getExpiresAt() != null) {
                ps.setTimestamp(5, Timestamp.from(ur.getExpiresAt()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Insertion échouée : aucune ligne affectée.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion d'un nouveau rôle", e);
        }

        return ur;
    }

    @Override
    public UseRole update(UseRole ur) {
        String sql = """
            UPDATE UseRoles
               SET updatedAt = ?,
                   expiresAt = ?
             WHERE id = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.from(ur.getUpdatedAt()));
            ps.setTimestamp(2, Timestamp.from(ur.getExpiresAt()));
            ps.setObject(3, ur.getId());

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Modification échouée");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ur;
    }

    @Override
    public void delete(UUID userId, UUID roleId) {
        String sql = "DELETE FROM UseRoles WHERE userId = ? AND roleId = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);
            ps.setObject(2, roleId);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Aucun match trouvé entre cet user et ce rôle");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du rôle de cet user", e);
        }
    }

    @Override
    public List<UseRole> findUserRoles(UUID userId) {
        String sql = "SELECT * FROM UseRoles WHERE userId = ?";
        List<UseRole> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(useRoleTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des rôles de l'utilisateur", e);
        }

        return list;
    }

    @Override
    public Optional<UseRole> findByUserId(UUID userId) {
        String sql = "SELECT * FROM UseRoles WHERE userId = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(useRoleTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du UseRole", e);
        }

        return Optional.empty();
    }

    @Override
    public List<UseRole> findRoleUsers(UUID roleId) {
        String sql = "SELECT * FROM UseRoles WHERE roleId = ?";
        List<UseRole> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(useRoleTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs du rôle", e);
        }

        return list;
    }

    @Override
    public List<UseRole> findAll() {
        String sql = "SELECT * FROM UseRoles";
        List<UseRole> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(useRoleTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les userRoles", e);
        }

        return list;
    }

    @Override
    public Optional<UseRole> findUseRole(UUID urId) {
        String sql = "SELECT * FROM UseRoles WHERE id = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, urId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(useRoleTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du UseRole", e);
        }

        return Optional.empty();
    }

    private UseRole useRoleTemplate(ResultSet rs) throws SQLException {
        UseRole ur = new UseRole();
        ur.setId((UUID) rs.getObject("id"));
        ur.setUserId((UUID) rs.getObject("userId"));
        ur.setRoleId((UUID) rs.getObject("roleId"));

        Timestamp createdTs = rs.getTimestamp("assignedAt");
        if (createdTs != null) ur.setCreatedAt(createdTs.toInstant());

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) ur.setUpdatedAt(updatedTs.toInstant());

        Timestamp expiresTs = rs.getTimestamp("expiresAt");
        if (expiresTs != null) ur.setExpiresAt(expiresTs.toInstant());

        ur.setCreatedBy((UUID) rs.getObject("createdBy"));

        return ur;
    }
}
