package com.example.controller;

import java.net.ResponseCache;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@Component
public class SocialMediaController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    } 
    
    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody Account account) {
        if(accountService.findUsername(account.getUsername()) == false){
            accountService.register(account);
            return ResponseEntity.status(HttpStatus.OK).body(200);
        }
        else{
            return ResponseEntity.status(409).body(409);
        }
    }

    @PostMapping("/login")
        public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException{
            if(accountService.login(account.getUsername(), account.getPassword()) != null){
                return ResponseEntity.ok(accountService.login(account.getUsername(), account.getPassword()));
            }
            else{
                return ResponseEntity.status(401).body(null);
            }
        }

    @PostMapping("/messages")
        public ResponseEntity<Message> createMessage(@RequestBody Message message){
            messageService.createMessage(message);
            if(messageService.createMessage(message) != null){
                return ResponseEntity.ok(messageService.createMessage(message));
            }
            else{
                return ResponseEntity.status(400).body(null);
            }
        }
        
    @GetMapping("/messages")
    public @ResponseBody List<Message> getMessages(){
        return messageService.getMessages();
    }

    @GetMapping("/messages/{messageID}")
    public @ResponseBody ResponseEntity<Optional<Message>> findMessageByID(@PathVariable Integer messageID){
        return ResponseEntity.ok().body(messageService.findMessageByID(messageID));
    }

    @DeleteMapping("/messages/{messageID}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageByID(@PathVariable Integer messageID){
        messageService.deleteMessageByID(messageID);
        return ResponseEntity.ok(1);
    }

    @PatchMapping("/messages/{messageID}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable Integer messageID, 
    @RequestBody Message message){
            messageService.updateMessageByID(messageID, message.getMessageText());
            if(messageService.updateMessageByID(messageID, message.getMessageText()) == true && !message.getMessageText().isEmpty()){
                return ResponseEntity.ok(1);
            }
            else{
                return ResponseEntity.status(400).body(null);
            }
    } 

    //@GetMapping("/account/{accountId}")
    //public @ResponseEntity List<Message> getMessagesByAccountId(@PathVariable Integer accountId){
//
    //}
}
