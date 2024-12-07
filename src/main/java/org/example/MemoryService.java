package org.example;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MemoryService {

    private final Memory memory;
    public  Memory getMemory(){
        return memory;
    }
    MemoryService(String url,String user,String password) {
        this.memory = new Memory(url,user,password);
    }
    public void saveChoice(Choice choice){
          memory.getChoiceInterface().save(choice);
    }
    public void saveUser(User user){
        memory.getUserInterface().save(user);
    }
    public List<Choice> getChoicesWithAnswer(String answer,boolean option,Integer responderID){
        if(option){
            return memory.getChoiceInterface().getAll().stream().filter(choice -> choice.getAnswer().equals(answer) && choice.getResponder_user().getId()==responderID).collect(Collectors.toList());
        }
        else{
            return memory.getChoiceInterface().getAll().stream().filter(choice -> !Objects.equals(choice.getAnswer(), answer) && choice.getResponder_user().getId()==responderID).collect(Collectors.toList());
        }

    }
    public List<Choice> getAllChoices(){
        return memory.getChoiceInterface().getAll();
    }

    public User getById(int searchId){
        return memory.getUserInterface().getByID(searchId).orElse(null);
    }
    public List<User> getAllUsers(){
        return memory.getUserInterface().getAll();
    }
    public List<User> getUsersByGender(String gender){
        return memory.getUserInterface().getUsersByGender(gender);
    }
    public void save(Message message){
        memory.getMessageInterface().save(message);
    }
    public List<Message> getAll(Integer fromWho,Integer toWho){
        return memory.getMessageInterface().getAll(fromWho,toWho);
    }
}
