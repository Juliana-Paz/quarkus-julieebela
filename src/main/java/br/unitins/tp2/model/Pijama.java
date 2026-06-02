package br.unitins.tp2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pijama extends DefaultEntity {

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(length = 100)
    private String modelo;

    @Column(nullable = false)
    private Integer estoque;

    @Column(nullable = false)
    private Boolean ativo;

    private Tamanho tamanho;

    private SexoPijama sexo;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "estampa_id")
    private Estampa estampa;

    @ManyToMany
    @JoinTable(
        name = "pijama_cor",
        joinColumns = @JoinColumn(name = "pijama_id"),
        inverseJoinColumns = @JoinColumn(name = "cor_id")
    )
    private List<Cor> cores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "pijama_material",
        joinColumns = @JoinColumn(name = "pijama_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> materiais = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "pijama_arquivo",
        joinColumns = @JoinColumn(name = "pijama_id"),
        inverseJoinColumns = @JoinColumn(name = "arquivo_id", unique = true)
    )
    private List<Arquivo> imagens = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public SexoPijama getSexo() {
        return sexo;
    }

    public void setSexo(SexoPijama sexo) {
        this.sexo = sexo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Estampa getEstampa() {
        return estampa;
    }

    public void setEstampa(Estampa estampa) {
        this.estampa = estampa;
    }

    public List<Cor> getCores() {
        return cores;
    }

    public void setCores(List<Cor> cores) {
        this.cores = cores;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

    public List<Arquivo> getImagens() {
        return imagens;
    }

    public void setImagens(List<Arquivo> imagens) {
        this.imagens = imagens;
    }

    public void addImagem(Arquivo imagem) {
        if (imagem == null) {
            return;
        }
        imagens.add(imagem);
    }

    public void removeImagem(Arquivo imagem) {
        if (imagem == null) {
            return;
        }
        imagens.remove(imagem);
    }

}
