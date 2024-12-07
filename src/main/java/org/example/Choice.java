package org.example;

import java.util.Objects;

public class Choice {
    private final String answer;
    private  final User responder_user;
    private final User receiver_user;
    public Choice(String answer, User responder_user, User receiver_user) {
        this.answer = answer;
        this.responder_user = responder_user;
        this.receiver_user = receiver_user;
    }
    public String getAnswer() {
        if(!Objects.isNull(answer)) {
            return answer;
        }
        else
           return "null";
    }

    public User getReceiver_user() {
        return receiver_user;
    }

    public User getResponder_user() {
        return responder_user;
    }
    @Override
    public int hashCode() {
        return answer.hashCode() + responder_user.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Choice other = (Choice) obj;
        return answer.equals(other.answer) && responder_user.equals(other.responder_user);
    }
}
