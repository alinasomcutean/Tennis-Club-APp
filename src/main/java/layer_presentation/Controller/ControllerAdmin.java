package layer_presentation.Controller;

import dto.TennisMatchDTO;
import dto.UserDTO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import layer_business.Functions;
import layer_business.TennisService;

import java.util.List;

public class ControllerAdmin {

    @FXML private TableView<UserDTO> tableViewPlayers;
    @FXML private TableColumn<UserDTO, String> playerId;
    @FXML private TableColumn<UserDTO, String> playerMail;
    @FXML private TableColumn<UserDTO, String> playerName;
    @FXML private TableColumn<UserDTO, String> playerPassword;

    @FXML private TableView<TennisMatchDTO> tableViewMatches;
    @FXML private TableColumn<TennisMatchDTO, String> matchId;
    @FXML private TableColumn<TennisMatchDTO, String> player1;
    @FXML private TableColumn<TennisMatchDTO, String> player2;

    //Fields for creating a new user
    @FXML
    private TextField newMail;
    @FXML
    private TextField newName;
    @FXML
    private TextField newPassword;

    //Fields for updating a user
    @FXML
    private TextField mail;
    @FXML
    private TextField name;
    @FXML
    private TextField password;

    //Used for adding a player to a match
    private UserDTO newPlayer1;
    private UserDTO newPlayer2;

    //Lists with all the players and matches from database
    ObservableList<UserDTO> playerList;
    ObservableList<TennisMatchDTO> matchList;

    //Fields for updating a match
    @FXML
    private TextField updatePlayer1;
    @FXML
    private TextField updatePlayer2;

    @FXML
    public void viewPlayers() {
        Functions f = new Functions();
        List<UserDTO> userDTOList = f.viewAllPlayers();

        if(userDTOList != null){
            //ObservableList<UserDTO> observableList = FXCollections.observableArrayList(userDTOList);
            playerList = FXCollections.observableArrayList(userDTOList);
            tableViewPlayers.setItems(playerList);

            playerId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
            playerMail.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMail()).asString());
            playerName.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getName()).asString());
            playerPassword.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPassword()).asString());
        }
    }

    @FXML
    public void viewMatches() {
        TennisService tennisService = new TennisService();
        List<TennisMatchDTO> tennisMatchDTO = tennisService.viewTennisMatches();

        if(tennisMatchDTO != null) {
            //ObservableList<TennisMatchDTO> tennisMatchObservableList = FXCollections.observableArrayList(tennisMatchDTO);
            matchList = FXCollections.observableArrayList(tennisMatchDTO);
            tableViewMatches.setItems(matchList);

            matchId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
            player1.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlayer1().getName()).asString());
            player2.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPlayer2().getName()).asString());
        }
    }

    @FXML
    public void createPlayer() {
        Functions f = new Functions();
        UserDTO user = f.insertPlayer(newMail.getText(), newName.getText(), newPassword.getText());
        playerList.add(user);
        tableViewPlayers.refresh();
    }

    @FXML
    public void updatePlayer() {
        UserDTO selectedRow = tableViewPlayers.getSelectionModel().getSelectedItem();
        Functions f = new Functions();

        f.updatePlayer(selectedRow.getId(), mail.getText(), name.getText(), password.getText());
        tableViewPlayers.refresh();
        playerList.remove(selectedRow);
        playerList.add(f.findById(selectedRow.getId()));
        tableViewPlayers.refresh();
    }

    @FXML
    public void deletePlayer() {
        UserDTO selectedRow =  tableViewPlayers.getSelectionModel().getSelectedItem();
        Functions f = new Functions();

        f.deletePlayer(selectedRow.getId());
        playerList.remove(selectedRow);
        tableViewPlayers.refresh();
    }

    @FXML
    public void deleteMatch() {
        TennisMatchDTO selectedRow = tableViewMatches.getSelectionModel().getSelectedItem();
        TennisService tennisService = new TennisService();
        tennisService.deleteMatch(selectedRow.getId());
        matchList.remove(selectedRow);
        tableViewMatches.refresh();
    }

    @FXML
    public void createMatch() {
        TennisService tennisService = new TennisService();
        TennisMatchDTO tennisMatchDTO = tennisService.createMatch(newPlayer1, newPlayer2);
        matchList.add(tennisMatchDTO);
        tableViewMatches.refresh();
    }

    //First player for a new match
    @FXML
    private void addPlayer1ToMatch() {
        newPlayer1 = tableViewPlayers.getSelectionModel().getSelectedItem();
    }

    //Second player for a new match
    @FXML
    private void addPlayer2ToMatch() {
        newPlayer2 =  tableViewPlayers.getSelectionModel().getSelectedItem();
    }

    public void updateMatch() {
        TennisService tennisService = new TennisService();
        TennisMatchDTO selectedRow = tableViewMatches.getSelectionModel().getSelectedItem();

        int idPlayer1 = Integer.parseInt(updatePlayer1.getText());
        int idPlayer2 = Integer.parseInt(updatePlayer2.getText());

        tennisService.updateMatch(selectedRow.getId(), idPlayer1, idPlayer2);
        matchList.remove(selectedRow);
        TennisMatchDTO tennisMatchDTO = tennisService.findMatchById(selectedRow.getId());
        matchList.add(tennisMatchDTO);
        tableViewMatches.refresh();
    }
}
