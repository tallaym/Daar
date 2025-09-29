package com.daar.persistence.jdbc.auth.permission;



import com.daar.core.model.auth.permission.Permission;
import com.daar.core.model.auth.permission.Role;
import com.daar.core.port.out.auth.permission.RoleRepository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcRole implements RoleRepository {

    private final Connection cn;

    public JdbcRole(Connection cn) {
        this.cn = cn;
    }

    @Override
    public Role insert(Role role) {
        String sql = "INSERT into Roles(rolename, createdBy) VALUES(?,?)";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, role.getRolename());
            ps.setObject(2, role.getCreatedBy());


            int insertedRows = ps.executeUpdate();
            if(insertedRows == 0){
                throw new SQLException("insertion echouée");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    @Override
    public Role update(Role permission) {
        String sql = "UPDATE Roles " +
                "        SET rolename = ?" +
                "            description = ?" +
                "            updated_at = ?" +
                "            updatedBy = ?" +
                "      WHERE id = ?   ";

        try(PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,permission.getRolename());
            ps.setString(2,permission.getDescription());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setObject(4, permission.getUpdatedBy());


            ps.setObject(5, permission.getId());

            int updatedRows = ps.executeUpdate();
            if(updatedRows == 0){
                throw new SQLException("modification échouée!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return permission;
    }

    @Override
    public Optional<Role> findById(UUID id) {
        String sql = "SELECT * FROM roles WHERE id = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(roleTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la permission : " + id, e);
        }

        return Optional.empty();
    }


    @Override
    public List<Role> allRoles() {
        String sql = "SELECT * FROM permissions";
        List<Role> roles = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roles.add(roleTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des permissions", e);
        }

        return roles;
    }




    private Role roleTemplate(ResultSet rs) throws SQLException {
        Role r = new Role();

        r.setId((UUID) rs.getObject("id"));
        r.setRolename(rs.getString("permission_name"));
        r.setDescription(rs.getString("description"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) {
            r.setCreatedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) {
            r.setUpdatedAt(updatedTs.toInstant());
        }

        r.setCreatedBy((UUID) rs.getObject("createdBy"));
        r.setUpdatedBy((UUID) rs.getObject("updatedBy"));

        return r;
    }

}
