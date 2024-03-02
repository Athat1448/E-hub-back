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

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public Store create(String name, String lineApiKey, String lazadaApiKey,  User user) throws ResponseStatusException {
        if(storeRepository.findByName(name) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store's name already exists");
        }
        Store store = new Store();
        store.setName(name);
        if(lineApiKey != null){store.setLineApiKey(lineApiKey);}
        if(lazadaApiKey != null){store.setLazadaApiKey(lazadaApiKey);}
        List<User> userList = new ArrayList<>();
        userList.add(user);
        store.setUser(userList);
        storeRepository.save(store);
        return store;
    }
}
