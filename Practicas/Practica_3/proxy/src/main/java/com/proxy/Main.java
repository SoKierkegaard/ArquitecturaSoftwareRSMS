package com.proxy;

public class Main {
    public static void main(String[] args) {
        LibroReal libro = new LibroReal("Cien años de soledad", "Gabriel García Márquez", 1967, "Muchos años después...");

        ProxyLibro proxyConAcceso = new ProxyLibro(libro, true);
        System.out.println(proxyConAcceso.leer());

        System.out.println("-----");

        ProxyLibro proxySinAcceso = new ProxyLibro(libro, false);
        System.out.println(proxySinAcceso.leer());
    }
}