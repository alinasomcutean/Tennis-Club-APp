package layer_business;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import dto.TennisGameDTO;
import dto.TennisMatchDTO;
import dto.TennisSetDTO;
import dto.UserDTO;
import layer_data_access.repo.GenericRepo;
import layer_data_access.repo.TennisRepo;
import layer_data_access.repo.UserRepo;
import model.TennisGame;
import model.TennisMatch;
import model.TennisSet;
import model.User;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TennisService {

    public TennisMatch createNewMatch(User player1, User player2) {
        TennisSet tennisSet = new TennisSet();
        tennisSet.setGames(Collections.singletonList(getEmptyTennisGame()));

        List<TennisSet> sets = new ArrayList<>();
        sets.add(tennisSet);

        TennisMatch tennisMatch = new TennisMatch();
        tennisMatch.setPlayer1(player1);
        tennisMatch.setPlayer2(player2);

        tennisMatch.setSets(sets);
        return tennisMatch;
    }

    private TennisGame getEmptyTennisGame() {
        TennisGame tennisGame = new TennisGame();
        tennisGame.setP1Score("0");
        tennisGame.setP2Score("0");
        return tennisGame;
    }

    public List<TennisMatchDTO> viewTennisMatches(){
        List<TennisMatch> tennisMatchList = TennisRepo.findAllMatches();
        List<TennisMatchDTO> tennisMatchDTO = new ArrayList<TennisMatchDTO>();

        if(tennisMatchList != null) {
            for (int i = 0; i < tennisMatchList.size(); i++) {
                TennisMatchDTO dto = new TennisMatchDTO(tennisMatchList.get(i));
                tennisMatchDTO.add(dto);
            }
        }

        return tennisMatchDTO;
    }

    public List<TennisSetDTO> viewTennisSets(){
        List<TennisSet> tennisSetList = TennisRepo.findAllSets();
        List<TennisSetDTO> tennisSetDTO = new ArrayList<TennisSetDTO>();

        if(tennisSetList != null){
            for(int i = 0; i < tennisSetList.size(); i++){
                TennisSetDTO dto = new TennisSetDTO(tennisSetList.get(i));
                tennisSetDTO.add(dto);
            }
        }

        return tennisSetDTO;
    }

    public List<TennisGameDTO> viewTennisGames(){
        List<TennisGame> tennisGameList = TennisRepo.findAllGames();
        List<TennisGameDTO> tennisGameDTOList = new ArrayList<TennisGameDTO>();

        if(tennisGameList != null){
            for(int i = 0; i < tennisGameList.size(); i++){
                TennisGameDTO dto = new TennisGameDTO(tennisGameList.get(i));
                tennisGameDTOList.add(dto);
            }
        }

        return tennisGameDTOList;
    }

    public TennisMatchDTO findMatchById(int id){
        return new TennisMatchDTO(TennisRepo.findMatchById(id));
    }

    public TennisSetDTO findSetById(int id){
        return new TennisSetDTO(TennisRepo.findSetById(id));
    }

    public List<TennisGameDTO> findGamesFromSet(int id){
        TennisSet tennisSet = TennisRepo.findSetById(id);
        List<TennisGame> tennisGameList = TennisRepo.findGamesFromSet(tennisSet);
        List<TennisGameDTO> tennisGameDTOList = new ArrayList<TennisGameDTO>();

        if(tennisGameList != null){
            for(int i = 0; i < tennisGameList.size(); i++){
                TennisGameDTO dto = new TennisGameDTO(tennisGameList.get(i));
                tennisGameDTOList.add(dto);
            }
        }

        return tennisGameDTOList;
    }

    public List<TennisSetDTO> findSetsFromMatch(int id){
        TennisMatch tennisMatch = TennisRepo.findMatchById(id);
        List<TennisSet> tennisSetList = TennisRepo.findSetsFromMatch(tennisMatch);
        List<TennisSetDTO> tennisSetDTOList = new ArrayList<TennisSetDTO>();

        if(tennisSetList != null){
            for(int i = 0; i < tennisSetList.size(); i++){
                TennisSetDTO dto = new TennisSetDTO(tennisSetList.get(i));
                tennisSetDTOList.add(dto);
            }
        }

        return tennisSetDTOList;
    }

    public TennisMatchDTO createMatch(UserDTO player1, UserDTO player2){
        User user1 = UserRepo.findUserById(player1.getId());
        User user2 = UserRepo.findUserById(player2.getId());
        TennisMatch tennisMatch = TennisMatch.builder().player1(user1).player2(user2).build();
        GenericRepo.save(tennisMatch);
        return new TennisMatchDTO(tennisMatch);
    }

    public void deleteMatch(int id){
        TennisRepo.deleteMatch(id);
    }

    public void updateMatch(int idMatch, int idPlayer1, int idPlayer2){
        User player1 = UserRepo.findUserById(idPlayer1);
        User player2 = UserRepo.findUserById(idPlayer2);
        TennisRepo.updateMatch(idMatch, player1, player2);
    }

    public void updateGame(int id, String p1Score, String p2Score){
        TennisRepo.updateGameScore(id, p1Score, p2Score);
    }

}
