package training.path.academicrecordsystem.security.userdetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AcademicUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username does not exist"));
        return new AcademicUserDetails(user.getUserName(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().getRoleName())));
    }

}
