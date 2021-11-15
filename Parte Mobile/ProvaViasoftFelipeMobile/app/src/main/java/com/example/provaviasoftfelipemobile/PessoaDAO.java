package com.example.provaviasoftfelipemobile;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    private static List<Pessoa> ListadePessoa = new ArrayList<Pessoa>();
    private static int FnId = 1;


    public static List<Pessoa> getAllPessoas() {
        return ListadePessoa;
    }

    public static void addPessoa(Pessoa pessoa){
        Pessoa p = new Pessoa();
        p.setId(FnId++);
        p.setNome(pessoa.getNome());
        p.setTelefone(pessoa.getTelefone());
        p.setCpf(pessoa.getCpf());
        p.setDtNascimento(pessoa.getDtNascimento());
        ListadePessoa.add(p);
    }

    public static void deletarPessoa(Pessoa pessoa) {
        Pessoa p = null;
        for (int i = 0; i < ListadePessoa.size(); i++) {
            p = ListadePessoa.get(i);

            if (p.getId() == pessoa.getId()) {
                ListadePessoa.remove(i);
                break;
            }
        }

    }

    public static void atualizarPessoa(Pessoa p) {
        int IdOrigem;
        int IdAtu;
        Pessoa pessoa = null;
        for (int i = 0; i < ListadePessoa.size(); i++) {
            pessoa = ListadePessoa.get(i);
            IdOrigem = pessoa.getId();
            IdAtu =  p.getId();
            if (IdOrigem == IdAtu) {
                pessoa.setNome(p.getNome());
                pessoa.setCpf(p.getCpf());
                pessoa.setCpf(p.getTelefone());
                pessoa.setDtNascimento(p.getDtNascimento());
                break;
            }
        }
    }




}
