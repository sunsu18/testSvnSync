package com.sfr.engage.model.queries.uvo;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 21 14:37:31 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardUserRoleMappingVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        UsRoleId {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getUsRoleId();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setUsRoleId((DBSequence)value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        UserEmail {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getUserEmail();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setUserEmail((String)value);
            }
        }
        ,
        UserRole {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getUserRole();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setUserRole((String)value);
            }
        }
        ,
        AssociationType {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getAssociationType();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setAssociationType((String)value);
            }
        }
        ,
        UserAssociation {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getUserAssociation();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setUserAssociation((String)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtCardUserRoleMappingVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtCardUserRoleMappingVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardUserRoleMappingVORowImpl object);

        public abstract void put(PrtCardUserRoleMappingVORowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int USROLEID = AttributesEnum.UsRoleId.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int USEREMAIL = AttributesEnum.UserEmail.index();
    public static final int USERROLE = AttributesEnum.UserRole.index();
    public static final int ASSOCIATIONTYPE = AttributesEnum.AssociationType.index();
    public static final int USERASSOCIATION = AttributesEnum.UserAssociation.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardUserRoleMappingVORowImpl() {
    }

    /**
     * Gets PrtCardUserRoleMappingEO entity object.
     * @return the PrtCardUserRoleMappingEO
     */
    public EntityImpl getPrtCardUserRoleMappingEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for US_ROLE_ID using the alias name UsRoleId.
     * @return the US_ROLE_ID
     */
    public DBSequence getUsRoleId() {
        return (DBSequence)getAttributeInternal(USROLEID);
    }

    /**
     * Sets <code>value</code> as attribute value for US_ROLE_ID using the alias name UsRoleId.
     * @param value value to set the US_ROLE_ID
     */
    public void setUsRoleId(DBSequence value) {
        setAttributeInternal(USROLEID, value);
    }

    /**
     * Gets the attribute value for COUNTRY_CODE using the alias name CountryCode.
     * @return the COUNTRY_CODE
     */
    public String getCountryCode() {
        return (String) getAttributeInternal(COUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for COUNTRY_CODE using the alias name CountryCode.
     * @param value value to set the COUNTRY_CODE
     */
    public void setCountryCode(String value) {
        setAttributeInternal(COUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for USER_EMAIL using the alias name UserEmail.
     * @return the USER_EMAIL
     */
    public String getUserEmail() {
        return (String) getAttributeInternal(USEREMAIL);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_EMAIL using the alias name UserEmail.
     * @param value value to set the USER_EMAIL
     */
    public void setUserEmail(String value) {
        setAttributeInternal(USEREMAIL, value);
    }

    /**
     * Gets the attribute value for USER_ROLE using the alias name UserRole.
     * @return the USER_ROLE
     */
    public String getUserRole() {
        return (String) getAttributeInternal(USERROLE);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_ROLE using the alias name UserRole.
     * @param value value to set the USER_ROLE
     */
    public void setUserRole(String value) {
        setAttributeInternal(USERROLE, value);
    }

    /**
     * Gets the attribute value for ASSOCIATION_TYPE using the alias name AssociationType.
     * @return the ASSOCIATION_TYPE
     */
    public String getAssociationType() {
        return (String) getAttributeInternal(ASSOCIATIONTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for ASSOCIATION_TYPE using the alias name AssociationType.
     * @param value value to set the ASSOCIATION_TYPE
     */
    public void setAssociationType(String value) {
        setAttributeInternal(ASSOCIATIONTYPE, value);
    }

    /**
     * Gets the attribute value for USER_ASSOCIATION using the alias name UserAssociation.
     * @return the USER_ASSOCIATION
     */
    public String getUserAssociation() {
        return (String) getAttributeInternal(USERASSOCIATION);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_ASSOCIATION using the alias name UserAssociation.
     * @param value value to set the USER_ASSOCIATION
     */
    public void setUserAssociation(String value) {
        setAttributeInternal(USERASSOCIATION, value);
    }

    /**
     * Gets the attribute value for PARTNER_ID using the alias name PartnerId.
     * @return the PARTNER_ID
     */
    public String getPartnerId() {
        return (String) getAttributeInternal(PARTNERID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARTNER_ID using the alias name PartnerId.
     * @param value value to set the PARTNER_ID
     */
    public void setPartnerId(String value) {
        setAttributeInternal(PARTNERID, value);
    }

    /**
     * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @return the MODIFIED_BY
     */
    public String getModifiedBy() {
        return (String) getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @param value value to set the MODIFIED_BY
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**
     * Gets the attribute value for MODIFIED_DATE using the alias name ModifiedDate.
     * @return the MODIFIED_DATE
     */
    public Timestamp getModifiedDate() {
        return (Timestamp) getAttributeInternal(MODIFIEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_DATE using the alias name ModifiedDate.
     * @param value value to set the MODIFIED_DATE
     */
    public void setModifiedDate(Timestamp value) {
        setAttributeInternal(MODIFIEDDATE, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
