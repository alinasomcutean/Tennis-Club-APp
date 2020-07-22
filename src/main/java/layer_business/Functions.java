package layer_business;

import dto.AdminDTO;
import dto.UserDTO;
import layer_data_access.repo.AdminRepo;
import layer_data_access.repo.UserRepo;
import model.Admin;
import model.User;
import layer_data_access.repo.GenericRepo;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    // Finds if the person logged in is an user
    public UserDTO userLoginPlayer(String mail, String password) {

        User user = UserRepo.findUserByMail(mail);
        if(user != null) {
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }
        else{
            return null;
        }
    }

    // Finds if the person logged in is an admin
    public AdminDTO adminLoginPlayer(String mail, String password) {

        Admin admin = AdminRepo.findAdminByMail(mail);
        if(admin != null) {
            AdminDTO adminDTO = new AdminDTO(admin);
            return adminDTO;
        }
        else{
            return null;
        }
    }

    public List<UserDTO> viewAllPlayers(){
        List<User> userList = UserRepo.findAllUsers();
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();

        if(userList != null){
            for(int i = 0; i < userList.size(); i++){
                UserDTO user = new UserDTO(userList.get(i));
                userDTOList.add(user);
            }
        }

        return userDTOList;
    }

    //Find an user after id
    public UserDTO findById(int id){
        return new UserDTO(UserRepo.findUserById(id));
    }

    //Find an user after email
    public UserDTO findByEmail(String mail){
        return new UserDTO(UserRepo.findUserByMail(mail));
    }

    public void deletePlayer(int id){
        UserRepo.deleteUser(id);
    }

    public void updatePlayer(int id, String mail, String name, String password){
        UserRepo.updateUser(id, mail, name, password);
    }

    public UserDTO insertPlayer(String mail, String name, String password){
        User user = User.builder().mail(mail).name(name).password(password).build();
        GenericRepo.save(user);
        return new UserDTO(user);
    }

}
