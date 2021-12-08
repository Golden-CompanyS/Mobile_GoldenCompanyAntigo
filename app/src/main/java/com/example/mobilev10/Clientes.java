package com.example.mobilev10;

public class Clientes {
    private int _id;
    private String _nome;
    private String _cnpj;
    private String _numend;

    private String _telefone;
    private String _email;

    private String _estado;
    private String _cidade;
    private String _bairro;
    private String _logradouro;
    private String _complemento;

    public Clientes() { }

    public Clientes(String nome,  String cnpj, String numend,
                  String telefone, String email,
                  String estado, String cidade, String bairro, String logradouro, String complemento){
        this.set_nome(nome);
        this.set_cnpj(cnpj);
        this.set_numend(numend);
        this.set_telefone(telefone);
        this.set_email(email);
        this.set_estado(estado);
        this.set_cidade(cidade);
        this.set_bairro(bairro);
        this.set_logradouro(logradouro);
        this.set_complemento(complemento);
    }

    public Clientes(int _id, String _nome, String _cnpj, String _numend,
                  String _telefone, String _email,
                  String _estado, String _cidade, String _bairro, String _logradouro, String _complemento) {
        this._id = _id;
        this._nome = _nome;
        this._cnpj = _cnpj;
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



    public String get_cnpj() { return _cnpj; }
    public void set_cnpj(String _cnpj) { this._cnpj = _cnpj; }

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
