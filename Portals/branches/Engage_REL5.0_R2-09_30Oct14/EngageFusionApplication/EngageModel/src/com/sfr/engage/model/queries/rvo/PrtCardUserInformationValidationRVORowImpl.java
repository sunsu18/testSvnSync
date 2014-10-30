package com.sfr.engage.model.queries.rvo;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 22 15:38:09 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardUserInformationValidationRVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        UserRole {
            public Object get(PrtCardUserInformationValidationRVORowImpl obj) {
                return obj.getUserRole();
            }

            public void put(PrtCardUserInformationValidationRVORowImpl obj, Object value) {
                obj.setUserRole((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardUserInformationValidationRVORowImpl object);

        public abstract void put(PrtCardUserInformationValidationRVORowImpl object, Object value);

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
    public static final int USERROLE = AttributesEnum.UserRole.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardUserInformationValidationRVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute UserRole.
     * @return the UserRole
     */
    public String getUserRole() {
        return (String) getAttributeInternal(USERROLE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UserRole.
     * @param value value to set the  UserRole
     */
    public void setUserRole(String value) {
        setAttributeInternal(USERROLE, value);
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