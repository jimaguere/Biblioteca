/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblioteca.validators;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author mateo
 */
@FacesValidator("emailValidator")
public class EmailValidation implements Validator {

  
    @Override
    public void validate(FacesContext facesContext,UIComponent uIComponent,Object value) {
            Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");
            Matcher matcher = pattern.matcher(
                (CharSequence) value);
            HtmlInputText htmlInputText =
                (HtmlInputText) uIComponent;
        String label;
        if (htmlInputText.getLabel() == null
                || htmlInputText.getLabel().trim().equals("")) {
            label = htmlInputText.getId();
        } else {
            label = htmlInputText.getLabel();
        }
        if (!matcher.matches()) {
            FacesMessage facesMessage =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    label
                    + "",ResourceBundle.getBundle("/Bundle").getString("mensajeMailValidaitor"));
            throw new ValidatorException(facesMessage);
        }

    }
}
