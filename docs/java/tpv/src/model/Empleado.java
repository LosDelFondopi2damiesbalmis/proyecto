package model;

public class Empleado {
    private long idEmpleado;
    private long telefono;
    private String email;
    private String cargo;
    private String nombre;
    private Local local;

    public long getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public long getTelefono() {
        return telefono;
    }
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Local getLocal() {
        return local;
    }
    public void setLocal(Local local) {
        this.local = local;
    }

}
