package dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import model.Admin;
import model.TennisMatch;
import model.TennisSet;
import model.User;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Data
public class TennisMatchDTO {

    int id;

    User player1;

    User player2;

    List<TennisSet> sets;

    public TennisMatchDTO(TennisMatch tennisMatch) {
        this.id = tennisMatch.getId();
        this.player1 = tennisMatch.getPlayer1();
        this.player2 = tennisMatch.getPlayer2();
        this.sets = tennisMatch.getSets();
    }
}
