/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.usfx.practica.singleton;

/**
 *
 * @author Sebastian
 */
public class SingleObject {

    private static SingleObject instance=null;
    private static String mensaje = "Hola ";

    private SingleObject() {

    }

    public static SingleObject getInstance() {
        if (instance == null) {
            instance = new SingleObject();
        }
        return instance;
    }

    public static void setMensaje(String mensaje) {
        SingleObject.mensaje = mensaje;
    }
    
    public void ShowMessage(){
        System.out.print(mensaje);
    }
    

}
