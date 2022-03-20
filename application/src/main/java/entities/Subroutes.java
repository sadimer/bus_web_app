package entities;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "subroutes")
public class Subroutes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_id")
    private Long id;

    @ManyToOne(targetEntity = Routes.class)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Long route_id;

    @OneToOne(targetEntity = Stations.class)
    @JoinColumn(name = "arrival_st_id", referencedColumnName = "id")
    private Long arrival_st_id;

    @OneToOne(targetEntity = Stations.class)
    @JoinColumn(name = "depart_st_id", referencedColumnName = "id")
    private Long depart_st_id;
}
