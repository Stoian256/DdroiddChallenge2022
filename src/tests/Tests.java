package tests;

import business.CartService;
import business.ProductService;
import domain.Product;
import domain.ShippingRate;
import domain.SpecialOffer;
import exceptions.RepoException;
import org.junit.Test;
import repository.CartInMemoryRepo;
import repository.OffersFileRepo;
import repository.ProductFileRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
public class Tests {
    @Test
    public void runallTests(){
       productTests();
       shippingRateTests();
       specialOfferTests();
       cartInMemoryRepoTests();
       offerFileRepoTests();
       productFileRepoTests();
       productServiceTests();
       cartServiceTests();
    }

    public void productTests(){
        ShippingRate shippingRate=new ShippingRate("RO",1.0);
        ShippingRate newShippingRate=new ShippingRate("US",3.0);
        Product product=new Product(1,"Monitor",16.77,shippingRate,0.5);
        assertEquals(product.getProductID(),1);
        assertEquals(product.getName(),"Monitor");
        assertTrue(product.getPrice()-16.77<0.0001);
        assertEquals(product.getShippingRate(),shippingRate);
        assertTrue(product.getWeight()-0.5<0.0001);

        product.setProductID(2);
        product.setName("Moni");
        product.setPrice(10.0);
        product.setShippingRate(newShippingRate);
        product.setWeight(0.1);
        assertEquals(product.getProductID(),2);
        assertEquals(product.getName(),"Moni");
        assertTrue(product.getPrice()-10<0.0001);
        assertEquals(product.getShippingRate(),newShippingRate);
        assertTrue(product.getWeight()-0.1<0.0001);
    }

    public void shippingRateTests(){
        ShippingRate shippingRate=new ShippingRate("RO",1.0);
        assertEquals(shippingRate.getCountry(),"RO");
        assertTrue(shippingRate.getRate()-1<0.0001);

        shippingRate.setCountry("US");
        shippingRate.setRate(3.0);
        assertEquals(shippingRate.getCountry(),"US");
        assertTrue(shippingRate.getRate()-3<0.0001);
    }

    public void specialOfferTests(){
        SpecialOffer specialOffer=new SpecialOffer("Monitor",1,"Monitor",0.2);
        assertEquals(specialOffer.getRequirementName(),"Monitor");
        assertTrue(specialOffer.getRequirementCount()==1);
        assertEquals(specialOffer.getAppliedOn(),"Monitor");
        assertTrue(specialOffer.getDiscount()-0.2<0.0001);

        specialOffer.setRequirementName("Moni");
        specialOffer.setRequirementCount(2);
        specialOffer.setAppliedOn("Moni");
        specialOffer.setDiscount(0.3);
        assertEquals(specialOffer.getRequirementName(),"Moni");
        assertTrue(specialOffer.getRequirementCount()==2);
        assertEquals(specialOffer.getAppliedOn(),"Moni");
        assertTrue(specialOffer.getDiscount()-0.3<0.0001);

    }

    public void cartInMemoryRepoTests(){
        CartInMemoryRepo cartInMemoryRepo=new CartInMemoryRepo();
        assertTrue(cartInMemoryRepo.getAll().size()==0);

        ShippingRate shippingRate=new ShippingRate("RO",1.0);
        Product product=new Product(1,"Monitor",16.77,shippingRate,0.5);
        cartInMemoryRepo.add(product,1);
        Map<Product,Integer> cart=cartInMemoryRepo.getAll();
        assertTrue(cart.size()==1);
        assertTrue(cart.get(product)==1);

        cartInMemoryRepo.add(product,2);
        cart=cartInMemoryRepo.getAll();
        assertTrue(cart.size()==1);
        assertTrue(cart.get(product)==3);

        cartInMemoryRepo.remove(product);
        assertTrue(cartInMemoryRepo.getAll().size()==0);
    }

    public void offerFileRepoTests(){
        OffersFileRepo offersFileRepo = new OffersFileRepo("data/offers.csv");
        List<SpecialOffer> specialOffers=offersFileRepo.getAll().stream().toList();
        assertTrue(specialOffers.size()==3);
        SpecialOffer specialOffer=specialOffers.get(0);
        assertEquals(specialOffer.getRequirementName(),"Monitor");
        assertTrue(specialOffer.getRequirementCount()==2);
        assertEquals(specialOffer.getAppliedOn(),"Desk Lamp");
        assertTrue(specialOffer.getDiscount()-0.5<0.0001);
    }

    public void productFileRepoTests(){
        ProductFileRepo productFileRepository = new ProductFileRepo("data/products.csv");
        Collection<Product> products=productFileRepository.getAll();
        assertTrue(products.size()==6);
        try {
            assertEquals(productFileRepository.getOne(1).getName(),"Mouse");
            productFileRepository.getOne(7);
        } catch (RepoException e) {
            assertEquals(e.getMessage(), "Produs inexistenta!\n");
        }
        assertTrue(productFileRepository.getByName("Mouse").getPrice()-10.99<0.0001);
    }

    public void productServiceTests(){
        ProductFileRepo productFileRepository = new ProductFileRepo("data/products.csv");
        ProductService productService=new ProductService(productFileRepository);
        assertTrue(productService.getAllProducts().size()==6);

    }

    public void cartServiceTests(){
        ProductFileRepo productFileRepository = new ProductFileRepo("data/products.csv");
        OffersFileRepo offersFileRepo = new OffersFileRepo("data/offers.csv");
        CartInMemoryRepo cartInMemoryRepo = new CartInMemoryRepo();
        CartService cartService=new CartService(productFileRepository,cartInMemoryRepo,offersFileRepo);
        assertTrue(cartService.getProductsInCart().size()==0);
        try {
            Product product=productFileRepository.getOne(3);
            cartService.addProductToCart(3,1);
            Map<Product, Integer> cart= cartService.getProductsInCart();
            assertTrue(cart.size()==1);
            assertTrue(cart.get(product)==1);
            List<Double> invoice=cartService.computeInvoice(false,false);
            assertTrue(invoice.get(0)-164.99<0.0001);
            assertTrue(invoice.get(1)-57<0.0001);
            assertTrue(invoice.get(2)-221.99<0.0001);

            cartService.addProductToCart(3,1);
            cartService.addProductToCart(2,1);

            invoice=cartService.computeInvoice(true,true);
            assertTrue(invoice.get(0)-437.36<0.0001);
            assertTrue(invoice.get(1)-118<0.0001);
            assertTrue(invoice.get(2)-555.36<0.0001);
            cartService.removeProductFromCart(2);
            assertTrue(cartService.getProductsInCart().size()==1);
            assertTrue(cartService.getProductsInCart().get(product)==2);
        } catch (RepoException e) {
            e.getMessage();
        }

    }
}
