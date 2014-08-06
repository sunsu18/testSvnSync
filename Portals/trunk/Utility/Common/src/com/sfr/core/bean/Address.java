package com.sfr.core.bean;

import com.sfr.util.AccessDataControl;
import com.sfr.util.ThreadSerialization;

public class Address extends BaseBean {


    private static final long serialVersionUID = 1L;
    private Integer addressNumber;
    private String alphaName;
    private String street; // Not present in cust info wsdl not required
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String postalCode;
    private String city;
    private String countyName; // county
    private String countryDescription; // get country description from Item Master
    private String countryCode; //country code from jde ws
    private String telephone; //CustomerResults/soldTo/phoneNumber or shipTo /phoneNumber  (not present in customer info wsdl)
    private String classCode; // SH/SP/BP/P
    // Newly Added
    private String state;
    private String internetAddress;
    private String emailAddress;
    private String currencyCode; //for Webshop to display products according to the currencyCode
    private String actionType; // Action type from the Portal  U = Update
    private String specialTreatmentFlagStopCode;


    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    public String getCountryDescription() {
        return countryDescription;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAlphaName(String alphaName) {
        this.alphaName = alphaName;
    }

    public String getAlphaName() {
        return alphaName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setInternetAddress(String internetAddress) {
        this.internetAddress = internetAddress;
    }

    public String getInternetAddress() {
        return internetAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        sb.append("<Address Begins>");
        sb.append("  actionType=<" + this.actionType + ">");
        sb.append("  addressLine1=<" + this.addressLine1 + ">");
        sb.append("  addressLine2=<" + this.addressLine2 + ">");
        sb.append("  addressLine3=<" + this.addressLine3 + ">");
        sb.append("  addressLine4=<" + this.addressLine4 + ">");
        sb.append("  addressNumber=<" + this.addressNumber + ">");
        sb.append("  alphaName=<" + this.alphaName + ">");
        sb.append("  city=<" + this.city + ">");
        sb.append("  classCode=<" + this.classCode + ">");
        sb.append("  countryCode=<" + this.countryCode + ">");
        sb.append("  countryDescription=<" + this.countryDescription + ">");
        sb.append("  countyName=<" + this.countyName + ">");
        sb.append("  currencyCode=<" + this.currencyCode + ">");
        sb.append("  emailAddress=<" + this.emailAddress + ">");
        sb.append("  internetAddress=<" + this.internetAddress + ">");
        sb.append("  postalCode=<" + this.postalCode + ">");
        sb.append("  state=<" + this.state + ">");
        sb.append("  street=<" + this.street + ">");
        sb.append("  telephone=<" + this.telephone + ">");
        sb.append("  specialTreatmentFlagStopCode=<" +
                  this.specialTreatmentFlagStopCode + ">");
        sb.append("<Address Ends/>");
        return sb.toString();
    }

    public static void main(String[] args) {
        Address a = new Address();

    }

    @Override
    public boolean equals(Object obj) {
        boolean isAddrSame = true;
        if (obj != null) {
            if (obj instanceof Address) {
                Address objaddr = (Address)obj;
                if (objaddr.getAlphaName() != null &&
                    this.getAlphaName() != null &&
                    !objaddr.getAlphaName().equalsIgnoreCase(this.getAlphaName())) {
                    isAddrSame = false;
                } else if ((objaddr.getAlphaName() == null) !=
                           (this.getAlphaName() == null)) {
                    isAddrSame = false;
                }
                if (objaddr.getAddressLine1() != null &&
                    this.getAddressLine1() != null &&
                    !objaddr.getAddressLine1().equalsIgnoreCase(this.getAddressLine1())) {
                    isAddrSame = false;
                } else if ((objaddr.getAddressLine1() == null) !=
                           (this.getAddressLine1() == null)) {
                    isAddrSame = false;
                }
                if (objaddr.getAddressLine2() != null &&
                    this.getAddressLine2() != null &&
                    !objaddr.getAddressLine2().equalsIgnoreCase(this.getAddressLine2())) {
                    isAddrSame = false;
                } else if ((objaddr.getAddressLine2() == null) !=
                           (this.getAddressLine2() == null)) {
                    isAddrSame = false;
                }
                if (objaddr.getAddressLine3() != null &&
                    this.getAddressLine3() != null &&
                    !objaddr.getAddressLine3().equalsIgnoreCase(this.getAddressLine3())) {
                    isAddrSame = false;
                } else if ((objaddr.getAddressLine3() == null) !=
                           (this.getAddressLine3() == null)) {
                    isAddrSame = false;
                }
                if (objaddr.getAddressLine4() != null &&
                    this.getAddressLine4() != null &&
                    !objaddr.getAddressLine4().equalsIgnoreCase(this.getAddressLine4())) {
                    isAddrSame = false;
                } else if ((objaddr.getAddressLine4() == null) !=
                           (this.getAddressLine4() == null)) {
                    isAddrSame = false;
                }
                if (objaddr.getPostalCode() != null &&
                    this.getPostalCode() != null &&
                    !objaddr.getPostalCode().equalsIgnoreCase(this.getPostalCode())) {
                    isAddrSame = false;
                } else if ((objaddr.getPostalCode() == null) !=
                           (this.getPostalCode() == null)) {
                    isAddrSame = false;
                }
                if (objaddr.getCountryCode() != null &&
                    this.getCountryCode() != null &&
                    !objaddr.getCountryCode().equalsIgnoreCase(this.getCountryCode())) {
                    isAddrSame = false;
                } else if ((objaddr.getCountryCode() == null) !=
                           (this.getCountryCode() == null)) {
                    isAddrSame = false;
                }
                return isAddrSame;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int prime = 17;
        int result = 1;

        if (this.alphaName != null) {
            result += this.alphaName.hashCode() * prime;
        }
        if (this.addressLine1 != null) {
            result += this.addressLine1.hashCode() * prime;
        }
        if (this.addressLine2 != null) {
            result += this.addressLine2.hashCode() * prime;
        }
        if (this.addressLine3 != null) {
            result += this.addressLine3.hashCode() * prime;
        }
        if (this.addressLine4 != null) {
            result += this.addressLine4.hashCode() * prime;
        }
        if (this.postalCode != null) {
            result += this.postalCode.hashCode() * prime;
        }
        if (this.countryCode != null) {
            result += this.countryCode.hashCode() * prime;
        }
        return result;
    }

    public void setSpecialTreatmentFlagStopCode(String specialTreatmentFlagStopCode) {
        this.specialTreatmentFlagStopCode = specialTreatmentFlagStopCode;
    }

    public String getSpecialTreatmentFlagStopCode() {
        return specialTreatmentFlagStopCode;
    }
}
