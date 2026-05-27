package com.proyecto.PeluPos.data.mocks.factura

import com.proyecto.PeluPos.data.services.facturas.FacturaProductoService
import com.proyecto.PeluPos.data.services.facturas.FacturaService
import com.proyecto.PeluPos.data.services.facturas.FacturaServicioService
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.FacturaProductoDto
import com.proyecto.PeluPos.models.FacturaServicioDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class FacturaRepository @Inject constructor(
    private val facturaService: FacturaService,
    // 🚀 NUEVO: Inyectamos los dos nuevos servicios
    private val facturaProductoService: FacturaProductoService,
    private val facturaServicioService: FacturaServicioService
) {

    // --- MÉTODOS ORIGINALES DE FACTURA ---

    suspend fun getFacturas(): List<Factura> {
        val response = facturaService.getFacturas()
        if (response.isSuccessful) {
            val dtos = response.body() ?: emptyList()
            return dtos.map { dto ->
                Factura(
                    idFactura = dto.idFactura,
                    monto = dto.monto,
                    fecha = parseFecha(dto.fecha),
                    pendiente = dto.pendiente,
                    tipoPago = dto.tipoPago,
                    cliente = dto.idCliente,
                    empleado = dto.idEmpleado,
                    // 🚀 USAMOS mapNotNull PARA EXTRAER LOS PRODUCTOS DE FORMA SEGURA
                    productos = dto.facturaProductoCollection.mapNotNull { it.producto }.toMutableList(),
                    servicios = dto.facturaServicioCollection.mapNotNull { it.servicio }.toMutableList()
                )
            }
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    suspend fun getFactura(id: Long): Factura? {
        val response = facturaService.getFactura(id)
        if (response.isSuccessful && response.body() != null) {
            val dto = response.body()!!
            return Factura(
                idFactura = dto.idFactura,
                monto = dto.monto,
                fecha = parseFecha(dto.fecha),
                pendiente = dto.pendiente,
                tipoPago = dto.tipoPago,
                cliente = dto.idCliente,
                empleado = dto.idEmpleado,
                productos = dto.facturaProductoCollection.mapNotNull { it.producto }.toMutableList(),
                servicios = dto.facturaServicioCollection.mapNotNull { it.servicio }.toMutableList()
            )
        } else if (response.code() == 404) {
            return null
        } else {
            throw Exception("Error al buscar la factura: ${response.code()}")
        }
    }

    suspend fun createFactura(factura: Factura): Factura {
        val requestDto = factura.toRequestDto()
        val response = facturaService.createFactura(requestDto)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("Error al crear: ${response.code()}")
        }
    }

    suspend fun updateFactura(id: Long, factura: Factura): Factura {
        val requestDto = factura.toRequestDto()
        val response = facturaService.updateFactura(id, requestDto)
        if (response.isSuccessful && response.body() != null) {
            return factura
        } else {
            throw Exception("Error del servidor al actualizar: ${response.code()}")
        }
    }

    suspend fun deleteFactura(id: Long) {
        val response = facturaService.deleteFactura(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo borrar la factura")
        }
    }

    private fun parseFecha(fechaString: String): Date {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            format.parse(fechaString) ?: Date()
        } catch (e: Exception) {
            Date()
        }
    }

    // --- 🚀 NUEVOS MÉTODOS PARA PRODUCTOS Y SERVICIOS ---

    suspend fun addProductoAFactura(facturaProducto: FacturaProductoDto) {
        val response = facturaProductoService.createFacturaProducto(facturaProducto)
        if (!response.isSuccessful) {
            throw Exception("Error al vincular el producto a la factura: ${response.code()}")
        }
    }
    suspend fun deleteFacturaProducto(idFactura: Long, idProducto: Long) {
        val response = facturaProductoService.deleteFacturaProducto(idFactura, idProducto)
        if (!response.isSuccessful) {
            throw Exception("Error al borrar el producto de la factura: ${response.code()}")
        }
    }
    suspend fun addServicioAFactura(facturaServicio: FacturaServicioDto) {
        val response = facturaServicioService.createFacturaServicio(facturaServicio)
        if (!response.isSuccessful) {
            throw Exception("Error al vincular el servicio a la factura: ${response.code()}")
        }
    }
    suspend fun deleteFacturaServicio(idFactura: Long, idServicio: Long) {
        val response = facturaServicioService.deleteFacturaServicio(idFactura, idServicio)
        if (!response.isSuccessful) {
            throw Exception("Error al borrar el servicio de la factura: ${response.code()}")
        }
    }
}