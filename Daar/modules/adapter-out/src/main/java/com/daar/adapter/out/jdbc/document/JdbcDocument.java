package com.daar.adapter.out.jdbc.document;

import com.daar.core.domain.model.document.Document;
import com.daar.core.domain.repository.document.DocumentRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;

public class JdbcDocument implements DocumentRepository {

    private final DataSource dataSource;

    public JdbcDocument(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Document insert(Document doc) {
        String sql = """
            INSERT INTO documents (
                id, docName, docFormat, storageType, storagePath,
                isSensitive, parentID, createdBy, typeId, createdAt
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, doc.getId() != null ? doc.getId() : UUID.randomUUID());
            ps.setString(2, doc.getDocName());
            ps.setString(3, doc.getDocFormat());
            ps.setString(4, doc.getStorageType());
            ps.setString(5, doc.getStoragePath());
            ps.setBoolean(6, doc.isSensitive());
            ps.setObject(7, doc.getParentID());
            ps.setObject(8, doc.getCreatedBy());
            ps.setObject(9, doc.getTypeId());
            ps.setTimestamp(10, Timestamp.from(Instant.now()));

            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Échec de l'insertion du document");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du document", e);
        }

        return doc;
    }

    @Override
    public Document update(Document doc) {
        String sql = """
            UPDATE documents
            SET docName = ?, docFormat = ?, storageType = ?, storagePath = ?,
                isSensitive = ?, parentID = ?, updatedAt = ?, updatedBy = ?, typeId = ?
            WHERE id = ?
        """;

        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, doc.getDocName());
            ps.setString(2, doc.getDocFormat());
            ps.setString(3, doc.getStorageType());
            ps.setString(4, doc.getStoragePath());
            ps.setBoolean(5, doc.isSensitive());
            ps.setObject(6, doc.getParentID());
            ps.setTimestamp(7, doc.getUpdatedAt() != null ? Timestamp.from(doc.getUpdatedAt()) : Timestamp.from(Instant.now()));
            ps.setObject(8, doc.getUpdatedBy());
            ps.setObject(9, doc.getTypeId());
            ps.setObject(10, doc.getId());

            int updated = ps.executeUpdate();
            if (updated == 0) throw new SQLException("Échec de la mise à jour du document");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du document", e);
        }

        return doc;
    }

    @Override
    public Optional<Document> findById(UUID id) {
        String sql = "SELECT * FROM documents WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(documentTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du document", e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM documents WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 0) throw new SQLException("Aucun document trouvé avec id " + id);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du document", e);
        }
    }

    @Override
    public List<Document> findAll() {
        String sql = "SELECT * FROM documents";
        List<Document> list = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(documentTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les documents", e);
        }
        return list;
    }

    @Override
    public List<Document> findAddedAfter(Date start) {
        if (start == null) return Collections.emptyList();

        String sql = "SELECT * FROM documents WHERE createdAt > ?";
        List<Document> list = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(start.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents après " + start, e);
        }
        return list;
    }

    @Override
    public List<Document> findAddedBetween(Date start, Date end) {
        if (start == null || end == null) return Collections.emptyList();

        String sql = "SELECT * FROM documents WHERE createdAt BETWEEN ? AND ?";
        List<Document> list = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(start.getTime()));
            ps.setTimestamp(2, new Timestamp(end.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents entre " + start + " et " + end, e);
        }
        return list;
    }

    @Override
    public List<Document> findByUserId(UUID userId) {
        String sql = "SELECT * FROM documents WHERE userId = ?";
        List<Document> list = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents du user " + userId, e);
        }
        return list;
    }

    @Override
    public List<Document> findByAuthorId(UUID userId) {
        String sql = "SELECT * FROM documents WHERE createdBy = ?";
        List<Document> list = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents créés par " + userId, e);
        }
        return list;
    }

    @Override
    public List<Document> findSubDocuments(UUID parentId) {
        String sql = "SELECT * FROM documents WHERE parentID = ?";
        List<Document> list = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(documentTemplate(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des sous-documents", e);
        }
        return list;
    }

    private Document documentTemplate(ResultSet rs) throws SQLException {
        Document doc = new Document();
        doc.setId((UUID) rs.getObject("id"));
        doc.setDocName(rs.getString("docName"));
        doc.setDocFormat(rs.getString("docFormat"));
        doc.setStorageType(rs.getString("storageType"));
        doc.setStoragePath(rs.getString("storagePath"));
        doc.setSensitive(rs.getBoolean("isSensitive"));
        doc.setParentID((UUID) rs.getObject("parentID"));
        doc.setCreatedBy((UUID) rs.getObject("createdBy"));
        doc.setUpdatedBy((UUID) rs.getObject("updatedBy"));
        doc.setTypeId((UUID) rs.getObject("typeId"));

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) doc.setCreatedAt(createdTs.toInstant());

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) doc.setUpdatedAt(updatedTs.toInstant());

        return doc;
    }
}
