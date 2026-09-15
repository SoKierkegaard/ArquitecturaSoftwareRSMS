/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.edu.usfx.practica.singleton;

/**
 *
 * @author Sebastian
 */
public class SingletonPatternDemo {
    public static void main(String[] args){
        SingleObject objeto =SingleObject.getInstance();
        objeto.ShowMessage();
        objeto.setMensaje("Buenas Tardes");
        objeto.ShowMessage();
        SingleObject.getInstance().ShowMessage();
        SingleObject.getInstance().setMensaje("Buenas Noches");
        objeto.ShowMessage();
    }
}
