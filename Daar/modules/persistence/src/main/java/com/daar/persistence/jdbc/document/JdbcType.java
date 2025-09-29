package com.daar.persistence.jdbc.document;

import com.daar.core.model.document.Type;
import com.daar.core.port.out.document.TypeRepository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcType implements TypeRepository {

    private final Connection cn;

    public JdbcType(Connection cn) {
        this.cn = cn;
    }

    @Override
    public Type insert(Type type) {
        String sql = "INSERT into document_types(name, createdBy)" +
                "                   VALUES(?,?)";

        try(PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1, type.getName());
            ps.setObject(2, type.getCreatedBy());

            if(ps.executeUpdate() == 0){
                throw new SQLException("Echec de l'ajout");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return type;
    }

    @Override
    public Type update(Type type){
        String sql = "UPDATE document_types " +
                "       SET name = ?," +
                "           description = ?," +
                "           updatedBy = ?, " +
                "           updatedAt = ? " +
                "       WHERE id = ?";

        try(PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1, type.getName());
            ps.setString(2, type.getDescription());
            ps.setObject(3, type.getUpdatedBy());
            ps.setTimestamp(4, type.getUpdatedAt() != null ? Timestamp.from(type.getUpdatedAt()) : Timestamp.from(Instant.now()));
            ps.setObject(5, type.getId());

            if(ps.executeUpdate() == 0){
                throw new SQLException("Echec de la modification");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }
    @Override
    public Optional<Type> findById(UUID id) {
        String sql = "SELECT * FROM document_types WHERE id = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(typeTemplate(rs)); // méthode à créer pour mapper ResultSet → Type
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du type avec id " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM document_types WHERE id = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, id);

            int deletedRows = ps.executeUpdate();
            if (deletedRows == 0) {
                throw new SQLException("Aucun type trouvé avec id " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du type avec id " + id, e);
        }
    }

    @Override
    public List<Type> findAll() {
        String sql = "SELECT * FROM document_types";
        List<Type> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(typeTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les types", e);
        }

        return list;
    }

    private Type typeTemplate(ResultSet rs) throws SQLException {
        Type type = new Type();

        type.setId((UUID) rs.getObject("id"));
        type.setName(rs.getString("name"));
        type.setDescription(rs.getString("description"));
        type.setCreatedBy((UUID) rs.getObject("createdBy"));
        type.setUpdatedBy((UUID) rs.getObject("updatedBy"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) {
            type.setCreatedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) {
            type.setUpdatedAt(updatedTs.toInstant());
        }

        return type;
    }

}
