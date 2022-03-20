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
@Table(name = "tickets")
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long user_id;

    @ManyToOne(targetEntity = Subroutes.class)
    @JoinColumn(name = "sub_id", referencedColumnName = "id")
    private Long sub_id;

    @Column(name = "seats")
    private Long seats;

    @Column(name = "price_rub")
    private Double price;
}
