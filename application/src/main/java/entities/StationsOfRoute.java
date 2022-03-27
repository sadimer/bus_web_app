package entities;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

//чтоб выводить конечную и начальную станцию добавим фильтр по индексу
@FilterDef(name = "indexFilter", parameters = @ParamDef(name = "indexParam", type = "java.lang.Long"))
@Filter(name = "indexFilter", condition = "st_index = :indexParam")

@FilterDef(name = "arrivalFilter", parameters = {@ParamDef(name = "minDate", type = "java.time.LocalDateTime"),
        @ParamDef(name = "maxDate", type = "java.time.LocalDateTime")})
@Filter(name = "arrivalFilter",condition = "arrival_time >= :minDate and arrival_time <= :maxDate")

@FilterDef(name = "departFilter", parameters = {@ParamDef(name = "minDate", type = "java.time.LocalDateTime"),
        @ParamDef(name = "maxDate", type = "java.time.LocalDateTime")})
@Filter(name = "departFilter",condition = "depart_time >= :minDate and depart_time <= :maxDate")

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
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Routes route;

    @ManyToOne(targetEntity = Stations.class)
    @JoinColumn(name = "st_id", referencedColumnName = "st_id")
    private Stations st;

    @Column(name = "arrival_time")
    private LocalDateTime arrival;

    @Column(name = "depart_time")
    private LocalDateTime dep;

    @Column(name = "st_index")
    private Long st_index;
}
