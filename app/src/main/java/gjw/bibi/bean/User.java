package gjw.bibi.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 皇上 on 2017/3/27.
 */

@Entity
public class User {
    @Id
    private Long id;
    private String username;
    private String passwrod;
    public String getPasswrod() {
        return this.passwrod;
    }
    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 143815316)
    public User(Long id, String username, String passwrod) {
        this.id = id;
        this.username = username;
        this.passwrod = passwrod;
    }
    @Generated(hash = 586692638)
    public User() {
    }
}
