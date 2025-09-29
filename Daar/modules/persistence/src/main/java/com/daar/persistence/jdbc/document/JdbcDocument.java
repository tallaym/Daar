package com.daar.persistence.jdbc.document;

import com.daar.core.model.document.Document;
import com.daar.core.port.out.document.DocumentRepository;

import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;

public class JdbcDocument implements DocumentRepository {

    private final Connection cn;

    public JdbcDocument(Connection cn) {
        this.cn = cn;
    }

    @Override
    public Document insert(Document doc) {
        String sql = """
                        INSERT INTO documents (
                            id,
                            docName,
                            docFormat,
                            storageType,
                            storagePath,
                            isSensitive,
                            parentID,
                            createdBy,
                            typeId
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

        try(PreparedStatement ps = cn.prepareStatement(sql) ){
            ps.setObject(1, doc.getId());
            ps.setString(2, doc.getDocName());
            ps.setString(3, doc.getDocFormat());
            ps.setString(4, doc.getStorageType());
            ps.setString(5, doc.getStoragePath());
            ps.setBoolean(6, doc.isSensitive());
            ps.setObject(7, doc.getParentID());
            ps.setObject(8, doc.getCreatedBy());
            ps.setObject(9, doc.getTypeId());

            if(ps.executeUpdate() == 0){
                throw new SQLException("échec lors de l'insertion!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doc;
    }

    @Override
    public Document update(Document doc) {
        String sql = """
                        UPDATE documents (
                            SET docName = ?,
                                docFormat = ?,
                                storageType = ?,
                                storagePath = ?,
                                isSensitive = ?,
                                parentID = ?,
                                updatedAt = ?,
                                updatedBy = ?,
                                typeId = ?
                            WHERE id = ?
                        
                    """;

        try(PreparedStatement ps = cn.prepareStatement(sql)){
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

            if(ps.executeUpdate() == 0){
                throw new SQLException("échec de la modification");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return doc;
    }

    @Override
    public Optional<Document> findById(UUID id) {
        String sql = "SELECT * FROM documents WHERE id = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(documentTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du document avec id " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM documents WHERE id = ?";

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
    public List<Document> findAll() {
        String sql = "SELECT * FROM documents";
        List<Document> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql);
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
    public List<Document> findSubDocuments(UUID parentId) {
        String sql = "SELECT * FROM documents WHERE parentID = ?";
        List<Document> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, parentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents enfants", e);
        }

        return list;
    }



    public List<Document> findAddedAfter(Date start) {
        String sql = "SELECT * FROM documents WHERE createdAt > ?";
        List<Document> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, start != null ? new Timestamp(start.getTime()) : null);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents ajoutés après " + start, e);
        }

        return list;
    }

    @Override
    public List<Document> findAddedBetween(Date start, Date end) {
        String sql = "SELECT * FROM documents WHERE createdAt BETWEEN ? AND ?";
        List<Document> list = new ArrayList<>();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, start != null ? new Timestamp(start.getTime()) : null);
            ps.setTimestamp(2, end != null ? new Timestamp(end.getTime()) : null);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(documentTemplate(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des documents ajoutés entre " + start + " et " + end, e);
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

        Timestamp createdTs = rs.getTimestamp("createdAt");
        if (createdTs != null) {
            doc.setCreatedAt(createdTs.toInstant());
        }

        Timestamp updatedTs = rs.getTimestamp("updatedAt");
        if (updatedTs != null) {
            doc.setUpdatedAt(updatedTs.toInstant());
        }

        doc.setTypeId((UUID) rs.getObject("typeId"));

        return doc;
    }

}
