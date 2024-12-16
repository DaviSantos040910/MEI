package com.example.mei;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ARQUIVO_MEUS_DADOS = "Meus Dados";
    private NumberPicker numberPicker;
    private RadioGroup mRadioGroup;
    private RadioButton mAdcionar, mExcluir;
    private EditText mValor;
    private TextView mTotal;
    private Button mconfirmar, mNomeEmpresaAdicionar;

    // Listener para quando o valor do NumberPicker ser alterado
    private NumberPicker.OnValueChangeListener valorAlterado = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int valorAntigo, int valorAtual) {
            mostrarDados(valorAtual);
        }
    };

    //Soprepor o metódo onResume, para poder definir o titulo da aplicação assim que aparecer a tela
    //para o usuário
    @Override
    protected void onResume() {
        super.onResume();
        //Recupera o nome da empresa no SharedPreferencs
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        String nomeEmpresa = sharedPreferences.getString("Nome", null);
        if (nomeEmpresa != null) { //Verifica se existe alguma nome registrado para a empresa, se sim, é definido no titulo da aplicação
            setTitle(nomeEmpresa);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        numberPicker = findViewById(R.id.ano);
        mNomeEmpresaAdicionar = findViewById(R.id.nomeEmpresaAdicionar);
        mAdcionar = findViewById(R.id.radioAdicionar);
        mExcluir = findViewById(R.id.radioExcluir);
        mValor = findViewById(R.id.valor);
        mTotal = findViewById(R.id.TextValor);
        mconfirmar = findViewById(R.id.btnConfirme);
        mRadioGroup = findViewById(R.id.radioGroup);


        numberPicker.setMinValue(2018);
        numberPicker.setMaxValue(2022);
        // Inicia a Activity com o valor atualizado. Precisa estar depois das outras configurações
        mostrarDados(numberPicker.getValue());
        numberPicker.setOnValueChangedListener(valorAlterado); // Define o metódo do NumberPick
        mNomeEmpresaAdicionar.setOnClickListener(adiconarNomeEmpresaListener);
    }

    private void gravarDados(int ano, float valor) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        // Recuperando os dados armazenados
        float valorArmazenado = sharedPreferences.getFloat(String.valueOf(ano), 0);
        sharedPreferences.edit()
                .putFloat(String.valueOf(ano), (valorArmazenado + valor)) // Atualizando o valor
                .apply();


    }

    private void excluirDados(int ano, float valor) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        // Recuperando os dados armazenados
        float valorArmazenado = sharedPreferences.getFloat(String.valueOf(ano), 0);
        sharedPreferences.edit()
                .putFloat(String.valueOf(ano), (valorArmazenado - valor)) // Atualizando o valor
                .apply();

    }

    private void mostrarDados(int ano) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        //Recuperando os dados
        float valor = sharedPreferences.getFloat(String.valueOf(ano), 0);
        mTotal.setText("R$" + String.valueOf(valor)); //Exebindo os dados
    }

    public void confirmar(View v) {
        if (mValor.getText().toString().isEmpty()) { // Verifica se o valor está em branco
            Toast.makeText(MainActivity.this, "Por favor, digite um valor", Toast.LENGTH_SHORT).show();
        } else {
            float valorDigitado = Float.parseFloat(mValor.getText().toString());
            int ano = numberPicker.getValue();
            // Seleciona o RadioGroup para poder saber qual RadionButton está ativado pelo id
            switch (mRadioGroup.getCheckedRadioButtonId()) {
                case R.id.radioAdicionar:
                    gravarDados(ano, valorDigitado);
                    break;
                case R.id.radioExcluir:
                    excluirDados(ano, valorDigitado);
                    break;
            }
            mostrarDados(ano); // Mostra os dados
        }

    }

    private View.OnClickListener adiconarNomeEmpresaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getBaseContext(), MainActivity2.class); // Primeiro parâmeto é o contexto; o segundo é a Activy que será iniciada
            startActivity(intent); // Inicia a Activity
        }
    };
}
    /*
    LEMBRETE - Metódo para chamar fazer um intent implicito(quando não se define a Activy)

    private void compartilharMensagem(String mensagem) {

        39. Intent sendIntent = new Intent();
        40. sendIntent.setAction(Intent.ACTION_SEND);
        41. sendIntent.putExtra(Intent.EXTRA_TEXT, mensagem);
        42. sendIntent.setType("text/plain");
        43. if (sendIntent.resolveActivity(getPackageMa
                nager()) != null) {
            44. startActivity(sendIntent);
            45. }
        46. }
Linha 39: declaramos um novo objeto Intent com nenhum
parâmetro no construtor.

Linha 40: utilizamos o método setAction(Intent.ACTION_
SEND) para definir a ação “Enviar” que deverá ser executada no
objeto Intent.

Linha 41: armazenamos no objeto Intent, através do método
putExtra(), a chave “Intent.EXTRA_TEXT” e como valor definimos a
mensagem que vamos compartilhar com outros aplicativos.

Linha 42: por meio do método setType("text/plain"), definimos o
tipo de conteúdo que o Intent está enviando. No nosso exemplo,
estamos enviando um “texto/simples”.

Linha 43: caso não exista nenhum aplicativo capaz de receber
o Intent, o aplicativo será encerrado com uma mensagem de erro.
É possível contornar esta situação acrescentando uma estrutura
condicional que verifica se há alguma Activity que pode receber o
Intent criado. Se o resultado for diferente de nulo, significa que há no
mínimo um aplicativo capaz de receber o Intent para ser processado.
Desta forma, é seguro chamar o método startActivity(sendIntent).

Linha 44: utilizamos o método startActivity(intent) para iniciar
uma Activity de qualquer aplicativo que aceite a ação Intent.
ACTION_SEND com o tipo text/plain

        **/
