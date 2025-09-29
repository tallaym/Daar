package com.daar.persistence.jdbc.auth;


import com.daar.core.model.auth.User;
import com.daar.core.port.out.auth.UserRepository;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.*;



public class JdbcUser implements UserRepository {


    private final Connection connection;

    public JdbcUser(Connection connection) {
        this.connection = connection;
    }
    @Override
    public User insert(User user) {
        boolean inserted = false;

        while (!inserted) {
            try {
                String sql = "INSERT INTO users(id, firstname, lastname, phone, createdBy) VALUES (?,?,?,?,?)";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setObject(1, user.getId());
                    ps.setString(2, user.getFirstname());
                    ps.setString(3, user.getLastname());
                    ps.setString(4, user.getPhone());
                    ps.setObject(5, user.getCreatedBy());

                    ps.executeUpdate();
                    inserted = true;
                }
            } catch (SQLException e) {
                if ("23505".equals(e.getSQLState())) {
                    user.setId(UUID.randomUUID());
                } else {
                    throw new RuntimeException("Erreur lors de la sauvegarde de l'utilisateur", e);
                }
            }
        }

        return user;
    }

    @Override
    public User update(User user) {

            String sql = "UPDATE users " +
                    "       SET firstname = ?, " +
                    "           lastname = ?," +
                    "           origin = ?," +
                    "           identityType = ?, " +
                    "           identityNumber = ?," +
                    "           address = ?," +
                    "           email = ?, " +
                    "           phone = ?, " +
                    "           createdAt = ?, " +
                    "           updatedAt = ?, " +
                    "           suspendedUntil = ?, " +
                    "           createdBy = ?, " +
                    "           updatedBy = ?, " +
                    "           suspendedBy = ? " +
                    "       WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user.getFirstname());
                ps.setString(2, user.getLastname());
                ps.setString(3, user.getOrigin());
                ps.setObject(4, user.getIdentityType());
                ps.setString(5, user.getIdentityNumber());
                ps.setString(6, user.getAddress());
                ps.setString(7, user.getEmail());
                ps.setString(8, user.getPhone());
                ps.setObject(9, user.getCreatedAt());
                ps.setObject(10, user.getUpdatedAt());
                ps.setObject(11, user.getSuspendedUntil());
                ps.setObject(12, user.getCreatedBy());
                ps.setObject(13, user.getUpdatedBy());
                ps.setObject(14, user.getSuspendedBy());
                ps.setObject(15, user.getId());

                int updatedRows = ps.executeUpdate();
                if (updatedRows == 0) {
                    throw new RuntimeException("Utilisateur introuvable pour la mise à jour : " + user.getId());
                }

        } catch (SQLException e) {

            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(userTemplate(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur", e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(userTemplate(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les utilisateurs", e);
        }
    }

    @Override
    public List<User> findAddedAfter(Date start) {
        String sql = "SELECT * FROM users WHERE created_at > ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(start.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(userTemplate(rs));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs ajoutés après " + start, e);
        }
    }

    @Override
    public List<User> findAddedBetween(Date start, Date end) {
        String sql = "SELECT * FROM users WHERE created_at BETWEEN ? AND ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(start.getTime()));
            ps.setTimestamp(2, new Timestamp(end.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(userTemplate(rs));
                }
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs entre " + start + " et " + end, e);
        }
    }

    private User userTemplate(ResultSet rs) throws SQLException {
    User user = new User();

        user.setId((UUID) rs.getObject("id"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));

        String origin = rs.getString("origin");
        user.setOrigin(origin != null ? origin : "Senegalese");

        String identityStr = rs.getString("identity_type");
        if (identityStr != null) {
            user.setIdentityType(User.IdentityType.valueOf(identityStr));
        }

        user.setIdentityNumber(rs.getString("identity_number"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));

        Timestamp createdTs = rs.getTimestamp("created_at");
        if (createdTs != null) {
            user.setCreatedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updated_at");
        if (updatedTs != null) {
            user.setUpdatedAt(updatedTs.toInstant());
        }

        Timestamp suspendedTs = rs.getTimestamp("suspended_until");
        if (suspendedTs != null) {
            user.setSuspendedUntil(suspendedTs.toInstant());
        }

        user.setCreatedBy((UUID) rs.getObject("created_by"));
        user.setUpdatedBy((UUID) rs.getObject("updated_by"));
        user.setSuspendedBy((UUID) rs.getObject("suspended_by"));

        return user;

    }

}
