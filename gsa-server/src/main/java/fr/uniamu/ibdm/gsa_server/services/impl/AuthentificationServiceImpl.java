package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.services.AuthentificationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class AuthentificationServiceImpl implements AuthentificationService {
    @Override
    public User login(String email, byte password) {
        return null;
    }

    @Override
    public User register(Map<String, String> userInfo) {
        return null;
    }
}
