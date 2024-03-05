package com.return0.dev.backend.service;

import com.return0.dev.backend.entity.Social;
import com.return0.dev.backend.entity.User;
import com.return0.dev.backend.exception.BaseException;
import com.return0.dev.backend.exception.UserException;
import com.return0.dev.backend.repository.SocialRepository;
import com.return0.dev.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SocialService {

    private final SocialRepository repository;
    public SocialService(SocialRepository repository){
        this.repository = repository;
    }

    public Optional<Social> findByUser(User user){
        return repository.findByUser(user);
    }

    public Social create(User user, String facebook, String line, String instagram, String tiktok) {
        Social entity = new Social();

        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setLine(line);
        entity.setInstagram(instagram);
        entity.setTiktok(tiktok);

        return repository.save(entity);
    }
}
