package com.example.mobilev10;

import org.json.JSONException;
import org.json.JSONObject;

public class Socios {

    private int _id;
    private String _nome;
    private String _dtnasc;
    private String _cpf;
    private String _cargo;
    private String _senha;
    private String _numend;

    private String _telefone;
    private String _email;

    private String _estado;
    private String _cidade;
    private String _bairro;
    private String _logradouro;
    private String _complemento;

    public Socios() { }

    public Socios(String nome, String dtnasc, String cpf, String cargo, String senha, String numend,
                    String telefone, String email,
                    String estado, String cidade, String bairro, String logradouro, String complemento){
        this.set_nome(nome);
        this.set_dtnasc(dtnasc);
        this.set_cpf(cpf);
        this.set_cargo(cargo);
        this.set_senha(senha);
        this.set_numend(numend);
        this.set_telefone(telefone);
        this.set_email(email);
        this.set_estado(estado);
        this.set_cidade(cidade);
        this.set_bairro(bairro);
        this.set_logradouro(logradouro);
        this.set_complemento(complemento);
    }

    public Socios(int _id, String _nome, String _dtnasc, String _cpf, String _cargo, String _senha, String _numend,
                    String _telefone, String _email,
                    String _estado, String _cidade, String _bairro, String _logradouro, String _complemento) {
        this._id = _id;
        this._nome = _nome;
        this._dtnasc = _dtnasc;
        this._cpf = _cpf;
        this._cargo = _cargo;
        this._senha = _senha;
        this._numend = _numend;
        this._telefone = _telefone;
        this._email = _email;
        this._estado = _estado;
        this._cidade = _cidade;
        this._bairro = _bairro;
        this._logradouro = _logradouro;
        this._complemento = _complemento;

    }

    public int get_id() { return _id; }
    public void set_id(int _id) { this._id = _id; }

    public String get_nome() { return _nome; }
    public void set_nome(String _nome) { this._nome = _nome; }

    public String get_dtnasc() { return _dtnasc; }
    public void set_dtnasc(String _dtnasc) { this._dtnasc = _dtnasc; }

    public String get_cpf() { return _cpf; }
    public void set_cpf(String _cpf) { this._cpf = _cpf; }

    public String get_cargo() { return _cargo; }
    public void set_cargo(String _cargo) { this._cargo = _cargo; }

    public String get_senha() { return _senha; }
    public void set_senha(String _senha) { this._senha = _senha; }

    public String get_numend() { return _numend; }
    public void set_numend(String _numend) { this._numend = _numend; }

    public String get_telefone() { return _telefone; }
    public void set_telefone(String _telefone) { this._telefone = _telefone; }

    public String get_email() { return _email; }
    public void set_email(String _email) { this._email = _email; }

    public String get_estado() { return _estado; }
    public void set_estado(String _estado) { this._estado = _estado; }

    public String get_bairro() { return _bairro; }
    public void set_bairro(String _bairro) { this._bairro = _bairro; }

    public String get_cidade() { return _cidade; }
    public void set_cidade(String _cidade) { this._cidade = _cidade; }

    public String get_logradouro() { return _logradouro; }
    public void set_logradouro(String _logradouro) { this._logradouro = _logradouro; }

    public String get_complemento() { return _complemento; }
    public void set_complemento(String _complemento) { this._complemento = _complemento; }
}
