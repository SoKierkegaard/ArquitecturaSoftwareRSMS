package com.proxy;

public class ProxyLibro implements Libro{

    LibroReal libro;
    Boolean permiso;


    public ProxyLibro(LibroReal libro, Boolean permiso) {
        this.libro = libro;
        this.permiso = permiso;
    }


    @Override
    public String leer() {
        String mensaje="Verificando permisos";
        if(permiso){
            return mensaje +"\n Acceso concedido \n"+libro.leer();
        } else {
            return mensaje + "\n Acceso denegado";
        }
        
    }
}
