package entities;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;


@FilterDef(name = "nameFilter", parameters = @ParamDef(name = "nameParam", type = "java.lang.String"))
@Filter(name = "nameFilter", condition = "route_name like :nameParam")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "routes")
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long id;

    @Column(name = "route_name")
    private String name;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;
}
