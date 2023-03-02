package com.example.finalproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
//@Table(name = "myUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "Password must not be empty")
    private String password;
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Please Write correct email")
    private String email;
    @NotEmpty(message = "Phone Number must not be empty")
    @Size(min = 10, max = 10, message = "min 10 and max 10 phone number")
    private String phone;
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "(?:^|\\W)Customer(?:$|\\W)|(?:^|\\W)Admin(?:$|\\W)|(?:^|\\W)Merchant(?:$|\\W)", message = "Role must be customer or merchant or admin ")




    private String role;

    private boolean enable;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private Merchant merchant;

    public MyUser(Object o, String hashem, String s, String s1, String s2, Object o1, String merchant) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
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
        return this.enable;
    }
}
