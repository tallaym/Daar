package com.daar.persistence.jdbc.auth.permission;

import com.daar.core.model.auth.permission.UseRole;
import com.daar.core.model.auth.permission.UserPermission;
import com.daar.core.port.out.auth.permission.UserPermissionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserPermission implements UserPermissionRepository {

    private final Connection cn;

    public JdbcUserPermission(Connection cn) {
        this.cn = cn;
    }

    @Override
    public UserPermission insert(UserPermission ur) {
        String sql = "INSERT into UserPermissions(id, userId, permissionId, createdBy, expiresAt)" +
                "                   VALUES(?,?,?,?,?)";

        try(PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setObject(1, ur.getId());
            ps.setObject(2, ur.getUserId());
            ps.setObject(3, ur.getPermissionId());
            ps.setObject(4, ur.getCreatedBy());
            if(ur.getExpiresAt() != null){
                ps.setTimestamp(5, Timestamp.from(ur.getExpiresAt()));
            }else{
                ps.setNull(5, Types.VARCHAR);
            }

            if(ps.executeUpdate() == 0){
                throw new SQLException("insertion échouée");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ur;
    }

    @Override
    public UserPermission update(UserPermission ur) {
        String sql = "UPDATE UserPermissions" +
                "           SET updatedAt = ?" +
                "               expiresAt = ?" +
                "           WHERE id = ?";

        try(PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.from(ur.getUpdatedAt()));
            ps.setTimestamp(2, Timestamp.from(ur.getExpiresAt()));
            ps.setObject(3, ur.getId());

            if(ps.executeUpdate() == 0){
                throw new SQLException("modification échouée");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ur;
    }

    @Override
    public List<UserPermission> findUserPermissions(UUID userId) {
        String sql = "SELECT * FROM UserPermission WHERE userId = ?";
        List<UserPermission> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(userPermissionTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des roles de l'utilisateur", e);
        }

        return list;
    }

    @Override
    public List<UserPermission> findPermissionUses(UUID permissionId) {
        String sql = "SELECT * FROM UserPermission WHERE permissionId = ?";
        List<UserPermission> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, permissionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(userPermissionTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs du role", e);
        }

        return list;
    }

    @Override
    public List<UserPermission> findAll() {
        String sql = "SELECT * FROM UserPermissions";
        List<UserPermission> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(userPermissionTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les userRoles", e);
        }

        return list;
    }

    @Override
    public Optional<UserPermission> findUserPermission(UUID userId, UUID permissionId) {
        String sql = "SELECT * FROM UserPermission WHERE userId = ? AND permissionId = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, userId);
            ps.setObject(2, permissionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(userPermissionTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du UseRole", e);
        }

        return Optional.empty();

    }

    private UserPermission userPermissionTemplate(ResultSet rs) throws SQLException {
        UserPermission ur = new UserPermission();
        ur.setId((UUID) rs.getObject("id"));
        ur.setUserId((UUID) rs.getObject("userId"));
        ur.setPermissionId((UUID) rs.getObject("permissionId"));

        Timestamp createdTs = rs.getTimestamp("assignedAt");
        if (createdTs != null) {
            ur.setAssignedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) {
            ur.setUpdatedAt(updatedTs.toInstant());
        }

        Timestamp expiresTs = rs.getTimestamp("expiresAt");
        if (expiresTs != null) {
            ur.setExpiresAt(expiresTs.toInstant());
        }

        ur.setCreatedBy((UUID) rs.getObject("createdBy"));


        return ur;
    }



}
