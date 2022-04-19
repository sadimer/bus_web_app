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
@Table(name = "tickets")
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne(targetEntity = Subroutes.class)
    @JoinColumn(name = "sub_id", referencedColumnName = "sub_id")
    private Subroutes sub;

    @Column(name = "seats")
    private Long seats;

    @Column(name = "price_rub")
    private Double price;
}
