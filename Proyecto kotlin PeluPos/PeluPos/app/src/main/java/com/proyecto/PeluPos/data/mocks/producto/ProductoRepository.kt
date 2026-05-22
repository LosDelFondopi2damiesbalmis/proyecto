package com.proyecto.PeluPos.data.mocks.producto

import com.proyecto.PeluPos.data.services.productos.ProductoService
import com.proyecto.PeluPos.models.Producto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepository @Inject constructor(
    private val api: ProductoService // Hilt nos inyecta esto automáticamente
) {

    // 1. LEER (GET) - Obtener todos los productos
    suspend fun obtenerProductos(): List<Producto> {
        val respuesta = api.getProductos()

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: emptyList()
        } else {
            throw java.lang.Exception("Error al obtener productos: ${respuesta.code()}")
        }
    }

    // 2. CREAR (POST) - Guardar un producto nuevo
    suspend fun crearProducto(nuevoProducto: Producto): Producto {
        val respuesta = api.crearProducto(nuevoProducto)

        if (respuesta.isSuccessful) {
            // Devolvemos el producto que nos responda el servidor (suele venir con el ID ya asignado por la BD)
            return respuesta.body() ?: throw java.lang.Exception("El servidor devolvió un cuerpo vacío")
        } else {
            throw java.lang.Exception("Error al crear el producto: ${respuesta.code()}")
        }
    }

    // 3. ACTUALIZAR (PUT) - Modificar un producto (Sin ID en la URL, va dentro del objeto)
    suspend fun actualizarProducto(productoModificado: Producto): Producto {
        val respuesta = api.actualizarProducto(productoModificado)

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: throw java.lang.Exception("El servidor devolvió un cuerpo vacío")
        } else {
            throw java.lang.Exception("Error al actualizar el producto: ${respuesta.code()}")
        }
    }

    // 4. BORRAR (DELETE) - Eliminar un producto por su ID
    suspend fun borrarProducto(id: Int) {
        val respuesta = api.borrarProducto(id)

        if (!respuesta.isSuccessful) {
            // Como el DELETE suele devolver un Response<Void> (vacío), solo nos importa si falló
            throw java.lang.Exception("Error al borrar el producto: ${respuesta.code()}")
        }
        // Si es exitoso, la función simplemente termina sin lanzar errores
    }
}
