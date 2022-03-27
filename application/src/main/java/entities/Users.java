package entities;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@FilterDef(name = "nameFilter", parameters = @ParamDef(name = "nameParam", type = "java.lang.String"))
@Filter(name = "nameFilter", condition = "user_name like :nameParam")

@FilterDef(name = "loginFilter", parameters = @ParamDef(name = "loginParam", type = "java.lang.String"))
@Filter(name = "loginFilter", condition = "user_login like :loginParam")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_passwd")
    private String passwd;

    @Column(name = "is_admin")
    private Boolean admin;

    @Column(name = "user_login")
    private String login;
}
