package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//   private List<Topic> topics=new ArrayList<>( Arrays.asList(new Topic("spring","sping framework","descption"),
//                new Topic("spring2","spi1ng framework2","descption2")));

    public List<UserModel> getAllUsers()
    {
        List<UserModel> userModels =new ArrayList<>();
        //user.findAll().forEach(topics::add);
        for(UserModel userModel : userRepository.findAll())
        {
            userModels.add(userModel);
        }
        return userModels;
    }
    public UserModel getUser(String id)
    {
//      return user.findOne(id);
        List<UserModel> userModels = new ArrayList<>();
        userRepository.findAll().forEach(userModels::add);
        for(UserModel model :userModels)
        {
            if(model.getUserName().equals(id)){
                return model;
            }
        }
        return userModels.stream().filter(t-> t.getUserName().equals(id)).findFirst().get();
    }

    public UserModel getUserByFullName(String numePrenume)
    {
//      return user.findOne(id);
        List<UserModel> userModels = new ArrayList<>();
        userRepository.findAll().forEach(userModels::add);
        for(UserModel model :userModels)
        {
            if((model.getNume()+" "+model.getPrenume()).equals(numePrenume)){
                return model;
            }
        }
        return null;
    }
    public void addUser(UserModel userModel){
          userRepository.save(userModel);
    }

    public List<UserModel> findAllByEchipa(String echipa){
       return userRepository.findAllByEchipa(echipa);
    }

    public void updateUser(UserModel userModel, String id) {
        userRepository.save(userModel);
    }

    public void deleteUser(String id) {
        userRepository.delete(id);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // fetch user from e.g. DB
        List<UserModel> allUsers = getAllUsers();
        for(UserModel userModel:allUsers)
        {
            if(userModel.getUserName().equals(username)&&!userModel.getUserName().equals("admin")){
                authorities.add(new SimpleGrantedAuthority("CLIENT"));
                User user = new User(username, userModel.getPassword(), true, true, false, false, authorities);
                return user;
            }
        }
        if("client".equals(username)){
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
            User user = new User(username, "pass", true, true, false, false, authorities);
            return user;
        }
        if("admin".equals(username)){
            UserModel userModel=new UserModel();
            userModel.setUserName("admin");
            userModel.setPassword("admin");
            userModel.setNume("admin");
            userModel.setPrenume("admin");
            userModel.setEchipa("fara echipa");
            userModel.setFunctie("ADMINISTRATOR");
            addUser(userModel);
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            User user = new User(username, "admin", true, true, false, false, authorities);
            return user;
        }else{
            return null;
        }
    }

}
