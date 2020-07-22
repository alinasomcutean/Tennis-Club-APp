package layer_business;

import dto.TennisGameDTO;
import dto.TennisMatchDTO;
import dto.TennisSetDTO;
import dto.UserDTO;
import model.TennisGame;
import model.TennisSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TennisServiceTest {
    TennisService tennisService = new TennisService();
    Functions f = new Functions();

    @Test
    void createNewMatch() {
        UserDTO user1 = f.insertPlayer("u1", "u1", "u1");
        UserDTO user2 = f.insertPlayer("u2", "u2", "u2");
        TennisMatchDTO match = tennisService.createMatch(user1, user2);
        assertNotNull(match);
        assertEquals(user1, match.getPlayer1());
        assertEquals(user2, match.getPlayer2());
    }

    @Test
    void viewTennisMatches() {
        UserDTO user1 = f.insertPlayer("u1", "u1", "u1");
        UserDTO user2 = f.insertPlayer("u2", "u2", "u2");
        UserDTO user3 = f.insertPlayer("u3", "u3", "u3");
        UserDTO user4 = f.insertPlayer("u4", "u4", "u4");
        TennisMatchDTO match1 = tennisService.createMatch(user1, user2);
        TennisMatchDTO match2 = tennisService.createMatch(user3, user4);
        List<TennisMatchDTO> matchDTOList = new ArrayList<TennisMatchDTO>();
        matchDTOList.add(match1);
        matchDTOList.add(match2);
        List<TennisMatchDTO> findMatches = tennisService.viewTennisMatches();
        assertNotNull(findMatches);
        assertEquals(findMatches, matchDTOList);
    }

    @Test
    void findMatchById() {
        UserDTO user1 = f.insertPlayer("u1", "u1", "u1");
        UserDTO user2 = f.insertPlayer("u2", "u2", "u2");
        TennisMatchDTO match = tennisService.createMatch(user1, user2);
        TennisMatchDTO findMatch = tennisService.findMatchById(match.getId());
        assertNotNull(findMatch);
        assertEquals(findMatch, match);
    }

    @Test
    void findSetById() {
        TennisSet tennisSet = new TennisSet();
        TennisSetDTO set = new TennisSetDTO(tennisSet);
        TennisSetDTO findSet = tennisService.findSetById(set.getId());
        assertNotNull(findSet);
        assertEquals(findSet, set);
    }

    @Test
    void createMatch() {
        UserDTO user1 = f.insertPlayer("u1", "u1", "u1");
        UserDTO user2 = f.insertPlayer("u2", "u2", "u2");
        TennisMatchDTO match = tennisService.createMatch(user1, user2);
        assertNotNull(match);
        assertEquals(user1, match.getPlayer1());
        assertEquals(user2, match.getPlayer2());
    }

    @Test
    void deleteMatch() {
        UserDTO user1 = f.insertPlayer("u1", "u1", "u1");
        UserDTO user2 = f.insertPlayer("u2", "u2", "u2");
        TennisMatchDTO match = tennisService.createMatch(user1, user2);
        tennisService.deleteMatch(match.getId());
        TennisMatchDTO findMatch = tennisService.findMatchById(match.getId());
        assertNull(findMatch);
    }

    @Test
    void updateMatch() {
        UserDTO user1 = f.insertPlayer("u1", "u1", "u1");
        UserDTO user2 = f.insertPlayer("u2", "u2", "u2");
        UserDTO user3 = f.insertPlayer("u3", "u3", "u3");
        UserDTO user4 = f.insertPlayer("u4", "u4", "u4");
        TennisMatchDTO match = tennisService.createMatch(user1, user2);
        tennisService.updateMatch(match.getId(), user3.getId(), user4.getId());
        assertEquals(user3, match.getPlayer1());
        assertEquals(user4, match.getPlayer2());
    }
}