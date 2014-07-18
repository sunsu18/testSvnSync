package e012portaltransactionreport;

public class TransactionReportInput {
    public TransactionReportInput() {
        super();
    }
    private String Country,Partner,Account,Type,ReportType,TransactionBasis,TransactionBasisValues,TransactionDateFrom,TransactionDateTo;

    public void setPartner(String Partner) {
        this.Partner = Partner;
    }

    public String getPartner() {
        return Partner;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String getAccount() {
        return Account;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getType() {
        return Type;
    }

    public void setReportType(String ReportType) {
        this.ReportType = ReportType;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setTransactionBasis(String TransactionBasis) {
        this.TransactionBasis = TransactionBasis;
    }

    public String getTransactionBasis() {
        return TransactionBasis;
    }

    public void setTransactionBasisValues(String TransactionBasisValues) {
        this.TransactionBasisValues = TransactionBasisValues;
    }

    public String getTransactionBasisValues() {
        return TransactionBasisValues;
    }

    public void setTransactionDateFrom(String TransactionDateFrom) {
        this.TransactionDateFrom = TransactionDateFrom;
    }

    public String getTransactionDateFrom() {
        return TransactionDateFrom;
    }

    public void setTransactionDateTo(String TransactionDateTo) {
        this.TransactionDateTo = TransactionDateTo;
    }

    public String getTransactionDateTo() {
        return TransactionDateTo;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getCountry() {
        return Country;
    }
}
