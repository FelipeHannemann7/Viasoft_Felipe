package com.example.provaviasoftfelipemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText dtNascimento;
    private PessoaDAO pessoaDAO;
    private Pessoa pessoa = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.EditNome);
        cpf = findViewById(R.id.EditCPF);
        telefone = findViewById(R.id.EditTelefone);
        dtNascimento = findViewById(R.id.EditDtNascimento);
        pessoaDAO = new PessoaDAO();

        cpf.addTextChangedListener(Mascara.insert("###.###.###.##",cpf));
        telefone.addTextChangedListener(Mascara.insert("(##)####-####",telefone));
        dtNascimento.addTextChangedListener(Mascara.insert("##/##/####",dtNascimento));

        Intent it = getIntent();
        if(it.hasExtra("pessoa")){
            pessoa = (Pessoa) it.getSerializableExtra("pessoa");
            nome.setText(pessoa.getNome());
            cpf.setText(pessoa.getCpf());
            telefone.setText(pessoa.getTelefone());
            dtNascimento.setText(pessoa.getDtNascimento());
        }
    }

    public void salvar (View view){

        if (pessoa == null){
            Pessoa p = new Pessoa();
            p.setNome(nome.getText().toString());
            p.setCpf(cpf.getText().toString());
            p.setTelefone(telefone.getText().toString());
            p.setDtNascimento(dtNascimento.getText().toString());
            PessoaDAO.addPessoa(p);
            Toast.makeText(this, "Pessoa inserida com sucesso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListarPessoas.class);
            startActivity(intent);
        }else{
            pessoa.setNome(nome.getText().toString());
            pessoa.setCpf(cpf.getText().toString());
            pessoa.setTelefone(telefone.getText().toString());
            pessoa.setDtNascimento(dtNascimento.getText().toString());
            pessoaDAO.atualizarPessoa(pessoa);
            Toast.makeText(this, "Pessoa atualizada  com sucesso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListarPessoas.class);
            startActivity(intent);
        }




    }
}