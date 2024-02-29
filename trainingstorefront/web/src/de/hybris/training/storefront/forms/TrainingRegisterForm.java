package de.hybris.training.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TrainingRegisterForm extends RegisterForm {

    private String document;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
