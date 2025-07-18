package com.example.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public MessageController(AccountService aServ, MessageService mServ){
        this.accountService = aServ;
        this.messageService = mServ;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message m){
        Message responseMessage;
        try {
            responseMessage = messageService.createMessage(m);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> responseMessages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessages);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageByID(@PathVariable Integer messageId){
        try {
            Message responseMessage = messageService.getMessageByID(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e){
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable Integer messageId){
        try {
            int rowsUpdatedCount = messageService.deleteMessageByID(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdatedCount);
        } catch (RecordNotFoundException e){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Integer> updateMessageByID(@PathVariable Integer messageId, @RequestBody Message m){
        int rowsUpdatedCount;
        try {
            rowsUpdatedCount = messageService.updateMessageByID(messageId, m);
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdatedCount);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
