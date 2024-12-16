package com.example.mei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    Button mAdiconar;
    EditText mNome;
    public static final String ARQUIVO_MEUS_DADOS = "Meus Dados";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAdiconar = findViewById(R.id.buttonEmpresa);
        mNome = findViewById(R.id.nomeEmpresa);
        mAdiconar.setOnClickListener(mButtonAdicionar);

    }
    private View.OnClickListener mButtonAdicionar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
            String nomeEmpresa = mNome.getText().toString(); //Recupera o nome digitadp
            if (!nomeEmpresa.isEmpty()) { // Verifica se foi realmente digitado algo, se sim é adicionado ao SharedPreferencs
                sharedPreferences.edit().putString("Nome",nomeEmpresa).apply();
                Toast.makeText(getBaseContext(),"Nome Adicionado com Sucessso",Toast.LENGTH_SHORT).show();

            }
            // Mesmo se o nome não tenha sido adionado quando a Activy for chamada, se houver algum nome registrado
            // sharedPreferens,o usuário volta para a Activy anterior
            if (sharedPreferences.getString("Nome",null) !=null){

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }else{ // Se não houver nenhum nome registrado, essa mensagem é exibida, e o usuário não consegue voltar para a Activy anterior
                Toast.makeText(getBaseContext(),"Por favor, digite um nome",Toast.LENGTH_SHORT).show();

            }
        }
    };
}