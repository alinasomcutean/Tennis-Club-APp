package layer_presentation.Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dto.TennisGameDTO;
import dto.TennisMatchDTO;
import dto.TennisSetDTO;
import dto.UserDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import layer_business.TennisService;
import layer_presentation.util.AlertBox;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControllerUser {

    @FXML private TableView<TennisSetDTO> tableViewSets;
    @FXML private TableColumn<TennisSetDTO, String> setId;
    @FXML private TableColumn<TennisSetDTO, String> match;

    @FXML private TableView<TennisMatchDTO> tableViewMatches;
    @FXML private TableColumn<TennisMatchDTO, String> matchId;
    @FXML private TableColumn<TennisMatchDTO, String> player1;
    @FXML private TableColumn<TennisMatchDTO, String> player2;

    @FXML private TableView<TennisGameDTO> tableViewGames;
    @FXML private TableColumn<TennisGameDTO, String> gameId;
    @FXML private TableColumn<TennisGameDTO, String> player1Score;
    @FXML private TableColumn<TennisGameDTO, String> player2Score;
    @FXML private TableColumn<TennisGameDTO, String> setID;

    @FXML private TextField updatePlayer1Score;
    @FXML private TextField updatePlayer2Score;

    private UserDTO currentUser;

    public void viewSets() {
        TennisService tennisService = new TennisService();
        List<TennisSetDTO> tennisSetDTO = tennisService.viewTennisSets();

        if(tennisSetDTO != null){
            ObservableList<TennisSetDTO> tennisSetObservableList = FXCollections.observableArrayList(tennisSetDTO);
            tableViewSets.setItems(tennisSetObservableList);

            setId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
            match.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTennisMatch().getId()).asString());
        }
    }

    @FXML
    public void viewMatches() {
        TennisService tennisService = new TennisService();
        List<TennisMatchDTO> tennisMatchDTO = tennisService.viewTennisMatches();

        if(tennisMatchDTO != null) {
            ObservableList<TennisMatchDTO> tennisMatchObservableList = FXCollections.observableArrayList(tennisMatchDTO);
            tableViewMatches.setItems(tennisMatchObservableList);

            matchId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
            player1.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlayer1().getName()).asString());
            player2.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlayer2().getName()).asString());
        }
    }

    @FXML
    public void viewGames() {
        TennisService tennisService = new TennisService();
        List<TennisGameDTO> tennisGameDTOList = tennisService.viewTennisGames();

        if(tennisGameDTOList != null){
            ObservableList<TennisGameDTO> tennisGameDTOObservableList = FXCollections.observableArrayList(tennisGameDTOList);
            tableViewGames.setItems(tennisGameDTOObservableList);

            gameId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
            player1Score.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getP1Score()).asString());
            player2Score.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getP2Score()).asString());
            setID.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTennisSet().getId()).asString());
        }
    }

    //Find the current logged in user
    public void getUserFromControllerLogin(UserDTO userDTO){
        currentUser = userDTO;
    }

    //Method which checks, for a game for which the user wants to change the score, if the user is one of the players.
    //If yes, update the game score.
    //Otherwise error.
    private boolean checkUser(UserDTO userDTO, TennisGameDTO tennisGameDTO){
        TennisService tennisService = new TennisService();
        TennisSetDTO tennisSetDTO = tennisService.findSetById(tennisGameDTO.getTennisSet().getId());
        TennisMatchDTO tennisMatchDTO = tennisService.findMatchById(tennisSetDTO.getTennisMatch().getId());
        if(tennisMatchDTO.getPlayer1().getName().equals(userDTO.getName()) || tennisMatchDTO.getPlayer2().getName().equals(userDTO.getName())){
            return true;
        }
        else{
            return false;
        }
    }

    public void updateScore() {
        TennisGameDTO selectedGame = tableViewGames.getSelectionModel().getSelectedItem();
        boolean userOk = checkUser(currentUser, selectedGame);
        if(userOk == false){
            AlertBox alert = new AlertBox();
            alert.display("Stop!", "You cannot change the score for this game because you are not one of the players!");
        }
        else{
            TennisService tennisService = new TennisService();
            tennisService.updateGame(selectedGame.getId(), updatePlayer1Score.getText(), updatePlayer2Score.getText());
        }
    }

    private int gameEnd(TennisGameDTO game){
        int p1 = Integer.parseInt(game.getP1Score());
        int p2 = Integer.parseInt(game.getP2Score());
        if(p1 >= 4  && Math.abs(p1-p2) >= 2){
            return 1; // first player won the game
        }
        else{
            if(p2 >= 4 && Math.abs(p1-p2) >= 2){
                return 2; // second player won the game
            }
            else{
                return 0; // nobody won the game yet
            }
        }
    }

    private List<Integer> setScore(TennisSetDTO set){
        int p1 = 0, p2 = 0; //p1 score for the first player, p2 score for the second player
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < set.getGames().size(); i++){
            TennisGameDTO game = new TennisGameDTO(set.getGames().get(i));
            int x = gameEnd(game);
            if(x == 1){
                p1++;
            }
            if(x == 2){
                p2++;
            }
        }

        result.add(p1);
        result.add(p2);

        return result;
    }

    public void detectSetEnd() {
        TennisSetDTO selectedSet = tableViewSets.getSelectionModel().getSelectedItem();
        List<Integer> setScore = setScore(selectedSet);
        int p1Score = setScore.get(0);
        int p2Score = setScore.get(1);
        AlertBox alert = new AlertBox();

        if(p1Score == 7 || p2Score == 7){
            alert.display("Set end", "Set number " + selectedSet.getId() + " from match " + selectedSet.getTennisMatch().getId() + " has ended with score " + p1Score + " - " + p2Score);
        }
        else{
            if(p1Score == 6 || p2Score == 6){
                alert.display("Set end", "Set number " + selectedSet.getId() + " from match " + selectedSet.getTennisMatch().getId() + " has ended with score " + p1Score + " - " + p2Score);
            }
            else{
                if(p1Score == 5 || p2Score == 5){
                    alert.display("Set end", "Set number " + selectedSet.getId() + " from match " + selectedSet.getTennisMatch().getId() + " has ended with score " + p1Score + " - " + p2Score);
                }
                else{
                    alert.display("Set not done", "Set number " + selectedSet.getId() + " from match " + selectedSet.getTennisMatch().getId() + " is not done yet. Curent score is " + p1Score + " - " + p2Score);
                }
            }
        }
    }

    private List<Integer> matchScore(TennisMatchDTO match){
        int p1 = 0, p2 = 0; //p1 score for the first player, p2 score for the second player
        List<Integer> result = new ArrayList<Integer>();

        for(int i = 0; i < match.getSets().size(); i++){
            TennisSetDTO set = new TennisSetDTO(match.getSets().get(i));
            List<Integer> setScore = setScore(set);
            if(setScore.get(0) > setScore.get(1)){
                p1++;
            }
            else{
                p2++;
            }
        }

        result.add(p1);
        result.add(p2);

        return result;
    }

    public void detectMatchEnd() {
        TennisMatchDTO selectedMatch = tableViewMatches.getSelectionModel().getSelectedItem();
        List<Integer> matchScore = matchScore(selectedMatch);
        int p1Score = matchScore.get(0);
        int p2Score = matchScore.get(1);
        AlertBox alert = new AlertBox();

        if(p1Score == 3){
            alert.display("Match done", "Match number " + selectedMatch.getId() + " is done. The winner is " + selectedMatch.getPlayer1().getName() + ". Final score is " + p1Score + " - " + p2Score);
        }
        else {
            if (p2Score == 3) {
                alert.display("Match done", "Match number " + selectedMatch.getId() + " is done. The winner is " + selectedMatch.getPlayer2().getName() + ". Final score is " + p1Score + " - " + p2Score);
            }
            else{
                alert.display("Match not done", "Match number " + selectedMatch.getId() + " is not done yet. Curent score is " + p1Score + " - " + p2Score);
            }
        }
    }

    private static void addEmptyLineToReport(Paragraph paragraph, int nr) {
        for(int i = 0; i < nr; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void documentContent(Document document, TennisMatchDTO matchDTO) throws DocumentException {
        List<Integer> matchScore = matchScore(matchDTO);
        TennisService tennisService = new TennisService();

        document.add(new Paragraph("Match number " + matchDTO.getId() + " has the score " + matchScore.get(0) + " - " + matchScore.get(1)));
        Paragraph line = new Paragraph();
        addEmptyLineToReport(line, 2);

        List<TennisSetDTO> sets = tennisService.findSetsFromMatch(matchDTO.getId());
        Paragraph tab = new Paragraph();

        for(int i = 0; i < sets.size(); i++){
            List<Integer> setScore = setScore(sets.get(i));
            document.add(new Paragraph("Set number " + sets.get(i).getId() + " finished with score " + setScore.get(0) + " - " + setScore.get(1)));
            addEmptyLineToReport(line, 1);

            List<TennisGameDTO> games = tennisService.findGamesFromSet(sets.get(i).getId());
            for(int j = 0; j < games.size(); j++){
                document.add(new Paragraph("Game number " + games.get(j).getId() + " finished with score " + games.get(j).getP1Score() + " - " + games.get(j).getP2Score()));
                addEmptyLineToReport(line, 1);
            }
        }
    }

    public void generatePDF() {
        String file = "E:/Facultate/Anul III/Sem II/Software Design/Assignment 1/Report";
        TennisMatchDTO selectedMatch = tableViewMatches.getSelectionModel().getSelectedItem();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file + selectedMatch.getId() + ".pdf"));
            document.open();
            documentContent(document, selectedMatch);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
