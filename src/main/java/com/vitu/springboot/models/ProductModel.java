package com.vitu.springboot.models;


import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_products")
public class ProductModel implements Serializable {
    //Serializable -> mostra para a jvm que a classe ta habilitada a passar por serializações (json)

    private static final long serialVersionUID = 1L; //-> numero de controle de versão da classe

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct; //UUID -> utilizado em microserivces para evitar conflito de ID ao realizar hashcode
    private String name;
    private BigDecimal value;

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
