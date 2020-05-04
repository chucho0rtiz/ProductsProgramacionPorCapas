package co.edu.ff.orders.productos.controllers;

import co.edu.ff.orders.productos.domain.*;
import co.edu.ff.orders.productos.exceptions.ProductDoesNotExists;
import co.edu.ff.orders.productos.services.ProductServices;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private ProductServices services;

    //findById
    @Test
    @DisplayName("estas son las pruebas findByIdSuccess")
    void findByIdSuccess() throws Exception {
        Product product = Product.of(ProductId.of(1L),
                Name.of("sadsad"),
                Description.of("asdasd"),
                BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)),
                ProductStatus.Borrado,
                InventoryQuantity.of(18));

        when(services.findById(anyLong())).thenReturn(ProductOperationSuccess.of(product));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.get("/api/v1/products/1");
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("estas son las pruebas findByIdFail")
    void findByIdFail() throws Exception {
        when(services.findById(anyLong())).thenReturn(ProductOperationFailure.of(ProductDoesNotExists.of(1L)));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.get("/api/v1/products/1");
        this.mockMvc.perform(servletRequestBuilder).andDo(print()).andExpect(status().is4xxClientError());
    }

    //findAll
    @Test
    @DisplayName("estas son las pruebas findAllSuccess")
    void findAllSuccess() throws Exception {
        List<Product> product = services.findAll();

        when(services.findAll()).thenReturn(product);

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.get("/api/v1/products");
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    //delete
    @Test
    @DisplayName("estas son las pruebas deleteSuccess")
    void deleteSuccess() throws Exception {
        Product product = Product.of(ProductId.of(1L),
                Name.of("sadsad"),
                Description.of("asdasd"),
                BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)),
                ProductStatus.Borrado,
                InventoryQuantity.of(18));

        when(services.deleteOne(anyLong())).thenReturn(ProductOperationSuccess.of(product));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.delete("/api/v1/products/1");
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }



    @Test
    @DisplayName("estas son las pruebas deleteFail")
    void deleteFail() throws Exception {
        when(services.deleteOne(anyLong())).thenReturn(ProductOperationFailure.of(ProductDoesNotExists.of(1L)));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.delete("/api/v1/products/1");
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    //create
    @Test
    @DisplayName("estas son las pruebas createSuccess")
    void createSuccess() throws Exception {
        ProductOperationRequest productOperationRequest = ProductOperationRequest.from(
                Name.of("sadsad"),
                Description.of("asdasd"),
                BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)),
                ProductStatus.Borrado,
                InventoryQuantity.of(18));

        Product product = Product.of(ProductId.of(1L),
                productOperationRequest.getName(),
                productOperationRequest.getDescription(),
                productOperationRequest.getBasePrice(),
                productOperationRequest.getTaxRate(),
                productOperationRequest.getProductStatus(),
                productOperationRequest.getInventoryQuantity());

        when(services.createProduct(productOperationRequest)).thenReturn(ProductOperationSuccess.of(product));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(productOperationRequest));
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("estas son las pruebas createFail")
    void createFail() throws Exception {
        when(services.createProduct(any())).thenReturn(ProductOperationFailure.of(null));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("");
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    //update
    @Test
    @DisplayName("estas son las pruebas updateSuccess")
    void updateSuccess() throws Exception {
        ProductOperationRequest productOperationRequest = ProductOperationRequest.from(
            Name.of("sadsad"),
            Description.of("asdasd"),
            BasePrice.of(new BigDecimal(14544)),
            TaxRate.of(new BigDecimal(1)),
            ProductStatus.Borrado,
            InventoryQuantity.of(18));

        Product product = Product.of(ProductId.of(1L),
            productOperationRequest.getName(),
            productOperationRequest.getDescription(),
            productOperationRequest.getBasePrice(),
            productOperationRequest.getTaxRate(),
            productOperationRequest.getProductStatus(),
            productOperationRequest.getInventoryQuantity());

        when(services.updateOne(1L,productOperationRequest)).thenReturn(ProductOperationSuccess.of(product));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.put("/api/v1/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(productOperationRequest));
        this.mockMvc.perform(servletRequestBuilder)
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
}

    @Test
    @DisplayName("estas son las pruebas updateFail")
    void updateFail() throws Exception {
        ProductOperationRequest productOperationRequest = ProductOperationRequest.from(
                Name.of("sadsad"),
                Description.of("asdasd"),
                BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)),
                ProductStatus.Borrado,
                InventoryQuantity.of(18));

        when(services.updateOne(anyLong(),any())).thenReturn(ProductOperationFailure.of(ProductDoesNotExists.of(1L)));

        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(productOperationRequest));
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}