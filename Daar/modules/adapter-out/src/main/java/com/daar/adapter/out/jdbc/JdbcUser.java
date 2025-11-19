package com.daar.adapter.out.jdbc;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.port_out.auth.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class JdbcUser implements UserRepository {

    private final DataSource dataSource;

    public JdbcUser(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User insert(User user) {

        boolean inserted = false;
        int attempts = 0;
        final int maxAttempts = 5;

        while (!inserted && attempts < maxAttempts) {
            attempts++;
            try (Connection connection = dataSource.getConnection()) {
                String sql = "INSERT INTO users(id, firstname, lastname, phone, createdBy, keyCloakId) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setObject(1, user.getId());
                    ps.setString(2, user.getFirstname());
                    ps.setString(3, user.getLastname());
                    ps.setString(4, user.getPhone());
                    ps.setObject(5, user.getCreatedBy());
                    ps.setString(6, user.getKeycloakId());

                    ps.executeUpdate();
                    inserted = true; // insertion réussie
                }
            } catch (SQLException e) {
                if ("23505".equals(e.getSQLState()) && e.getMessage().contains("users_pkey")) {
                    user.setId(UUID.randomUUID());
                } else {
                    throw new RuntimeException("Erreur lors de la sauvegarde de l'utilisateur", e);
                }
            }
        }

        if (!inserted) {
            throw new RuntimeException("Impossible d'insérer l'utilisateur après " + maxAttempts + " tentatives");
        }

        return user;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET firstname=?, lastname=?, origin=?, identityType=?, identityNumber=?, address=?, email=?, phone=?, createdAt=?, updatedAt=?, suspendedUntil=?, createdBy=?, updatedBy=?, suspendedBy=?, keyCloak=? WHERE id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

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
            ps.setString(15, user.getKeycloakId());
            ps.setObject(16, user.getId());

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Utilisateur introuvable pour la mise à jour : " + user.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        }

        return user;
    }



    @Override
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

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

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
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

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(start.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(userTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs ajoutés après " + start, e);
        }

        return users;
    }

    @Override
    public List<User> findAddedBetween(Date start, Date end) {
        String sql = "SELECT * FROM users WHERE created_at BETWEEN ? AND ?";
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(start.getTime()));
            ps.setTimestamp(2, new Timestamp(end.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(userTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs entre " + start + " et " + end, e);
        }

        return users;
    }


    private User userTemplate(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId((UUID) rs.getObject("id"));
        user.setKeycloakId(rs.getString("keyCloakId"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setOrigin(Optional.ofNullable(rs.getString("origin")).orElse("Senegalese"));
        String identityStr = rs.getString("identityType");
        if (identityStr != null) user.setIdentityType(User.IdentityType.valueOf(identityStr));
        user.setIdentityNumber(rs.getString("identityNumber"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) user.setCreatedAt(createdTs.toInstant());
        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) user.setUpdatedAt(updatedTs.toInstant());
        Timestamp suspendedTs = rs.getTimestamp("suspendedUntil");
        if (suspendedTs != null) user.setSuspendedUntil(suspendedTs.toInstant());
        user.setCreatedBy((UUID) rs.getObject("createdBy"));
        user.setUpdatedBy((UUID) rs.getObject("updatedBy"));
        user.setSuspendedBy((UUID) rs.getObject("suspendedBy"));
        return user;
    }
}
