package entities;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "stations_of_route")
public class StationsOfRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strt_id")
    private Long id;

    @ManyToOne(targetEntity = Routes.class)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Long route_id;

    @ManyToOne(targetEntity = Stations.class)
    @JoinColumn(name = "st_id", referencedColumnName = "id")
    private Long st_id;

    @Column(name = "arrival_time")
    @Temporal(value= TemporalType.TIMESTAMP)
    private LocalDateTime arrival;

    @Column(name = "dep_time")
    @Temporal(value= TemporalType.TIMESTAMP)
    private LocalDateTime dep;

    @Column(name = "st_index")
    private Long st_index;
}
