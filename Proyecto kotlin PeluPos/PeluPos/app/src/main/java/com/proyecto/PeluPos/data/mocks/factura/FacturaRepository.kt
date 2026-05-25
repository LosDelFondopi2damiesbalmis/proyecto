package com.proyecto.PeluPos.data.mocks.factura

import com.proyecto.PeluPos.data.services.facturas.FacturaService
import com.proyecto.PeluPos.models.Factura
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class FacturaRepository @Inject constructor(
    private val facturaService: FacturaService
) {
    // En tu FacturaRepository.kt
    suspend fun getFacturas(): List<Factura> {
        val response = facturaService.getFacturas()
        if (response.isSuccessful) {
            val dtos = response.body() ?: emptyList()
            // 🚀 AQUÍ ESTÁ LA CLAVE: Mapeamos el DTO complejo a tu Factura simple
            return dtos.map { dto ->
                Factura(
                    idFactura = dto.idFactura,
                    monto = dto.monto,
                    fecha = parseFecha(dto.fecha), // Usa un SimpleDateFormat para el string
                    pendiente = dto.pendiente,
                    tipoPago = dto.tipoPago,
                    cliente = dto.idCliente, // O dto.idCliente.toDomain() si es necesario
                    empleado = dto.idEmpleado,
                    productos = dto.facturaProductoCollection.map { it.producto }.toMutableList(),
                    servicios = dto.facturaServicioCollection.map { it.servicio }.toMutableList()
                )
            }
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun getFactura(id: Long): Factura? {
        // 1. Llamamos al servicio esperando un DTO
        val response = facturaService.getFactura(id)

        if (response.isSuccessful && response.body() != null) {
            val dto = response.body()!!

            // 2. Mapeamos el DTO único a tu modelo de Factura simple
            return Factura(
                idFactura = dto.idFactura,
                monto = dto.monto,
                fecha = parseFecha(dto.fecha),
                pendiente = dto.pendiente,
                tipoPago = dto.tipoPago,
                cliente = dto.idCliente,
                empleado = dto.idEmpleado,
                productos = dto.facturaProductoCollection.map { it.producto }.toMutableList(),
                servicios = dto.facturaServicioCollection.map { it.servicio }.toMutableList()
            )
        } else if (response.code() == 404) {
            return null // Si no existe, devolvemos null tranquilamente
        } else {
            throw Exception("Error al buscar la factura: ${response.code()}")
        }
    }

    suspend fun createFactura(factura: Factura): Factura {
        // Usamos el mapper para convertir Factura -> FacturaRequestDto
        val requestDto = factura.toRequestDto()

        val response = facturaService.createFactura(requestDto)

        if (response.isSuccessful && response.body() != null) {
            // Aquí podrías convertir el resultado de vuelta a Factura si fuera necesario
            return factura
        } else {
            throw Exception("Error al crear: ${response.code()}")
        }
    }

    suspend fun updateFactura(id: Long, factura: Factura): Factura {
        // 1. Convertimos tu Factura (Domain) al formato plano que espera Tomcat
        val requestDto = factura.toRequestDto()

        // 2. Enviamos el DTO al servicio
        val response = facturaService.updateFactura(id, requestDto)

        // 3. Gestionamos la respuesta
        if (response.isSuccessful && response.body() != null) {
            // Aquí devuelves la factura actualizada.
            // Si el servidor te devuelve el objeto plano, podrías mapearlo de vuelta si fuera necesario.
            // Como ya tienes la factura original, retornar 'factura' suele ser suficiente.
            return factura
        } else {
            throw Exception("Error del servidor al actualizar: ${response.code()}")
        }
    }
    private fun parseFecha(fechaString: String): Date {
        return try {
            // Este formato "yyyy-MM-dd'T'HH:mm:ss" coincide con el que envías desde Tomcat
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            format.parse(fechaString) ?: Date()
        } catch (e: Exception) {
            Date() // Si falla por cualquier cosa, devuelve la fecha de hoy
        }
    }
    suspend fun deleteFactura(id: Long) {
        val response = facturaService.deleteFactura(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo borrar la factura")
        }
    }
}
