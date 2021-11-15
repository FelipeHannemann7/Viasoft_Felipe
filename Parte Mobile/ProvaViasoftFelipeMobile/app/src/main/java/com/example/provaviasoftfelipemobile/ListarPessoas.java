package com.example.provaviasoftfelipemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListarPessoas extends AppCompatActivity {



    private ListView listView;
    private PessoaDAO pessoaDAO;
    private List<Pessoa> pessoas;
    private List<Pessoa> pessoaFiltrada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pessoas);

        listView = findViewById(R.id.lista_pessoa);
        pessoaDAO = new PessoaDAO();

        pessoas = pessoaDAO.getAllPessoas();
        pessoaFiltrada.addAll(pessoas);
        ArrayAdapter<Pessoa> ad = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, pessoaFiltrada);
        listView.setAdapter(ad);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                System.out.println("Digitou: " + s);
                procuraPessoa(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_context, menu);
    }

    public void procuraPessoa(String nome){
        pessoaFiltrada.clear();
        for(Pessoa p: pessoas){
            if(p.getNome().toLowerCase(Locale.ROOT).contains(nome.toLowerCase())){
                pessoaFiltrada.add(p);
            }
        }
        listView.invalidateViews();
    }


    public void atualizarPessoa(MenuItem item){
        AdapterView.AdapterContextMenuInfo mnInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Pessoa pessoaAtualizar = pessoaFiltrada.get(mnInfo.position);

        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("pessoa", pessoaAtualizar);
        startActivity(it);

    }

    public void deletarPessoa(MenuItem item) {
        AdapterView.AdapterContextMenuInfo mnInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Pessoa pessoaDeletar = pessoaFiltrada.get(mnInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja excluir a pessoa ?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pessoaFiltrada.remove(pessoaDeletar);
                        pessoas.remove(pessoaDeletar);
                        PessoaDAO.deletarPessoa(pessoaDeletar);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();

    }

    public void add(MenuItem item){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    @Override
    public  void onResume(){
        super.onResume();
        pessoas = pessoaDAO.getAllPessoas();
        pessoaFiltrada.clear();
        pessoaFiltrada.addAll(pessoas);
        listView.invalidateViews();

    }
}