package com.daar.adapter.out.jdbc.document;

import com.daar.core.domain.model.document.TypeDocument;
import com.daar.core.domain.repository.document.TypeDocumentRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.*;

public class JdbcType implements TypeDocumentRepository {

    private final DataSource dataSource;

    public JdbcType(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TypeDocument insert(TypeDocument type) {
        String sql = "INSERT INTO document_types (name, description, createdBy, createdAt) VALUES (?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, type.getName());
            ps.setString(2, type.getDescription());
            ps.setObject(3, type.getCreatedBy());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));

            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Échec de l'insertion du type");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du type", e);
        }

        return type;
    }

    @Override
    public TypeDocument update(TypeDocument type) {
        String sql = """
            UPDATE document_types
            SET name = ?, description = ?, updatedBy = ?, updatedAt = ?
            WHERE id = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, type.getName());
            ps.setString(2, type.getDescription());
            ps.setObject(3, type.getUpdatedBy());
            ps.setTimestamp(4, type.getUpdatedAt() != null ? Timestamp.from(type.getUpdatedAt()) : Timestamp.from(Instant.now()));
            ps.setObject(5, type.getId());

            int updated = ps.executeUpdate();
            if (updated == 0) throw new SQLException("Échec de la mise à jour du type");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du type", e);
        }

        return type;
    }

    @Override
    public Optional<TypeDocument> findById(UUID id) {
        String sql = "SELECT * FROM document_types WHERE id = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapType(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du type avec id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM document_types WHERE id = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 0) throw new SQLException("Aucun type trouvé avec id " + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du type", e);
        }
    }

    @Override
    public List<TypeDocument> findAll() {
        String sql = "SELECT * FROM document_types";
        List<TypeDocument> list = new ArrayList<>();

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapType(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les types", e);
        }

        return list;
    }

    private TypeDocument mapType(ResultSet rs) throws SQLException {
        TypeDocument type = new TypeDocument();
        type.setId((UUID) rs.getObject("id"));
        type.setName(rs.getString("name"));
        type.setDescription(rs.getString("description"));
        type.setCreatedBy((UUID) rs.getObject("createdBy"));
        type.setUpdatedBy((UUID) rs.getObject("updatedBy"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) type.setCreatedAt(createdTs.toInstant());

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) type.setUpdatedAt(updatedTs.toInstant());

        return type;
    }
}
