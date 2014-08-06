package com.sfr.core.bean;

import java.io.Serializable;


public class AviationOrder extends Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private String deliveryTicket;
    private String CO2;
    private String creditCardNo;
    private String creditCardName;
    private String airlineNumber;
    private String airportIATACode;
    private Integer airlineShipToNo;
    private String aircraftRegistration;
    private String aircraftType;
    private String flightNumber;
    private String destination;
    private Integer startFuelTime;
    private Integer endFuelTime;
    private String nonEUCode;
    private String internationalFlag;
    private String VATFlag;
    private boolean VATApplicableFlag;
    private boolean co2Flag;
    private String taxExplanationCode;
    private String taxRateAreaCode;

    public void setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setStartFuelTime(Integer startFuelTime) {
        this.startFuelTime = startFuelTime;
    }

    public Integer getStartFuelTime() {
        return startFuelTime;
    }

    public void setEndFuelTime(Integer endFuelTime) {
        this.endFuelTime = endFuelTime;
    }

    public Integer getEndFuelTime() {
        return endFuelTime;
    }


    public void setDeliveryTicket(String deliveryTicket) {
        this.deliveryTicket = deliveryTicket;
    }

    public String getDeliveryTicket() {
        return deliveryTicket;
    }

    public void setCO2(String CO2) {
        this.CO2 = CO2;
    }

    public String getCO2() {
        return CO2;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setNonEUCode(String nonEUCode) {
        this.nonEUCode = nonEUCode;
    }

    public String getNonEUCode() {
        return nonEUCode;
    }

    public void setInternationalFlag(String internationalFlag) {
        this.internationalFlag = internationalFlag;
    }

    public String getInternationalFlag() {
        return internationalFlag;
    }

    public void setVATFlag(String VATFlag) {
        this.VATFlag = VATFlag;
    }

    public String getVATFlag() {
        return VATFlag;
    }

    public void setCo2Flag(boolean co2Flag) {
        this.co2Flag = co2Flag;
    }

    public boolean isCo2Flag() {
        return co2Flag;
    }

    public void setTaxExplanationCode(String taxExplanationCode) {
        this.taxExplanationCode = taxExplanationCode;
    }

    public String getTaxExplanationcode() {
        return taxExplanationCode;
    }

    public void setTaxRateAreaCode(String taxRateAreaCode) {
        this.taxRateAreaCode = taxRateAreaCode;
    }

    public String getTaxRateAreaCode() {
        return taxRateAreaCode;
    }

    public void setVATApplicableFlag(boolean VATApplicableFlag) {
        this.VATApplicableFlag = VATApplicableFlag;
    }

    public boolean isVATApplicableFlag() {
        return VATApplicableFlag;
    }

    public void setAirlineNumber(String airlineNumber) {
        this.airlineNumber = airlineNumber;
    }

    public String getAirlineNumber() {
        return airlineNumber;
    }

    public void setAirportIATACode(String airportIATACode) {
        this.airportIATACode = airportIATACode;
    }

    public String getAirportIATACode() {
        return airportIATACode;
    }

    public void setAirlineShipToNo(Integer airlineShipToNo) {
        this.airlineShipToNo = airlineShipToNo;
    }

    public Integer getAirlineShipToNo() {
        return airlineShipToNo;
    }
}
