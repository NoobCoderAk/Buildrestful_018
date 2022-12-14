/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws.springboot.praktikum10;

import java.util.HashMap;
import java.util.Map;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ABID
 */

@RestController
public class ProductServiceController {
    
    //method untuk menyimpan product
    private static Map<String, Product> productRepo = new HashMap<>();
    static{
        
        //membuat product
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        honey.setPrice(35000.0);
        honey.setDiskon(15);
        honey.setTotal(honey.getPrice()-(honey.getPrice()*honey.getDiskon())/100);
        productRepo.put(honey.getId(),honey);
        
        
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        almond.setPrice(25000.0);
        almond.setDiskon(25);
        almond.setTotal(honey.getPrice()-(honey.getPrice()*honey.getDiskon())/100);
        productRepo.put(almond.getId(), almond);
    }
    
    //method untuk menambahkan product
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //jika product id telah ada, maka display "product is already exist"
        if(productRepo.containsKey(product.getId())){
            return new ResponseEntity<>("product is already exist !", HttpStatus.CONFLICT);
        }else{ //kondisi lain Maka Display : "Product is created successfully"
            product.setTotal(product.getPrice()-(product.getPrice()*product.getDiskon())/100);
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
        }
    }
    
    //method untuk mengedit product
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id")String id, @RequestBody Product product){
        //jika id tidak ada maka display:
        if(!productRepo.containsKey(id)){
            return new ResponseEntity<>("product not found !", HttpStatus.NOT_FOUND);
        }else{//kondisi lain Maka Display :
            //remove product
            productRepo.remove(id);
            
            //replace dengnan product baru
            product.setId(id);
            product.setTotal(product.getPrice()-(product.getPrice()*product.getDiskon())/100);
            productRepo.put(id, product);
            
            //display
            return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
        }
    }
    //method untuk display product
    @RequestMapping(value="/products")
    public ResponseEntity<Object> getProduct(@RequestParam(value = "name", required = false, defaultValue="honey") String name){
        //diplay product
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }
    
    //method untuk menghapus product
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id")String id){
        //jika id tidak ada maka display:
        if(!productRepo.containsKey(id)){
            return new ResponseEntity<>("product not found !", HttpStatus.NOT_FOUND);
        }else{//kondisi lain Maka Display :
            //remove product
            productRepo.remove(id);
            return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
        }
    }
}
