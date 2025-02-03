package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public void addMessage(Message message){
        messageRepository.save(message);
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }
    
    public Message findMessageByID(Integer messageId){
        Message message = messageRepository.findById(messageId).orElseThrow();
        return message;
    }

    public void deleteMessageByID(Integer messageId){
        messageRepository.deleteById(messageId);
    }

    public boolean updateMessageByID(Integer messageId, String messageText){
        Optional <Message> message = messageRepository.findById(messageId);
        if(message.isPresent() && messageText.length() <= 255 && !message.get().getMessageText().isEmpty()){
            messageRepository.save(message.get());
            return true;
        }
        else{
            return false;
        }
    }


}
