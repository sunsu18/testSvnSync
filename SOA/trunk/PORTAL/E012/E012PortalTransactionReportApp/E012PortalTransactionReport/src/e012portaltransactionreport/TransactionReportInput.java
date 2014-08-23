package e012portaltransactionreport;

/**
 */
public class TransactionReportInput {
    /**
     */
    public TransactionReportInput() {
        super();
    }
    private String country,partner,account,type,reportType,transactionBasis,transactionBasisValues,transactionDateFrom,transactionDateTo;

    /**
     * @param partner
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * @return
     */
    public String getPartner() {
        return partner;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param reportType
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * @return
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * @param transactionBasis
     */
    public void setTransactionBasis(String transactionBasis) {
        this.transactionBasis = transactionBasis;
    }

    /**
     * @return
     */
    public String getTransactionBasis() {
        return transactionBasis;
    }

    /**
     * @param transactionBasisValues
     */
    public void setTransactionBasisValues(String transactionBasisValues) {
        this.transactionBasisValues = transactionBasisValues;
    }

    /**
     * @return
     */
    public String getTransactionBasisValues() {
        return transactionBasisValues;
    }

    /**
     * @param transactionDateFrom
     */
    public void setTransactionDateFrom(String transactionDateFrom) {
        this.transactionDateFrom = transactionDateFrom;
    }

    /**
     * @return
     */
    public String getTransactionDateFrom() {
        return transactionDateFrom;
    }

    /**
     * @param transactionDateTo
     */
    public void setTransactionDateTo(String transactionDateTo) {
        this.transactionDateTo = transactionDateTo;
    }

    /**
     * @return
     */
    public String getTransactionDateTo() {
        return transactionDateTo;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return
     */
    public String getCountry() {
        return country;
    }
}
