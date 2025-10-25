package com.daar.adapter.out.jdbc.auth.activity;

import com.daar.core.domain.model.auth.activity.RefreshToken;
import com.daar.core.port.out.auth.activity.RefreshTokenRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class JdbcRefreshToken implements RefreshTokenRepository {


private final DataSource ds;

    public JdbcRefreshToken(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public RefreshToken save(RefreshToken token) {
        String sql = "INSERT INTO refreshTokens(token, userId, expiresAt, revoked) VALUES (?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token.getToken());
            ps.setObject(2, token.getUserId());
            ps.setTimestamp(3, Timestamp.from(token.getExpiresAt()));
            ps.setBoolean(4, token.isRevoked());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving refresh token", e);
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String sql = "SELECT * FROM refreshTokens WHERE token = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RefreshToken t = new RefreshToken(
                            (UUID) rs.getObject("userId"),
                            rs.getString("token"),
                            rs.getTimestamp("expires_at").toInstant(),
                            rs.getBoolean("revoked")
                    );
                    return Optional.of(t);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding refresh token", e);
        }
        return Optional.empty();
    }

    @Override
    public void revoke(RefreshToken token) {
        String sql = "UPDATE refreshTokens SET revoked = TRUE WHERE token = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token.getToken());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error revoking refresh token", e);
        }
    }
}
