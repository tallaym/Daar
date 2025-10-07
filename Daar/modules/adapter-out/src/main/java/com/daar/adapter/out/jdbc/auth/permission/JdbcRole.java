package com.daar.adapter.out.jdbc.auth.permission;

import com.daar.core.domain.model.auth.permission.Role;
import com.daar.core.port.out.auth.permission.RoleRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.*;

public class JdbcRole implements RoleRepository {

    private final DataSource dataSource;

    public JdbcRole(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Role insert(Role role) {
        String sql = "INSERT INTO roles (rolename, description, createdBy, createdAt) VALUES (?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getDescription());
            ps.setObject(3, role.getCreatedBy());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));

            if (ps.executeUpdate() == 0) throw new SQLException("Insertion échouée");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du rôle", e);
        }
        return role;
    }

    @Override
    public Role update(Role role) {
        String sql = """
            UPDATE roles
            SET rolename = ?,
                description = ?,
                updatedAt = ?,
                updatedBy = ?
            WHERE id = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getDescription());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setObject(4, role.getUpdatedBy());
            ps.setObject(5, role.getId());

            if (ps.executeUpdate() == 0) throw new SQLException("Modification échouée");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du rôle", e);
        }

        return role;
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            if (ps.executeUpdate() == 0) throw new SQLException("Aucun rôle trouvé pour id " + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du rôle " + id, e);
        }
    }

    @Override
    public Optional<Role> findById(UUID id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du rôle " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Role> allRoles() {
        String sql = "SELECT * FROM roles";
        List<Role> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des rôles", e);
        }

        return list;
    }

    private Role map(ResultSet rs) throws SQLException {
        Role r = new Role();
        r.setId((UUID) rs.getObject("id"));
        r.setRoleName(rs.getString("rolename"));
        r.setDescription(rs.getString("description"));
        r.setCreatedBy((UUID) rs.getObject("createdBy"));
        r.setUpdatedBy((UUID) rs.getObject("updatedBy"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) r.setCreatedAt(createdTs.toInstant());

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) r.setUpdatedAt(updatedTs.toInstant());

        return r;
    }
}
