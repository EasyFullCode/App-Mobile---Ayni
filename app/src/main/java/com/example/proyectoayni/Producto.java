package com.example.proyectoayni;

public class Producto {

    private int idProducto;
    private String titulo;
    private String categoria;
    private String estado_producto;
    private String preferencia_entrega;
    private String preferencia_cambio;
    private String contacto;
    private String url;

    public Producto() {
    }

    public Producto(int idProducto, String titulo, String categoria, String estado_producto, String preferencia_entrega, String preferencia_cambio, String contacto, String url) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.categoria = categoria;
        this.estado_producto = estado_producto;
        this.preferencia_entrega = preferencia_entrega;
        this.preferencia_cambio = preferencia_cambio;
        this.contacto = contacto;
        this.url = url;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado_producto() {
        return estado_producto;
    }

    public void setEstado_producto(String estado_producto) {
        this.estado_producto = estado_producto;
    }

    public String getPreferencia_entrega() {
        return preferencia_entrega;
    }

    public void setPreferencia_entrega(String preferencia_entrega) {
        this.preferencia_entrega = preferencia_entrega;
    }

    public String getPreferencia_cambio() {
        return preferencia_cambio;
    }

    public void setPreferencia_cambio(String preferencia_cambio) {
        this.preferencia_cambio = preferencia_cambio;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*@Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", titulo='" + titulo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado_producto='" + estado_producto + '\'' +
                ", preferencia_entrega='" + preferencia_entrega + '\'' +
                ", preferencia_cambio='" + preferencia_cambio + '\'' +
                ", contacto='" + contacto + '\'' +
                ", url='" + url + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return titulo;
    }
}
