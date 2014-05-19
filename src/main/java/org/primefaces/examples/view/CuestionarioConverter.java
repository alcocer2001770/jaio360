/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.examples.view;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.primefaces.examples.domain.Cuestionario;

public class CuestionarioConverter implements Converter {

    public static List<Cuestionario> cuestionarioDB;

    static {
        cuestionarioDB = new ArrayList<Cuestionario>();

        cuestionarioDB.add(new Cuestionario("Cuestionario1"));
        cuestionarioDB.add(new Cuestionario("Cuestionario2"));
        cuestionarioDB.add(new Cuestionario("Cuestionario3"));
        cuestionarioDB.add(new Cuestionario("Cuestionario4"));
        cuestionarioDB.add(new Cuestionario("Cuestionario5"));
        cuestionarioDB.add(new Cuestionario("Cuestionario6"));
        cuestionarioDB.add(new Cuestionario("Cuestionario7"));
        cuestionarioDB.add(new Cuestionario("Cuestionario8"));
        cuestionarioDB.add(new Cuestionario("Cuestionario9"));
        cuestionarioDB.add(new Cuestionario("Cuestionario10"));
        cuestionarioDB.add(new Cuestionario("Cuestionario11"));
        cuestionarioDB.add(new Cuestionario("Cuestionario12"));
        cuestionarioDB.add(new Cuestionario("Cuestionario13"));
    }

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (Cuestionario p : cuestionarioDB) {
                    if (p.getNumber() == number) {
                        return p;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid cuestionario"));
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Cuestionario) value).getNumber());
        }
    }
}
