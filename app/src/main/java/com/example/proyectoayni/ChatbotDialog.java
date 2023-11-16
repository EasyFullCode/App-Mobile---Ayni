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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

public class ChatbotDialog extends Dialog {
    private final NavController navController;
    private EditText userInput;
    private TextView chatDisplay;

   // private StringBuilder chatMessages;

    private List<String> chatMessages;

    public ChatbotDialog(@NonNull Context context, NavController navController) {
        super(context);
        this.navController = navController;
        //this.chatMessages = new StringBuilder();
        this.chatMessages = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_chatbot);

        userInput = findViewById(R.id.editTextUserInput);
        chatDisplay = findViewById(R.id.textViewChatDisplay);


        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = userInput.getText().toString();
                String chatbotResponse = generateChatbotResponse(userMessage);

                appendToChat(userMessage, chatbotResponse);

                // Limpia el cuadro de texto del usuario
                userInput.setText("");
            }
        });
    }

    private String generateChatbotResponse(String userMessage) {
        String lowerCaseMessage = userMessage.toLowerCase();
        if (lowerCaseMessage.contains("ropa") && lowerCaseMessage.contains("hombre")) {
            navigateToRopaHombresFragment();
            return "Mostrando ropa para hombres. ¿Puedo ayudarte con algo más?";
        }
        if (lowerCaseMessage.contains("ropa") && lowerCaseMessage.contains("mujer")) {
            navigateToRopaMujerFragment();
            return "Mostrando ropa para mujeres. ¿Puedo ayudarte con algo más?";
        }
        if (lowerCaseMessage.contains("hola") && lowerCaseMessage.contains("ayuda")) {
            return "¡Hola!. ¿En que puedo ayudarte?";
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
        chatMessages.add("Tú: " + userMessage);
        chatMessages.add("Chatbot: " + chatbotResponse);
        updateChatDisplay();
    }

    private void updateChatDisplay() {
        chatDisplay.setText(TextUtils.join("\n", chatMessages));
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

}