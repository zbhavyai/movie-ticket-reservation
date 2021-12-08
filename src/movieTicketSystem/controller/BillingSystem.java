package movieTicketSystem.controller;

import movieTicketSystem.model.*;

/**
 * Class that emulates the function of an external Billing System to process the
 * payment
 */
public class BillingSystem {
    /**
     * Verifies the card details
     *
     * @param p   Payment object containing card details
     * @param cvc card CVC number
     * @return true if details are valid, false otherwise
     */
    private static boolean verifyCard(Payment p, String cvc) {
        // check if card is expired
        if (p.isExpired()) {
            return false;
        }

        // check if name is invalid
        if (p.getCardHolderName() == null || p.getCardHolderName().length() == 0) {
            return false;
        }

        // check if number is invalid
        if (p.getCardNum().length() != 16) {
            return false;
        }

        // check if cvc is invalid
        if (String.valueOf(cvc).length() != 3) {
            return false;
        }

        // check of cvc is only numbers
        try {
            Integer.parseInt(cvc);
        }

        catch(NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Verifies the payment and process the charge to the card
     *
     * @param p      Payment object containing card details
     * @param cvc    card CVC number
     * @param charge the amount to charge to the card
     * @return true if payment is successfully processed, false otherwise
     */
    public static boolean verifyPayment(Payment p, String cvc, double charge) {
        if (BillingSystem.verifyCard(p, cvc) == false) {
            return false;
        }

        // assume that the card always has the money to get charged

        return true;
    }
}
