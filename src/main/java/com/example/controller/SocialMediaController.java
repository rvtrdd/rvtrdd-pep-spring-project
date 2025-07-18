package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidUserParamException;
import com.example.exception.UnauthorizedLoginException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService aServ, MessageService mServ){
        this.accountService = aServ;
        this.messageService = mServ;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount (@RequestBody Account newAccount){
        Account responseAccount;
        try {
            responseAccount = accountService.registerAccount(newAccount);
            return ResponseEntity.status(HttpStatus.OK).body(responseAccount);
        } catch (InvalidUserParamException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account responseAccount;
        try {
            responseAccount = accountService.loginAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(responseAccount);
        } catch (UnauthorizedLoginException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesOfAccount(@PathVariable Integer accountId) {
        try {
            List<Message> responseMessages = messageService.getAllMessagesOfAccount(accountId);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessages);
        } catch (Exception e){
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
