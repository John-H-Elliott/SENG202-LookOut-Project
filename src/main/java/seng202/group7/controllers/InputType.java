package seng202.group7.controllers;

import javafx.css.PseudoClass;

/**
 * The types of input validators that can be applied to a Node.
 */
public enum InputType {
    DOUBLE (PseudoClass.getPseudoClass("double")),
    INTEGER (PseudoClass.getPseudoClass("integer")), 
    REQUIRED (PseudoClass.getPseudoClass("required")), 
    ID (PseudoClass.getPseudoClass("id")), 
    DATE (PseudoClass.getPseudoClass("dateEditor")), 
    TIME (PseudoClass.getPseudoClass("time"));

    private PseudoClass validationType;

    /**
     * Sets the type of validation that will be done when applied to a Node.
     * @param validationType The PseudoClass that determines what validation is required.
     */
    private InputType(PseudoClass validationType) {
        this.validationType = validationType;
    }

    /**
     * 
     * @returnThe type of validation that will be done when applied to a Node.
     */
    public PseudoClass getValidationType() {
        return this.validationType;
    }
}