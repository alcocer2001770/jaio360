package org.primefaces.examples.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class MultiSelectListboxBean {
    
    private List<SelectItem> categories;
    
    private String selection;

    @PostConstruct
    public void init() {
        categories = new ArrayList<SelectItem>();
        SelectItemGroup group1 = new SelectItemGroup("Competencia 1");
        SelectItemGroup group2 = new SelectItemGroup("Competencia 2");
        SelectItemGroup group3 = new SelectItemGroup("Competencia 3");
        SelectItemGroup group4 = new SelectItemGroup("Competencia 4");
        
        SelectItemGroup group11 = new SelectItemGroup("Pregunta 1.1");
        SelectItemGroup group12 = new SelectItemGroup("Pregunta 1.2");
        
        SelectItemGroup group21 = new SelectItemGroup("Pregunta 2.1");
        
        SelectItem option31 = new SelectItem("Pregunta 3.1", "Pregunta 3.1");
        SelectItem option32 = new SelectItem("Pregunta 3.2", "Pregunta 3.2");
        SelectItem option33 = new SelectItem("Pregunta 3.3", "Pregunta 3.3");
        SelectItem option34 = new SelectItem("Pregunta 3.4", "Pregunta 3.4");
        
        SelectItem option41 = new SelectItem("Pregunta 4.1", "Pregunta 4.1");
        
        SelectItem option111 = new SelectItem("Quien es el mas vago de tu grupo?");
        SelectItem option112 = new SelectItem("Ves que tu compi para en el facebook todo el dia?");
        group11.setSelectItems(new SelectItem[]{option111, option112});
        
        SelectItem option121 = new SelectItem("Te sientes a gusto con tu jefe?", "Te sientes a gusto con tu jefe?");
        SelectItem option122 = new SelectItem("Tu jefe es impasable?", "Tu jefe es impasable?");
        SelectItem option123 = new SelectItem("Te sientes en confianza con tu jefe?", "Te sientes en confianza con tu jefe?");
        group12.setSelectItems(new SelectItem[]{option121, option122, option123});
        
        SelectItem option211 = new SelectItem("Sientes que estas haciendo bien tu trabajo?", "Sientes que estas haciendo bien tu trabajo?");
        group21.setSelectItems(new SelectItem[]{option211});
        
        group1.setSelectItems(new SelectItem[]{group11, group12});
        group2.setSelectItems(new SelectItem[]{group21});
        group3.setSelectItems(new SelectItem[]{option31, option32, option33, option34});
        group4.setSelectItems(new SelectItem[]{option41});
        
        categories.add(group1);
        categories.add(group2);
        categories.add(group3);
        categories.add(group4);
    }
    
    public List<SelectItem> getCategories() {
        return categories;
    }    

    public String getSelection() {
        return selection;
    }
    public void setSelection(String selection) {
        this.selection = selection;
    }
}
