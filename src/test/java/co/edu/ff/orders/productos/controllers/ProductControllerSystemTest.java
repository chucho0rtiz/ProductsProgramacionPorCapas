package co.edu.ff.orders.productos.controllers;

import co.edu.ff.orders.productos.domain.*;
import co.edu.ff.orders.productos.exceptions.ProductDoesNotExists;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.util.Strings.concat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("prod")
class ProductControllerSystemTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @Test
    @DisplayName("Crear usuario y buscar por id")
    void createAndFindProduct() throws Exception {
        // Create product
        ProductOperationRequest productOperationRequest = ProductOperationRequest.from(
                Name.of("sadsad"), Description.of("asdasd"), BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)), ProductStatus.Borrado, InventoryQuantity.of(18));

        String jsonProductOperation = gson.toJson(productOperationRequest);
        String createdJsonProductOperation = String.format("{\"product\":%s}", jsonProductOperation);
        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProductOperation)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(createdJsonProductOperation));

        // Find product
        Product product = Product.of(ProductId.of(1L),
                productOperationRequest.getName(), productOperationRequest.getDescription(), productOperationRequest.getBasePrice(),
                productOperationRequest.getTaxRate(), productOperationRequest.getProductStatus(), productOperationRequest.getInventoryQuantity());

        String jsonProduct = gson.toJson(product);
        String findJsonProduct = String.format("{\"product\":%s}", jsonProduct);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(findJsonProduct));

    }

    @Test
    @DisplayName("Crear usuarios y listarlos")
    void createAndListProduct() throws Exception {
        ProductOperationRequest productOperationRequest = ProductOperationRequest.from(
                Name.of("sadsad"), Description.of("asdasd"), BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)), ProductStatus.Borrado, InventoryQuantity.of(18));

        String jsonProductOperation = gson.toJson(productOperationRequest);
        String createdJsonProductOperation = String.format("{\"product\":%s}", jsonProductOperation);
        for (Integer i=1; i<=5; i++){
            mockMvc.perform(
                    post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonProductOperation)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json(createdJsonProductOperation));
        }



        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    MockHttpServletResponse servletResponse = mvcResult.getResponse();
                    String responseList = servletResponse.getContentAsString();
                    String[] stringSplit = responseList.split("},");
                    String newProduct = createdJsonProductOperation.replace("{", "").replace("}", "");
                    List<String> stringResponses = new ArrayList<String>();
                    stringResponses = Arrays.asList(stringSplit);
                    List<String> listClean = stringResponses.stream()
                            .map(s -> s.replace("{", ""))
                            .map(s -> s.replace("[", ""))
                            .map(s -> s.replace("}]", ""))
                            .filter(s -> s.equals(newProduct))
                            .collect(Collectors.toList());
                    assertNotEquals(listClean, "");
                });

    }

    @Test
    @DisplayName("Crear usuarios y listarlos")
    void deleteisNotInAllProducts() throws Exception {
        ProductOperationRequest productOperationRequest = ProductOperationRequest.from(
                Name.of("sadsad"), Description.of("asdasd"), BasePrice.of(new BigDecimal(14544)),
                TaxRate.of(new BigDecimal(1)), ProductStatus.Borrado, InventoryQuantity.of(18));

        String jsonProductOperation = gson.toJson(productOperationRequest);
        String createdJsonProductOperation = String.format("{\"product\":%s}", jsonProductOperation);
        //String jsonExpectedPost = "{\"name\":\"Andres\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed...\",\"basePrice\":553.26,\"taxRate\":0.25,\"productStatus\":\"PUBLICADO\",\"inventoryQuantity\":88}";
        //String jsonProduct = this.gson.toJson(product);
        for (Integer i=1; i<=5; i++) {
            mockMvc.perform(delete(concat("/api/v1/products/",i))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonProductOperation))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    MockHttpServletResponse servletResponse = mvcResult.getResponse();
                    String responseList = servletResponse.getContentAsString();
                    String[] stringSplit = responseList.split("},");
                    String newProduct = createdJsonProductOperation.replace("{", "").replace("}", "");
                    List<String> stringResponses = new ArrayList<String>();
                    stringResponses = Arrays.asList(stringSplit);
                    List<String> listClean = stringResponses.stream()
                            .map(s -> s.replace("{", ""))
                            .map(s -> s.replace("[", ""))
                            .map(s -> s.replace("}]", ""))
                            .filter(s -> s.equals(newProduct))
                            .collect(Collectors.toList());
                    assertEquals(listClean.isEmpty(), true );
                });
    }

}