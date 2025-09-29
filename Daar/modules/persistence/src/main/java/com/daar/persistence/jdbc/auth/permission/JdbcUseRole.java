package com.daar.persistence.jdbc.auth.permission;

import com.daar.core.model.auth.permission.UseRole;
import com.daar.core.port.out.auth.permission.UseRoleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUseRole implements UseRoleRepository {

    private final Connection cn;

    public JdbcUseRole(Connection cn) {
        this.cn = cn;
    }

    @Override
    public UseRole insert(UseRole ur) {
        String sql = "INSERT into UseRole(id, userId, RoleId, createdBy, expiresAt)" +
                "                   VALUES(?,?,?,?,?)";

        try(PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setObject(1, ur.getId());
            ps.setObject(2, ur.getUserId());
            ps.setObject(3, ur.getRoleId());
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
    public UseRole update(UseRole ur) {
        String sql = "UPDATE useroles" +
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
    public List<UseRole> findUserRoles(UUID userId) {
        String sql = "SELECT * FROM useroles WHERE userId = ?";
        List<UseRole> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(useRoleTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des roles de l'utilisateur", e);
        }

        return list;
    }

    @Override
    public List<UseRole> findRoleUsers(UUID roleId) {
        String sql = "SELECT * FROM useroles WHERE roleId = ?";
        List<UseRole> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(useRoleTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs du role", e);
        }

        return list;
    }

    @Override
    public List<UseRole> findAll() {
        String sql = "SELECT * FROM useroles";
        List<UseRole> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
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
    public Optional<UseRole> findUseRole(UUID userId, UUID roleId) {
        String sql = "SELECT * FROM useroles WHERE userId = ? AND roleId = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, userId);
            ps.setObject(2, roleId);

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
