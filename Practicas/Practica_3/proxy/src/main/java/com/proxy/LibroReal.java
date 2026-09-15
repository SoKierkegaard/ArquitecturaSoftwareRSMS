package com.proxy;

public class LibroReal implements Libro{
    String texto;
    String titulo;
    String autor;
    int ano;

    public LibroReal(String titulo, String autor, int ano, String texto) {
        this.texto = texto;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    @Override
    public String leer() {
        return "Título: "+ titulo + 
                "\n Autor: "+autor +
                "\n Año: "+ano + 
                "\n Texto: " + texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    
}
