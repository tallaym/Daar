package com.daar.adapter.out.jdbc;


import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.repository.auth.UserRepository;

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
                    ps.setObject(1, user.getId().toString());
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
        String sql = "UPDATE users SET firstname=?, lastname=?, origin=?, identityType=?, identityNumber=?, address=?, email=?, phone=?, updatedAt=?, suspendedUntil=?, updatedBy=?, suspendedBy=?, keyCloakId=? WHERE id=?";

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
            ps.setObject(9, user.getUpdatedAt());
            ps.setObject(10, user.getSuspendedUntil());
            ps.setObject(11, user.getUpdatedBy());
            ps.setObject(12, user.getSuspendedBy());
            ps.setString(13, user.getKeycloakId());
            ps.setObject(14, user.getId().toString());

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

            ps.setObject(1, id.toString());
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
        String sql = "SELECT * FROM users WHERE createdAt > ?";
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
        String sql = "SELECT * FROM users WHERE createdAt BETWEEN ? AND ?";
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
        user.setId(UUID.fromString(rs.getString("id")));
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
        String createdByStr = rs.getString("createdBy");
        if (createdByStr != null) user.setCreatedBy(UUID.fromString(createdByStr));

        String updatedByStr = rs.getString("updatedBy");
        if (updatedByStr != null) user.setUpdatedBy(UUID.fromString(updatedByStr));

        String suspendedByStr = rs.getString("suspendedBy");
        if (suspendedByStr != null) user.setSuspendedBy(UUID.fromString(suspendedByStr));
        return user;
    }
}
