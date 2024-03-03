package athat.ehubback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.Store;
import athat.ehubback.model.User;
import athat.ehubback.repository.StoreRepository;
import athat.ehubback.repository.UserRepository;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    public Store create(String name, String lineApiKey, String lazadaApiKey,  User user) throws ResponseStatusException {
        if(storeRepository.findByName(name) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store's name already exists");
        }
        if(userRepository.findByUsername(user.getUsername()) == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Don't have username");
        }
        Store store = new Store();
        store.setName(name);
        if(lineApiKey != null){store.setLineApiKey(lineApiKey);}
        if(lazadaApiKey != null){store.setLazadaApiKey(lazadaApiKey);}
        List<User> userList = new ArrayList<>();
        userList.add(user);
        store.setUser(userList);
        storeRepository.save(store);
        System.out.println(store);
        User savedUser = userRepository.findByUsername(user.getUsername());
        savedUser.setStore(store);
        userRepository.save(savedUser);
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
}
