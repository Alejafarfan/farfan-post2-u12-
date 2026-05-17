package com.universidad.productosapi;

import com.universidad.productosapi.model.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductosApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void productoTieneNombreCorrect() {
        Producto p = new Producto("Laptop", "Laptop gamer", 1500.00);
        assertEquals("Laptop", p.getNombre());
    }

    @Test
    void productoPrecioEsPositivo() {
        Producto p = new Producto("Mouse", "Mouse inalámbrico", 25.00);
        assertTrue(p.getPrecio() > 0);
    }
}