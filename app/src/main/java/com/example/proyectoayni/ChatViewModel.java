package com.example.proyectoayni;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<List<String>> chatMessages = new MutableLiveData<>();

    public LiveData<List<String>> getChatMessages() {
        if (chatMessages.getValue() == null) {
            chatMessages.setValue(new ArrayList<>());
        }
        return chatMessages;
    }

    public void addMessage(String message) {
        List<String> currentMessages = chatMessages.getValue();
        if (currentMessages == null) {
            currentMessages = new ArrayList<>();
        }
        currentMessages.add(message);
        chatMessages.setValue(currentMessages);
    }

    public void clearChatMessages() {
        chatMessages.setValue(new ArrayList<>());
    }
}
