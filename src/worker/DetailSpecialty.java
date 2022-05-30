/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worker;

import javafx.scene.control.Button;

/**
 *
 * @author Admin
 */
public class DetailSpecialty {

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getMandatory_exams() {
        return mandatory_exams;
    }

    public void setMandatory_exams(String mandatory_exams) {
        this.mandatory_exams = mandatory_exams;
    }

    public String getChoice_exams() {
        return choice_exams;
    }

    public void setChoice_exams(String choice_exams) {
        this.choice_exams = choice_exams;
    }

    public String getEntrance_exams() {
        return entrance_exams;
    }

    public void setEntrance_exams(String entrance_exams) {
        this.entrance_exams = entrance_exams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEducation_id() {
        return education_id;
    }

    public void setEducation_id(String education_id) {
        this.education_id = education_id;
    }

    public String getEducation_name() {
        return education_name;
    }

    public void setEducation_name(String education_name) {
        this.education_name = education_name;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public DetailSpecialty(String id, String education_id, String education_name, String forma, String offer, String document_id, String document_name, String course, String term, String amount, String contract, String price, String mandatory_exams, String choice_exams, String entrance_exams, Button edit, String specialty) {
        this.id = id;
        this.education_id = education_id;
        this.education_name = education_name;
        this.forma = forma;
        this.offer = offer;
        this.document_id = document_id;
        this.document_name = document_name;
        this.course = course;
        this.term = term;
        this.amount = amount;
        this.contract = contract;
        this.price = price;
        this.mandatory_exams = mandatory_exams;
        this.choice_exams=choice_exams;
        this.entrance_exams = entrance_exams;
        this.edit = edit;
        this.specialty=specialty;
    }

    String id, education_id, education_name, forma, offer, document_id, document_name, course, term, amount, contract, price, mandatory_exams, choice_exams, entrance_exams, specialty;
    Button edit;
    
    
    
}
