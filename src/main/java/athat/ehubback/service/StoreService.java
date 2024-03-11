package athat.ehubback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.Role;
import athat.ehubback.model.Store;
import athat.ehubback.model.User;
import athat.ehubback.repository.StoreRepository;
import athat.ehubback.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StoreService {

    private StoreRepository storeRepository;
    private UserRepository userRepository;
    private JwtService jwtService;

    public String create(String storeName, String lineApiKey, String lazadaApiKey, String token) throws ResponseStatusException {
        String username = jwtService.extractUsername(token);

        if(userRepository.findByUsername(username) == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Don't have user");
        }
        if(userRepository.findByUsername(username).getStore() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already have store");
        }
        if(storeRepository.findByName(storeName) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store's name already exists");
        }
        for(Store store: storeRepository.findAll()){
            if(store.getLineApiKey() == lineApiKey)
            {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Api Key already exists ");
            }
        }

        Store store = new Store();
        User user = userRepository.findByUsername(username);
        store.setName(storeName);
        if(lineApiKey != null){store.setLineApiKey(lineApiKey);}
        if(lazadaApiKey != null){store.setLazadaApiKey(lazadaApiKey);}
        List<User> userList = new ArrayList<>();
        userList.add(user);
        store.setUser(userList);
        storeRepository.save(store);
        User savedUser = userRepository.findByUsername(user.getUsername());
        savedUser.setStore(store);
        savedUser.setRole(Role.OWNER);
        userRepository.save(savedUser);

        String newToken = jwtService.generateToken(user);

        return newToken;
    }

    public Store addUserToStore(String token, String storeName) throws ResponseStatusException {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);
        Store store = storeRepository.findByName(storeName);
        if(store == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(user.getStore() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already have store");
        }
        List<User> userList = store.getUser();
        userList.add(user);
        store.setUser(userList);
        storeRepository.save(store);
        user.setStore(store);
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);
        return store;
    }

    public Store update(String name, String lineApiKey, String lazadaApiKey,  User user)throws ResponseStatusException {
        Store store = storeRepository.findByName(name);
        store.setName(name);
        if(lineApiKey != null){store.setLineApiKey(lineApiKey);}
        if(lazadaApiKey != null){store.setLazadaApiKey(lazadaApiKey);}
        storeRepository.save(store);
        return store;
    }

    public Store getStore(String token){
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);
        Store store = user.getStore();
        return store;
    }
}
