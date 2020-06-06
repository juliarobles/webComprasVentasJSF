/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprasventasweb.bean;

import comprasventasweb.dto.CategoriaDTO;
import comprasventasweb.dto.ProductoBasicoDTO;
import comprasventasweb.service.CategoriaService;
import comprasventasweb.service.ProductoService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Usuario
 */
@Named(value = "productosListarBean")
@RequestScoped
public class ProductosListarBean {

    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private ProductoService productoServices;
    
    @EJB
    private CategoriaService categoriaService;
    
    private static final Logger LOG = Logger.getLogger(ProductosListarBean.class.getName());
    
    protected List<ProductoBasicoDTO> listaProductos;
    protected List<CategoriaDTO> listaCategorias;
    protected String[][] listaBusquedas = {{"TituloDescripcion", "Título y descripción"}, {"Titulo","Título"}, {"Descripcion","Descripción"}, {"Etiqueta","Etiquetas"}, 
        {"FechaHora", "Fecha y hora"}, {"Fecha","Fecha"}, {"Hora","Hora"}};;
    protected String select;
    protected String search;
    protected String categoria;
    
    /**
     * Creates a new instance of ProductosListarBean
     */
    public ProductosListarBean() {
    }
    
    @PostConstruct
    public void init(){
        /*
        if (this.usuarioBean.usuario == null) { 
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {*/
                listaProductos = this.productoServices.searchAllInverso(); 
                listaCategorias = this.categoriaService.searchAll();
                select = "";
                search = "";
                categoria = null;
        //}
    }

    public String[][] getListaBusquedas() {
        return listaBusquedas;
    }

    public void setListaBusquedas(String[][] listaBusquedas) {
        this.listaBusquedas = listaBusquedas;
    }

    public List<ProductoBasicoDTO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoBasicoDTO> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<CategoriaDTO> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<CategoriaDTO> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public void doMostrarTodo(){
        listaProductos = this.productoServices.searchAllInverso(); 
    }
    
    public void doFiltrar(){
        if(search == null || search.trim().equals("") || select == null){
            select = "";
            search = "";
        }
        
        String[] words;
        String inicio;
        String end;
        String formato;
        
        listaProductos.clear();
        
        if(categoria == null){
            switch(select) {
                case "TituloDescripcion":
                    words = search.split(" ");
                    for (String word : words) {
                        List<ProductoBasicoDTO> listaWord = this.productoServices.searchByTituloDescripcion(word);
                        listaProductos.removeAll(listaWord);
                        listaProductos.addAll(listaWord);
                    }
                    break;
                    
                case "Titulo":
                    words = search.split(" ");
                    for (String word : words) {
                        List<ProductoBasicoDTO> listaWord = this.productoServices.searchByTitulo(word);
                        listaProductos.removeAll(listaWord);
                        listaProductos.addAll(listaWord);
                    }
                    break;
                    
                case "Descripcion":
                    words = search.split(" ");
                    for (String word : words) {
                        List<ProductoBasicoDTO> listaWord = this.productoServices.searchByDescripcion(word);
                        listaProductos.removeAll(listaWord);
                        listaProductos.addAll(listaWord);
                    }
                    break;
                    
                case "Etiqueta":
                    words = search.split(" ");
                    for (String pal : words) {
                        if(pal != null && pal.length() > 1 && pal.charAt(0) == '#'){
                            pal = pal.substring(1, pal.length());
                        }
                        List<ProductoBasicoDTO> listaWord = this.productoServices.searchByEtiquetas(pal);
                        listaProductos.removeAll(listaWord);
                        listaProductos.addAll(listaWord);
                    }
                    break;
                    
                case "FechaHora":
                    words = search.split("-");
                    inicio = words[0].trim();
                    formato = "\\d{2}/\\d{2}/\\d{4}\\s{1,}\\d{2}:\\d{2}";
                    if(search.matches(formato)){ //Fecha concreta
                        listaProductos = this.productoServices.searchByFechaHora(inicio);
                    }else if(search.matches(formato +"\\s*-\\s*"+ formato)){ //Periodo entre dos fechas
                        end = words[1].trim();
                        listaProductos = this.productoServices.searchByFechaHoraEntre(inicio, end);
                    }else{ //Formato incorrecto
                        listaProductos = null;
                    }
                    break;
                    
                case "Fecha":
                    words = search.split("-");
                    inicio = words[0].trim();
                    formato = "\\d{2}/\\d{2}/\\d{4}";
                    if(search.matches(formato)){ //Fecha concreta
                        listaProductos = this.productoServices.searchByFecha(inicio);
                    }else if(search.matches(formato +"\\s*-\\s*"+ formato)){ //Periodo entre dos fechas
                        end = words[1].trim();
                        listaProductos = this.productoServices.searchByFechaEntre(inicio, end);
                    }else{ //Formato incorrecto
                        listaProductos = null;
                    }
                    break;
                        
                case "Hora":
                    words = search.split("-");
                    inicio = words[0].trim();
                    formato = "\\d{2}:\\d{2}";
                    if(search.matches(formato)){ //Hora concreta del dia actual
                        listaProductos = this.productoServices.searchByHora(inicio);
                    }else if(search.matches(formato + "\\s*-\\s*" + formato)){ //Periodo entre dos horas del dia actual
                        end = words[1].trim();
                        listaProductos = this.productoServices.searchByHoraEntre(inicio, end);
                    }else{ //Formato Incorrecto
                        listaProductos = null;
                    }
                    break;
                    
                default:
                    listaProductos = this.productoServices.searchAllInverso(); 
            }
        } else {
            int id = Integer.parseInt(categoria.substring(1, categoria.length()));
            if(categoria.charAt(0) == 'B'){
                listaProductos = this.productoServices.searchBySubcategory(id);
            }else{
                listaProductos = this.productoServices.searchByCategory(id);
            }
        }
    }
    
}
