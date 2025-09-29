package com.daar.persistence.jdbc.auth.permission;

import com.daar.core.model.auth.permission.Permission;
import com.daar.core.port.out.auth.permission.PermissionRepository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcPermission implements PermissionRepository {

    private final Connection cn;

    public JdbcPermission(Connection cn) {
        this.cn = cn;
    }

    @Override
    public Permission insert(Permission permission) {
        String sql = "INSERT into Permissions(permission_name, createdBy, roleId) VALUES(?,?,?)";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, permission.getPermission_name());
            ps.setObject(2, permission.getCreatedBy());
            if(permission.getRoleId() != null){
                ps.setObject(3, permission.getRoleId());
            }else{
                ps.setNull(3, Types.VARCHAR);
            }

            int insertedRows = ps.executeUpdate();
            if(insertedRows == 0){
                throw new SQLException("insertion echouée");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return permission;
    }

    @Override
    public Permission update(Permission permission) {
        String sql = "UPDATE Permissions " +
                "        SET permission_name = ?" +
                "            description = ?" +
                "            updated_at = ?" +
                "            updatedBy = ?" +
                "            roleId = ?" +
                "      WHERE id = ?   ";

        try(PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1,permission.getPermission_name());
            ps.setString(1,permission.getDescription());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setObject(4, permission.getUpdatedBy());

            if(permission.getRoleId() != null){
                ps.setObject(5, permission.getRoleId());
            }else{
                ps.setNull(5, Types.VARCHAR);
            }

            ps.setObject(6, permission.getId());

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
    public Optional<Permission> findById(UUID id) {
        String sql = "SELECT * FROM permissions WHERE id = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(permissionTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la permission : " + id, e);
        }

        return Optional.empty();
    }


    @Override
    public List<Permission> allPermissions() {
        String sql = "SELECT * FROM permissions";
        List<Permission> permissions = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                permissions.add(permissionTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des permissions", e);
        }

        return permissions;
    }

    public List<Permission> getAllPermissions(UUID roleId) {
        String sql = "SELECT * from permissions WHERE roleId = ?";

        List<Permission> myPermissions = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                myPermissions.add(permissionTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des permissions", e);
        }

        return myPermissions;
    }

    private Permission permissionTemplate(ResultSet rs) throws SQLException {
        Permission permission = new Permission();

        permission.setId((UUID) rs.getObject("id"));
        permission.setPermission_name(rs.getString("permission_name"));
        permission.setDescription(rs.getString("description"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) {
            permission.setCreatedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) {
            permission.setUpdatedAt(updatedTs.toInstant());
        }

        permission.setCreatedBy((UUID) rs.getObject("createdBy"));
        permission.setUpdatedBy((UUID) rs.getObject("updatedBy"));
        permission.setRoleId((UUID) rs.getObject("roleId"));

        return permission;
    }

}
