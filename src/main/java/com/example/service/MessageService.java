package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        if(message.getMessageText().isBlank() || 
        message.getMessageText().length() > 255 || 
        accountRepository.findById(message.getPostedBy()).isEmpty()){
            return null;
        }
        else{
            return messageRepository.save(message);
        }
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }
    
    public Optional<Message> findMessageByID(Integer messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isEmpty()){
            return null;
        }
        return message;
    }

    public void deleteMessageByID(Integer messageId){
        messageRepository.deleteById(messageId);
    }

    public boolean updateMessageByID(Integer messageId, String messageText){
        Optional <Message> message = messageRepository.findById(messageId);
        if(message.isPresent() && messageText.length() <= 255 && 
        !message.get().getMessageText().isEmpty()){
            messageRepository.save(message.get());
            return true;
        }
        else{
            return false;
        }
    }

    public List<Message> getMessagesByAccountId(Integer postedBy){
        List<Message> accountMessages = messageRepository.findByPostedBy(accountRepository.getById(postedBy));
        if (accountMessages == null) {
            return null;
        } else {
            return accountMessages;
        }
    }

}
