package athat.ehubback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.Store;
import athat.ehubback.model.User;
import athat.ehubback.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Getter
    static class StoreRequestBody {
        private String name;
        private String lineApiKey;
        private String lazadaApiKey;
    }

    @PostMapping("/create")
    public String create(@RequestBody StoreRequestBody storeRequest, HttpServletRequest request)throws ResponseStatusException{
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String newtoken = storeService.create(storeRequest.getName(), storeRequest.getLineApiKey(), storeRequest.getLazadaApiKey(), token);
        return newtoken;
    }

    @PostMapping("/adduser")
    public ResponseEntity<Object> join(HttpServletRequest request, @RequestBody User user)throws ResponseStatusException{
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String name = user.getUsername();
        Store store = storeService.addUserToStore(token, name);
        return ResponseEntity.status(202).body("Join " + store.getName() + "'s store");
    }

    @GetMapping("/getstore")
    public String getStore(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Store store = storeService.getStore(token);
        String key = store.getLineApiKey();
        return key;
    }

}
