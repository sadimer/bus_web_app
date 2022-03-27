package entities;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@FilterDef(name = "nameCityFilter", parameters = {@ParamDef(name = "nameParam", type = "java.lang.String"),
        @ParamDef(name = "cityParam", type = "java.lang.String")})
@Filter(name = "nameCityFilter", condition = "st_name like :nameParam and city like :cityParam")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "stations")
public class Stations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "st_id")
    private Long id;

    @Column(name = "st_name")
    private String name;

    @Column(name = "city")
    private String city;
}
