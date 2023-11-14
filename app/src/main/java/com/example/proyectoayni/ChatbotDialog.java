package com.example.proyectoayni;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ChatbotDialog extends Dialog {
    private EditText userInput;
    private TextView chatDisplay;

    public ChatbotDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_chatbot); // crea un archivo XML para tu diseño de cuadro de diálogo

        userInput = findViewById(R.id.editTextUserInput);
        chatDisplay = findViewById(R.id.textViewChatDisplay);

        // Maneja el botón de enviar en el cuadro de diálogo
        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el texto del usuario y lo agrega a la vista del chat
                String userMessage = userInput.getText().toString();
                appendToChat("Usuario: " + userMessage);

                // Aquí es donde puedes llamar a tu método `processUserQuery` para obtener la respuesta del chatbot
                // Luego, puedes agregar la respuesta a la vista del chat
                // processUserQuery(userMessage);
                // appendToChat("Chatbot: " + respuestaDelChatbot);

                // Limpia el cuadro de texto del usuario
                userInput.setText("");
            }
        });
    }

    // Método para agregar texto a la vista del chat
    private void appendToChat(String message) {
        chatDisplay.append(message + "\n");
    }
}
