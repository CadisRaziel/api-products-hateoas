package com.vitu.springboot.controllers;


import com.vitu.springboot.dtos.ProductRecordDTO;
import com.vitu.springboot.models.ProductModel;
import com.vitu.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    //No resposeEntitye passamos o model e como parametro passamos o DTO
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {

        //quando recebermos um recordDTo ali do parametros, vamor criar uma instancia da entidade que vai ser salva no banco de dados
        //O dto é só pra nós receber o body
        var productModel = new ProductModel();

        // BeanUtils.copyProperties -> converte um DTO para um MODEL (ou poderiamos fazer em um map)
        //o primeiro parametro é o que vai ser convertido
        //o segundo parametro é o tipo para que ele será convertido
        //ou seja vamos converter um dto para um model
        BeanUtils.copyProperties(productRecordDTO, productModel);

        //CREATED -> 201
        //body(productRepository.save(productModel)); // ->  enviamos o que for salvo no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        //ResponseEntity<Object> -> esta object aqui pois vão ter dois tipos de resposta(retorno) a de sucesso e a de erro
        Optional<ProductModel> productO = productRepository.findById(id);

        //por causa do Optional conseguimos usar o isEmpty aqui
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get()); //get -> quando usamos o Optional precisamos utilizar esse .get()
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        var ProductModel = productO.get(); //aqui nao vamos instanciar do zero, vamos atribuir um valor que ja recebemos na base de dados pois o UUID(produto) ja existe
        BeanUtils.copyProperties(productRecordDTO, ProductModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(ProductModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
