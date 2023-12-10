package com.example.proyectoayni;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

public class ChatbotDialog extends Dialog {
    private final NavController navController;
    private EditText userInput;
    private TextView chatDisplay;
    private TextView chatbot;

    private ChatViewModel chatViewModel;
   // private StringBuilder chatMessages;

    private List<String> chatMessages;

    public ChatbotDialog(@NonNull Context context, NavController navController) {
        super(context);
        this.navController = navController;
        //this.chatMessages = new StringBuilder();
        this.chatMessages = new ArrayList<>();
        // Utiliza el ViewModelStoreOwner personalizado
        ChatViewModelStoreOwner viewModelStoreOwner = new ChatViewModelStoreOwner();
        chatViewModel = new ViewModelProvider(viewModelStoreOwner).get(ChatViewModel.class);
        updateChatDisplay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_chatbot);

        userInput = findViewById(R.id.editTextUserInput);
        chatDisplay = findViewById(R.id.textViewChatDisplay);
        chatbot = findViewById(R.id.textViewChatbotMessage);



        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = userInput.getText().toString();
                String chatbotResponse = generateChatbotResponse(userMessage);

                // Agrega los mensajes al ViewModel
                chatViewModel.addMessage("Tú: " + userMessage);
                chatViewModel.addMessage("Chatbot: " + chatbotResponse);


                // Limpia el cuadro de texto del usuario
                userInput.setText("");

                // Actualiza la interfaz de usuario
                updateChatDisplay();
            }
        });
    }

    //AREA DE APRENDIZAJE

    private String generateChatbotResponse(String userMessage) {
        String lowerCaseMessage = userMessage.toLowerCase();
        if (lowerCaseMessage.contains("hola") && lowerCaseMessage.contains("ayuda")) {
            return "¡Hola!. ¿En que puedo ayudarte?";
        }
        if (lowerCaseMessage.contains("gracias")) {
            return "De nada!, estoy para ayudarte.";
        }
        if (lowerCaseMessage.contains("ropa") && lowerCaseMessage.contains("hombre")) {
            navigateToRopaHombresFragment();
            return "Mostrando ropa para hombres. ¿Puedo ayudarte con algo más?";
        }
        if (lowerCaseMessage.contains("ropa") && lowerCaseMessage.contains("mujer")) {
            navigateToRopaMujerFragment();
            return "Mostrando ropa para mujeres. ¿Puedo ayudarte con algo más?";
        }
        if (lowerCaseMessage.contains("artefactos") && lowerCaseMessage.contains("electronicos")) {
            navigateArtefactosFragment();
            return "Mostrando artefactos electrónicos. ¿Puedo ayudarte con algo más?";
        }


        // Respuesta predeterminada
        return "Lo siento, no entendí. ¿En qué más puedo ayudarte?";
    }

    private void navigateToRopaHombresFragment() {
        if (navController != null) {
            dismiss();
            navController.navigate(R.id.ropaHombreFragment);
        } else {
            Log.e("Chatbot", "NavController sigue siendo nulo");
        }
    }


    // Método para agregar texto a la vista del chat
    private void appendToChat(String userMessage, String chatbotResponse) {
        chatMessages.add(userMessage);
        chatMessages.add(chatbotResponse);
        updateChatDisplay();
    }

    private void updateChatDisplay() {
        List<String> messages = chatViewModel.getChatMessages().getValue();
        if (messages != null && messages.size() >= 2) {
            String userMessage = messages.get(messages.size() - 2);  // Obtén el último mensaje del usuario
            String chatbotResponse = messages.get(messages.size() - 1);  // Obtén la última respuesta del chatbot

            // Actualiza los nuevos TextViews correspondientes
            chatDisplay.setText(userMessage);
            chatbot.setText(chatbotResponse);
        }
    }
    private void navigateToRopaMujerFragment() {
        if (navController != null) {
            // Cierra el diálogo antes de navegar al fragmento
            dismiss();
            // Navega al fragmento de ropa para mujeres
            navController.navigate(R.id.ropaMujerFragment);
        } else {
            Log.e("Chatbot", "NavController sigue siendo nulo");
        }
    }

    private void navigateArtefactosFragment() {
        if (navController != null) {
            // Cierra el diálogo antes de navegar al fragmento
            dismiss();
            // Navega al fragmento de ropa para mujeres
            navController.navigate(R.id.nav_electronico);
        } else {
            Log.e("Chatbot", "NavController sigue siendo nulo");
        }
    }
    @Override
    public void dismiss() {
        hideDialog();
    }

    public void hideDialog() {
        this.hide(); // Puedes utilizar hide() para ocultar el diálogo sin destruir la instancia
    }
    public void showDialog() {
        if (!isShowing()) {
            show();
        }
    }


}