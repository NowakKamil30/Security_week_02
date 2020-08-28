package pl.akademiaspring.security_week_01.models;

import javax.persistence.*;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean isTokenForAdmin;
    @ManyToOne
    private AppUser appUser;

    public VerificationToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public boolean isTokenForAdmin() {
        return isTokenForAdmin;
    }

    public void setTokenForAdmin(boolean tokenForAdmin) {
        isTokenForAdmin = tokenForAdmin;
    }
}
