package com.example.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidUserParamException;
import com.example.exception.RecordNotFoundException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Message createMessage (Message m) throws InvalidUserParamException, DataAccessException{
        String mText = m.getMessageText();
        if (mText.isBlank() || mText.length() > 255){
            throw new InvalidUserParamException();
        }
        return messageRepository.save(m);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByID(Integer messageId){
        return messageRepository.findById(messageId).orElseThrow();
    }

    @Transactional
    public int deleteMessageByID(Integer messageId) throws RecordNotFoundException{
        int rowsUpdatedCount = messageRepository.deleteMessageByID(messageId);
        if (rowsUpdatedCount == 0){
            throw new RecordNotFoundException();
        }
        return rowsUpdatedCount; 
    }

    @Transactional
    public int updateMessageByID(Integer messageId, Message m) throws InvalidUserParamException, RecordNotFoundException{
        String mText = m.getMessageText();
        if (mText.isBlank() || mText.length() > 255){
            throw new InvalidUserParamException();
        }
        int rowsUpdatedCount =  messageRepository.updateMessageByID(mText, m.getTimePostedEpoch(), messageId);
        if (rowsUpdatedCount == 0){
            throw new RecordNotFoundException();
        }
        return rowsUpdatedCount;
    }

    public List<Message> getAllMessagesOfAccount(Integer accountID){
        return messageRepository.findByPostedBy(accountID);
    }
}

