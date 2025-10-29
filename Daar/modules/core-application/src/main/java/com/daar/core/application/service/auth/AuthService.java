package com.daar.core.application.service.auth;

import com.daar.core.domain.model.auth.Credential;
import com.daar.core.domain.model.auth.User;
import com.daar.core.domain.model.auth.permission.UseRole;
import com.daar.core.port.in.dto.credential.UpdateCredentialCommand;
import com.daar.core.port.in.dto.login.LoginRequestDTO;
import com.daar.core.port.in.dto.login.LoginResponseDTO;
import com.daar.core.port.in.dto.login.RefreshTokenRequestDTO;
import com.daar.core.port.in.usecase.auth.AuthUseCase;
import com.daar.core.port.out.auth.CredentialRepository;
import com.daar.core.port.out.auth.JwtRepository;
import com.daar.core.port.out.auth.permission.UseRoleRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class AuthService implements AuthUseCase {

    private final CredentialRepository credRepo ;

    private final UseRoleRepository jobRepo;
    private final JwtRepository jwtRepo;

    public AuthService(CredentialRepository credRepo, UseRoleRepository jobRepo, JwtRepository jwtRepo) {
        this.credRepo = credRepo;
        this.jobRepo = jobRepo;
        this.jwtRepo = jwtRepo;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        // 1. Récupérer credential par identifier
        Credential cr = credRepo.findByIdentifier(request.getIdentifier());

        if (cr == null) {
            throw new RuntimeException("Identifiant invalide");
        }
        // 2. Vérifier mot de passe
        if (!BCrypt.checkpw(request.getPassword(), cr.getSecret())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        // 3. Récupérer userRoleId
        Optional<UseRole> job = jobRepo.findByUserId(cr.getUserId());
        if(job.isEmpty()){
throw new RuntimeException("job not found");
        }
        UUID useRoleId = job.get().getId();
        UUID userId = cr.getUserId();

        // 4. Générer tokens
        String accessToken = jwtRepo.generateToken(userId, useRoleId, JwtRepository.TokenType.ACCESS);
        String refreshToken = jwtRepo.generateToken(userId, useRoleId, JwtRepository.TokenType.REFRESH);

        // 5. Construire la réponse
        return new LoginResponseDTO(useRoleId, accessToken, refreshToken, "Bearer");


    }

    @Override
    public void changePassword(String currentPassword, UpdateCredentialCommand request) {
        Credential cr = credRepo.findByIdentifier(request.getIdentifier());

        if(!BCrypt.checkpw(currentPassword, cr.getSecret())){
            throw new RuntimeException("mdp incorrect");
        }

        String hashedNewPassword = BCrypt.hashpw(request.getSecret(), BCrypt.gensalt());

        cr.setSecret(hashedNewPassword);
        cr.setUpdatedAt(Instant.now()); // si tu as ce champ

        credRepo.update(cr);

    }

    @Override
    public LoginResponseDTO refreshToken(RefreshTokenRequestDTO dto) {
        if (!jwtRepo.validateToken(dto.getRefreshToken())) {
            throw new RuntimeException("Refresh token invalide ou expiré");
        }

        UUID userId = jwtRepo.extractUserId(dto.getRefreshToken());
        UUID userRoleId = jwtRepo.extractUseRoleId(dto.getRefreshToken());

        // Créer un nouveau access token
        String newAccessToken = jwtRepo.generateToken(userId, userRoleId, JwtRepository.TokenType.ACCESS);

        // Retourne la réponse avec le même refreshToken
        return new LoginResponseDTO(userRoleId, newAccessToken, dto.getRefreshToken(), "Bearer");
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        // Vérifie signature et expiration
        return jwtRepo.validateToken(accessToken) && !jwtRepo.isTokenExpired(accessToken);
    }


    @Override
    public User getCurrentUser(String accessToken) {
        return null;
    }
}
