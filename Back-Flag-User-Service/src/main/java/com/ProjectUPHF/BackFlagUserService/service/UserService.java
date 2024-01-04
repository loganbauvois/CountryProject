package com.ProjectUPHF.BackFlagUserService.service;

import com.ProjectUPHF.BackFlagUserService.api.request.UserCreationRequest;
import com.ProjectUPHF.BackFlagUserService.entity.User;
import com.ProjectUPHF.BackFlagUserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User create(UserCreationRequest request) {
        User users = userRepository.findByUsername(request.getUsername());
        if(users == null){
            final User user = User.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .score(0)
                    .build();
            return userRepository.insert(user);
        }else{
            return null;
        }

    }

    public User update(UserCreationRequest request, String username) {
        User userToUpdate = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe
        if (userToUpdate != null) {
            // Mettre à jour les champs de l'utilisateur avec les valeurs de la requête
            userToUpdate.setUsername(request.getUsername());
            userToUpdate.setPassword(request.getPassword());

            // Enregistrer l'utilisateur mis à jour dans la base de données
            return userRepository.save(userToUpdate);
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            // Par exemple, vous pouvez lever une exception ou retourner null
            return null;
        }

    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserByName(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void updateScore(String username, Integer score) {
        final User u = userRepository.findByUsername(username);
        if (u == null) return ;
        score += 1;
        if (score > u.getScore()){
            u.setScore(score);
            userRepository.save(u);
        }
    }

    public List<User> getClassement() {
        List<User> list = userRepository.findTop10ByOrderByScoreDesc();
        list.forEach(x -> x.setPassword(null));
        System.out.println(list);
        return list;

    }
}
