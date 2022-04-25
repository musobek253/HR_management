package uz.muso.hrmanagement.entitiy;


import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.muso.hrmanagement.entitiy.baseEntity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)// auditing qilish uchun ishlatiladi
@Entity(name = "users")
public class User extends AbstractEntity implements UserDetails{

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String password;

    @Email(message = "Emailni to'g'ri kiriting")
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;// bu yerda userning rolari yani director va director ham xodim

    private String verifyCode;// emayilga tasdiqlash uchun yuboriladigan code

    private String position;//yani bu lavozimi

    private boolean accountNonExpired = true;//hisob muddati tugamaganmi
    private boolean accountNonLocked = true;//hisob blocklanmaganmi
    private boolean credentialsNonExpired = true;//Hisob maʼlumotlari muddati tugamaganmi
    private boolean enabled = false; //hisob faolmi
// bu  UserDetails interfesining filtlarini  implment qilip bolmoqda UserDetails fodyalnganimiz sababi spring security user sifatida tanishi uchun
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String password, @Email String email, Set<Role> roles, String position, boolean enabled) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.position = position;
        this.enabled = enabled;
    }


}
