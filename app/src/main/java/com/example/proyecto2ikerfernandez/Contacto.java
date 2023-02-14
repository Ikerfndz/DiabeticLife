package com.example.proyecto2ikerfernandez;

public class Contacto {

    public String nombre;
    public String direccion;
    public String mail;
    public String telefono;


    public Contacto(String nombre, String direccion, String mail, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.mail = mail;
        this.telefono = telefono;
    }

    public Contacto() {

    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMail() {
        return mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", mail='" + mail + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
