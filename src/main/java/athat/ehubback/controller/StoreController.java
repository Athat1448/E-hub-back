package athat.ehubback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.Store;
import athat.ehubback.model.User;
import athat.ehubback.service.StoreService;
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
        private User user;
    }

    @PostMapping("/create")
    public Store create(@RequestBody StoreRequestBody storeRequest)throws ResponseStatusException{
        Store Store = storeService.create(storeRequest.getName(), storeRequest.getLineApiKey(), storeRequest.getLazadaApiKey(), storeRequest.getUser());
        return Store;
    }
}
