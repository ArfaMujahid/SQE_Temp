package com.jtspringproject.JtSpringProject.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class ProductTest {
    /**
     * <ul>
     *   <li>{@link Product#setCategory(Category)}
     *   <li>{@link Product#setDescription(String)}
     *   <li>{@link Product#setId(int)}
     *   <li>{@link Product#setImage(String)}
     *   <li>{@link Product#setName(String)}
     *   <li>{@link Product#setPrice(int)}
     *   <li>{@link Product#setQuantity(int)}
     *   <li>{@link Product#setWeight(int)}
     *   <li>{@link Product#getCategory()}
     *   <li>{@link Product#getDescription()}
     *   <li>{@link Product#getId()}
     *   <li>{@link Product#getImage()}
     *   <li>{@link Product#getName()}
     *   <li>{@link Product#getPrice()}
     *   <li>{@link Product#getQuantity()}
     *   <li>{@link Product#getWeight()}
     * </ul>
     */
    @Test
    void testSetCategory() {
        Product product = new Product();

        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        Category actualCategory = product.getCategory();
        String actualDescription = product.getDescription();
        int actualId = product.getId();
        String actualImage = product.getImage();
        String actualName = product.getName();
        int actualPrice = product.getPrice();
        int actualQuantity = product.getQuantity();
        assertEquals("Image", actualImage);
        assertEquals("Name", actualName);
        assertEquals("The characteristics of someone or something", actualDescription);
        assertEquals(1, actualId);
        assertEquals(1, actualPrice);
        assertEquals(1, actualQuantity);
        assertEquals(3, product.getWeight());
        assertSame(category, actualCategory);
    }
}
