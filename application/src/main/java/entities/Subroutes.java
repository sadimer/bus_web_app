package entities;
import lombok.*;
import javax.persistence.*;
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
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Routes route;

    @OneToOne(targetEntity = Stations.class)
    @JoinColumn(name = "arrival_st_id", referencedColumnName = "st_id")
    private Stations arrival_st;

    @OneToOne(targetEntity = Stations.class)
    @JoinColumn(name = "depart_st_id", referencedColumnName = "st_id")
    private Stations depart_st;

    @Override
    public boolean equals(Object obj) {
        final Subroutes other = (Subroutes) obj;
        return this.id == other.id;
    }
}
