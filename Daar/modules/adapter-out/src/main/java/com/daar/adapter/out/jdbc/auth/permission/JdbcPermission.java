package com.daar.adapter.out.jdbc.auth.permission;


import com.daar.core.domain.model.auth.permission.Perm;
import com.daar.core.port.out.auth.permission.PermRepository;

import javax.sql.DataSource;
import java.security.Permission;
import java.sql.*;
import java.time.Instant;
import java.util.*;

public class JdbcPermission implements PermRepository {

    private final DataSource dataSource;

    public JdbcPermission(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Perm insert(Perm perm) {

        String sql = "INSERT INTO permissions (permission_name, description, createdBy, createdAt) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, perm.getPermission_name());
            ps.setString(2, perm.getDescription());
            ps.setObject(3, perm.getCreatedBy());
            ps.setTimestamp(5, Timestamp.from(Instant.now()));

            int inserted = ps.executeUpdate();
            if (inserted == 0) throw new SQLException("Insertion échouée");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de la permission", e);
        }

        return perm;
    }

    @Override
    public Perm update(Perm permission) {
        String sql = """
            UPDATE permissions
            SET permission_name = ?,
                description = ?,
                updatedAt = ?,
                updatedBy = ?,
                roleId = ?
            WHERE id = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, permission.getPermission_name());
            ps.setString(2, permission.getDescription());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setObject(4, permission.getUpdatedBy());
            if (permission.getRoleId() != null) ps.setObject(5, permission.getRoleId());
            else ps.setNull(5, Types.VARCHAR);
            ps.setObject(6, permission.getId());

            int updated = ps.executeUpdate();
            if (updated == 0) throw new SQLException("Modification échouée");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la permission", e);
        }

        return permission;
    }

    @Override
    public Optional<Perm> findById(UUID id) {
        String sql = "SELECT * FROM permissions WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la permission " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM permissions WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 0) throw new SQLException("Aucune permission trouvée pour id " + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la permission " + id, e);
        }
    }

    @Override
    public List<Perm> allPermissions() {
        String sql = "SELECT * FROM permissions";
        List<Perm> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des permissions", e);
        }

        return list;
    }

    public List<Perm> getAllPermissions(UUID roleId) {
        String sql = "SELECT * FROM permissions WHERE roleId = ?";
        List<Perm> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des permissions du role " + roleId, e);
        }

        return list;
    }

    private Perm map(ResultSet rs) throws SQLException {
        Perm p = new Perm();
        p.setId((UUID) rs.getObject("id"));
        p.setPermission_name(rs.getString("permission_name"));
        p.setDescription(rs.getString("description"));
        p.setCreatedBy((UUID) rs.getObject("createdBy"));
        p.setUpdatedBy((UUID) rs.getObject("updatedBy"));
        p.setRoleId((UUID) rs.getObject("roleId"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) p.setCreatedAt(createdTs.toInstant());

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) p.setUpdatedAt(updatedTs.toInstant());

        return p;
    }

}
