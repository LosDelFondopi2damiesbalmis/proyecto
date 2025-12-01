data class Local(
    val idLocal: Long,
    var nombre: String,
    var direccion: String,
    val empleados: MutableList<Empleado> = mutableListOf()
)