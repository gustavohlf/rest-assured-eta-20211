package models;

public class Produtos {
    private String nome;
    private String preco;
    private String descricao;
    private String quantidade;
    private String _id;

    public Produtos(String nome, String preco, String descricao, String quantidade){
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public void setIdProduto(String id){
        this._id = id;
    }

    public String getProdutoId(){
        return this._id;
    }

    public String getNome(){
        return this.nome;
    }
}
