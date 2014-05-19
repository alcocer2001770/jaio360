package org.primefaces.examples.domain;

import java.io.Serializable;


public class Customer implements Serializable {

    private int row;

  
    private String descripcion;
    private String tarifa;
//        private Date creationdate;
    private int nrolicencia;
    private int pagototal;      
    private boolean confirmado;
    private String street;
    private String city;
    private String postalCode;
    private String info;
    private String email;
    private String phone;
    
    
    public Customer( String descripcion, String tarifa, int nrolicencia) {
		
		this.descripcion = descripcion;
		this.tarifa = tarifa;
		this.nrolicencia = nrolicencia;
//                this.pagototal=pagototal;
//                this.confirmado = confirmado;
	}

    

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

  

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public Integer getNrolicencia() {
        return nrolicencia;
    }

    public void setNrolicencia(Integer nrolicencia) {
        this.nrolicencia = nrolicencia;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }
    
   
    public void setNrolicencia(int nrolicencia) {
        this.nrolicencia = nrolicencia;
    }

    public int getPagototal() {
        return pagototal;
    }

    public void setPagototal(int pagototal) {
        this.pagototal = pagototal;
    }
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
