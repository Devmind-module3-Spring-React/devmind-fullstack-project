package ro.devmind.devmind_fullstack_project.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.devmind.devmind_fullstack_project.model.Role;
import ro.devmind.devmind_fullstack_project.model.User;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {


    private Integer id;
    private String username;
    private String email;
    private String name;
    public Set<Role> roles;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();;
    }

    //TODO: UserDetails methods


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
