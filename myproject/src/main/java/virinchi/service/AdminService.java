package virinchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virinchi.model.UserTable;
import virinchi.repository.UserRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;


    // GET ALL USERS
    public List<UserTable> getAllUsers() {
        return userRepository.findAll();
    }


    // ACTIVATE / DEACTIVATE USER
    public UserTable changeUserStatus(int id, boolean active) {

        UserTable user =
                userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setActive(active);

        return userRepository.save(user);
    }


    // DELETE USER
    public boolean deleteUser(int id) {

        UserTable user =
                userRepository.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        // Do not allow admin account deletion
        if ("admin".equalsIgnoreCase(user.getRole())) {
            return false;
        }

        userRepository.deleteById(id);

        return true;
    }
}