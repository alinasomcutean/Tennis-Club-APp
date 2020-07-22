package dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import model.TennisGame;
import model.TennisSet;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Data
public class TennisGameDTO {

    private int id;

    private String p1Score;

    private String p2Score;

    private TennisSet tennisSet;

    public TennisGameDTO(TennisGame tennisGame){
        this.id = tennisGame.getId();
        this.p1Score = tennisGame.getP1Score();
        this.p2Score = tennisGame.getP2Score();
        this.tennisSet = tennisGame.getTennisSet();
    }
}
