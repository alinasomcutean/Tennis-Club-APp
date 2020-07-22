package layer_business;

import dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsTest {
    Functions f = new Functions();

    @org.junit.jupiter.api.Test
    void viewAllPlayers() {
        UserDTO user1 = f.insertPlayer("test1", "test1", "test1");
        UserDTO user2 = f.insertPlayer("test2", "test2", "test2");
        UserDTO user3 = f.insertPlayer("test3", "test3", "test3");
        List<UserDTO> users = new ArrayList<UserDTO>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        List<UserDTO> usersFind = f.viewAllPlayers();
        assertNotNull(usersFind);
        assertEquals(usersFind, users);
    }

    @org.junit.jupiter.api.Test
    void findById() {
        UserDTO user = f.insertPlayer("test1", "test1", "test1");
        UserDTO userFind = f.findById(user.getId());
        assertNotNull(userFind);
        assertEquals(userFind, user);
    }

    @org.junit.jupiter.api.Test
    void findByEmail() {
        UserDTO user = f.insertPlayer("test1", "test1", "test1");
        UserDTO userFind = f.findByEmail("test1");
        assertNotNull(userFind);
        assertEquals(userFind, user);
    }

    @org.junit.jupiter.api.Test
    void deletePlayer() {
        UserDTO user = f.insertPlayer("test1", "test1", "test1");
        f.deletePlayer(user.getId());
        UserDTO userFind = f.findByEmail("test1");
        assertNull(userFind);
    }

    @org.junit.jupiter.api.Test
    void updatePlayer() {
        UserDTO user = f.insertPlayer("test1", "test1", "test1");
        f.updatePlayer(user.getId(), "test2", "test2", "test2");
        assertNotNull(user);
        assertEquals(user.getMail(), "test2");
        assertEquals(user.getName(), "test2");
        assertEquals(user.getPassword(), "test2");
    }

    @org.junit.jupiter.api.Test
    void insertPlayer() {
        UserDTO user = f.insertPlayer("test1", "test1", "test1");
        assertNotNull(user);
        assertEquals("test1", user.getMail());
        assertEquals("test1", user.getName());
        assertEquals("test1", user.getPassword());
    }
}