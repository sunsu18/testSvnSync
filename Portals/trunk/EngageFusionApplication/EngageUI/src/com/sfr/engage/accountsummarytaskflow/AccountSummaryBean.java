package com.sfr.engage.accountsummarytaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCardTypeNameMapVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class AccountSummaryBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    private String overviewText;

    private String id;

    private List<String> cardTypeList = new ArrayList<String>();

    private String accountId;
    private String cardGroupId;
    private String cardId;
    private String partnerId;
    private String partnerIdName;

    RowKeySetImpl rksImpl;

    private PartnerInfo partner = new PartnerInfo();
    private AccountInfo account = new AccountInfo();

    private List<AccountInfo> AccountList = new ArrayList<AccountInfo>();
    private List<PartnerInfo> partnerList = new ArrayList<PartnerInfo>();
    private List<CardGroupInfo> cardgrouplist = new ArrayList<CardGroupInfo>();
    private HttpServletRequest request;
    private ExternalContext ectx;
    private HttpSession session;
    private boolean displayAccountOverview = false;
    private boolean displayCardGroupOverview = false;
    private transient Bindings bindings;
    JUCtrlHierNodeBinding dropNodeParent;
    JUCtrlHierNodeBinding rootNode;
    AccessDataControl accessDC = new AccessDataControl();
    private boolean businessProfile = false;
    private boolean privateProfile = false;
    private String profile = "private";
    String cardType = "";
    private User userInfo;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    private String firstName = "";
    private String lastName = "";
    private List<AccountInfo> AccountListDefault =
        new ArrayList<AccountInfo>();
    private List<PartnerInfo> partnerListDefault =
        new ArrayList<PartnerInfo>();
    private List<CardGroupInfo> cardgrouplistDefault =
        new ArrayList<CardGroupInfo>();
    private List<CardGroupInfo> cardlistDefault =
        new ArrayList<CardGroupInfo>();
    private String partnerName = "";
    private String accountName = "";
    private String cardgroupName = "";
    private String cardName = "";
    private PartnerInfo partnerDefault = new PartnerInfo();
    private AccountInfo accountDefault = new AccountInfo();
    private boolean isPartner = false;
    private boolean ismanager = false;
    private boolean isEmployee = false;
    private boolean isManagerCg = false;
    private String displayCardTypeName = "";
    private Map<String, String> cardTypeNameMap;
    private String searchLevel;
    private String searchString;

    private List<String> cardTextline2SocList = new ArrayList<String>();
    private List<String> cardgroupsSocList = new ArrayList<String>();
    private List<String> cardsSocList = new ArrayList<String>();
    private List searchAttributes = new ArrayList();
    private RichTree tree1 = null;
    private String searchType = "CONTAIN";
    private RichInputText searchStringInputtext;
    private boolean executeSearch = false;
    private boolean hideblockedcards = false;
    private boolean displayactivecards = false;
    private boolean displaytempblockedcards = false;
    private boolean displayperblockedcards = false;
    private boolean displayperblockedandactivecards = false;
    private boolean displayperblockedandtempcards = false;
    private boolean displayallcards = true;
    private RichPanelGroupLayout noSearchResults;
    private EngageResourceBundle resourceBundle;
    private RichPanelGroupLayout treePanel;
    private List filterAttributes = new ArrayList();
    private RichTree blockcardshiddentree;
    private RichTree onlyactivecardstree;
    private RichTree onlytempblockedcardstree;
    private RichTree onlyblockedcardstree;
    private RichTree permblockedandactivecardstree;
    private RichTree permblockedandtempblockedcardstree;


    public AccountSummaryBean() {


        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Inside Constructor of Account Summary");
        RichTree tree = getBindings().getAdfTree();
        if (tree != null) {
            RowKeySet _disclosedRowKeys = tree.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in adf tree");

            tree.setDisclosedRowKeys(_disclosedRowKeys);
        } else {

            ectx = FacesContext.getCurrentInstance().getExternalContext();
            request = (HttpServletRequest)ectx.getRequest();
            session = request.getSession(false);

            if (session != null) {

                if (session.getAttribute("profile") != null) {

                    profile = (String)session.getAttribute("profile");

                    if (profile.equalsIgnoreCase("business")) {

                        businessProfile = true;
                        privateProfile = false;
                    } else if (profile.equalsIgnoreCase("private")) {

                        businessProfile = false;
                        privateProfile = true;

                    }
                }
            }

            if (session != null) {

                log.info(accessDC.getDisplayRecord() +
                         " session not null getting adfTree from session");
                if (session.getAttribute("adfTree") != null) {
                    tree = (RichTree)session.getAttribute("adfTree");
                    RowKeySet _disclosedRowKeys = tree.getDisclosedRowKeys();

                    if (_disclosedRowKeys != null &&
                        _disclosedRowKeys.size() > 0) {
                        _disclosedRowKeys.clear();
                    } else

                        log.info(accessDC.getDisplayRecord() +
                                 " No key to disclose in adf tree");
                    tree.setDisclosedRowKeys(_disclosedRowKeys);
                } else

                    log.info(accessDC.getDisplayRecord() +
                             " Session is not null but still adf tree is null it may be due to first hit on Account Summary");
            }

        }

        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Exiting from Constructor of Account Summary");

        if (session != null) {
            if (null != session.getAttribute(Constants.SESSION_USER_INFO))
                userInfo =
                        (User)session.getAttribute(Constants.SESSION_USER_INFO);

            if (userInfo != null) {

                firstName = userInfo.getFirstName();
                List<String> temp = new ArrayList<String>();
                List<String> temp1 = new ArrayList<String>();
                List<String> temp2 = new ArrayList<String>();
                List<String> temp3 = new ArrayList<String>();
                partnerListDefault =
                        (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
                for (int i = 0; i < userInfo.getRoleList().size(); i++) {
                    for (int j = 0;
                         j < userInfo.getRoleList().get(i).getIdString().size();
                         j++) {

                        if (userInfo.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                            isPartner = true;

                            if (partnerListDefault != null) {
                                for (int k = 0; k < partnerListDefault.size();
                                     k++) {
                                    int a =
                                        userInfo.getRoleList().get(i).getIdString().get(j).indexOf("PP");
                                    if (partnerListDefault.get(k).getPartnerValue().equals(userInfo.getRoleList().get(i).getIdString().get(j).substring(a +
                                                                                                                                                        2,
                                                                                                                                                        a +
                                                                                                                                                        10))) {
                                        temp.add(partnerListDefault.get(k).getPartnerName());
                                        partnerName =
                                                temp.toString().substring(1,
                                                                          temp.toString().length() -
                                                                          1).replace("",
                                                                                     "");
                                    }
                                }
                            }

                        }
                        if (userInfo.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR)) {
                            if (userInfo.getRoleList().get(i).getIdString().get(j).contains("AC")) {
                                ismanager = true;


                                int b =
                                    userInfo.getRoleList().get(i).getIdString().get(j).indexOf("AC");
                                temp1.add(userInfo.getRoleList().get(i).getIdString().get(j).substring(b +
                                                                                                       2,
                                                                                                       b +
                                                                                                       12));
                                accountName =
                                        temp1.toString().substring(1, temp1.toString().length() -
                                                                   1).replace("",
                                                                              "");

                            }
                            if (userInfo.getRoleList().get(i).getIdString().get(j).contains("CG")) {
                                isManagerCg = true;
                                if (partnerListDefault != null) {
                                    for (int k = 0;
                                         k < partnerListDefault.size(); k++) {
                                        int c =
                                            userInfo.getRoleList().get(i).getIdString().get(j).indexOf("CG");


                                        for (int ac = 0;
                                             ac < partnerListDefault.get(k).getAccountList().size();
                                             ac++) {
                                            for (int cg = 0;
                                                 cg < partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().size();
                                                 cg++) {

                                                if (partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().equals(userInfo.getRoleList().get(i).getIdString().get(j).substring(c +
                                                                                                                                                                                                                   2))) {
                                                    temp2.add(partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID());
                                                    cardgroupName =
                                                            temp2.toString().substring(1,
                                                                                       temp2.toString().length() -
                                                                                       1).replace("",
                                                                                                  "");
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        if (userInfo.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP)) {

                            if (userInfo.getRoleList().get(i).getIdString().get(j).contains("CC")) {
                                isEmployee = true;
                                if (partnerListDefault != null) {
                                    for (int k = 0;
                                         k < partnerListDefault.size(); k++) {
                                        int d =
                                            userInfo.getRoleList().get(i).getIdString().get(j).indexOf("CC");


                                        for (int ac = 0;
                                             ac < partnerListDefault.get(k).getAccountList().size();
                                             ac++) {
                                            for (int cg = 0;
                                                 cg < partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().size();
                                                 cg++) {

                                                for (int cc = 0;
                                                     cc < partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().size();
                                                     cc++) {


                                                    if (partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().equals(userInfo.getRoleList().get(i).getIdString().get(j).substring(d +
                                                                                                                                                                                                                                    2))) {
                                                        temp3.add(partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID());
                                                        cardName =
                                                                temp3.toString().substring(1,
                                                                                           temp3.toString().length() -
                                                                                           1).replace("",
                                                                                                      "");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                        }


                    }

                    if (partnerListDefault != null) {

                        for (int a = 0; a < partnerListDefault.size(); a++) {
                            if (partnerListDefault.get(a) != null &&
                                partnerListDefault.get(a).getAccountList() !=
                                null)
                                for (int b = 0;
                                     b < partnerListDefault.get(a).getAccountList().size();
                                     b++) {
                                    if (partnerListDefault.get(a).getAccountList().get(b) !=
                                        null &&
                                        partnerListDefault.get(a).getAccountList().get(b).getCardGroup() !=
                                        null)
                                        for (int c = 0;
                                             c < partnerListDefault.get(a).getAccountList().get(b).getCardGroup().size();
                                             c++) {
                                            if (partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c) !=
                                                null &&
                                                partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getDisplayCardGroupIdName() !=
                                                null) {
                                                cardgroupsSocList.add(partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getDisplayCardGroupIdName());
                                                if (partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getCard() !=
                                                    null)
                                                    for (int d = 0;
                                                         d < partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getCard().size();
                                                         d++)


                                                    {
                                                        if (partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getCard().get(d).getExternalCardID() !=
                                                            null)
                                                            cardsSocList.add(partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getCard().get(d).getExternalCardID());
                                                        if (partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getCard().get(d).getCardTextline2() !=
                                                            null)
                                                            cardTextline2SocList.add(partnerListDefault.get(a).getAccountList().get(b).getCardGroup().get(c).getCard().get(d).getCardTextline2());

                                                    }
                                            }
                                        }
                                }
                        }

                    }

                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " cardgroupsSocList size " +
                             cardgroupsSocList.size());
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " cardsSocList size " + cardsSocList.size());
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " cardtextline2 size " +
                             cardTextline2SocList.size());

                }


            }
        }


    }

    public void resetinputtext(ValueChangeEvent valueChangeEvent) {
        searchString = "";
        searchStringInputtext.resetValue();
        searchStringInputtext.setSubmittedValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchStringInputtext);
    }

    public void hideBlockedCards(ActionEvent actionEvent) {


        searchString = "";
        searchStringInputtext.resetValue();
        searchStringInputtext.setSubmittedValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchStringInputtext);
        searchLevel = "";

        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 "filterAttributes size is " + filterAttributes.size());

        String searchAttributeArray[] =
            (String[])filterAttributes.toArray(new String[filterAttributes.size()]);

        String listString = "";

        for (String s : searchAttributeArray) {
            listString += s + ",";
        }

        if (listString.contains("Active Cards") &&
            listString.contains("Softblock Cards") &&
            listString.contains("Hardblock Cards")) {
            hideblockedcards = false;
            displayallcards = true;
            displayactivecards = false;
            displaytempblockedcards = false;
            displayperblockedcards = false;
            displayperblockedandactivecards = false;
            displayperblockedandtempcards = false;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);
        } else if (listString.contains("Active Cards") &&
                   listString.contains("Softblock Cards")) {
            hideblockedcards = true;
            displayallcards = false;
            displayactivecards = false;
            displaytempblockedcards = false;
            displayperblockedcards = false;
            displayperblockedandactivecards = false;
            displayperblockedandtempcards = false;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);
        } else if (listString.contains("Active Cards") &&
                   listString.contains("Hardblock Cards")) {
            hideblockedcards = false;
            displayallcards = false;
            displayactivecards = false;
            displaytempblockedcards = false;
            displayperblockedcards = false;
            displayperblockedandactivecards = true;
            displayperblockedandtempcards = false;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);

        } else if (listString.contains("Softblock Cards") &&
                   listString.contains("Hardblock Cards")) {

            hideblockedcards = false;
            displayallcards = false;
            displayactivecards = false;
            displaytempblockedcards = false;
            displayperblockedcards = false;
            displayperblockedandactivecards = false;
            displayperblockedandtempcards = true;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);
        } else if (listString.contains("Active Cards")) {
            hideblockedcards = false;
            displayallcards = false;
            displayactivecards = true;
            displaytempblockedcards = false;
            displayperblockedcards = false;
            displayperblockedandactivecards = false;
            displayperblockedandtempcards = false;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);
        } else if (listString.contains("Softblock Cards")) {
            hideblockedcards = false;
            displayallcards = false;
            displayactivecards = false;
            displaytempblockedcards = true;
            displayperblockedcards = false;
            displayperblockedandactivecards = false;
            displayperblockedandtempcards = false;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);
        } else if (listString.contains("Hardblock Cards")) {
            hideblockedcards = false;
            displayallcards = false;
            displayactivecards = false;
            displaytempblockedcards = false;
            displayperblockedcards = true;
            displayperblockedandactivecards = false;
            displayperblockedandtempcards = false;
            hideAll();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);
        }


        RichTree treereset;
        treereset = getBindings().getAdfTree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in adf tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }

        treereset = getBlockcardshiddentree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in getBlockcardshiddentree tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }

        treereset = getOnlyactivecardstree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in getOnlyactivecardstree tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }

        treereset = getOnlytempblockedcardstree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in getOnlytempblockedcardstree tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }


        treereset = getOnlyblockedcardstree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in getOnlyblockedcardstree tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }


        treereset = getPermblockedandactivecardstree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in getPermblockedandactivecardstree tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }

        treereset = getPermblockedandtempblockedcardstree();
        if (treereset != null) {
            RowKeySet _disclosedRowKeys = treereset.getDisclosedRowKeys();

            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {
                _disclosedRowKeys.clear();
            } else
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " No key to disclose in getPermblockedandtempblockedcardstree tree");

            treereset.setDisclosedRowKeys(_disclosedRowKeys);

        }

    }

    public void searchTraverse(ActionEvent actionEvent) {
        filterAttributes = null;
        executeSearch = false;
        hideAll();

        hideblockedcards = false;
        displayallcards = true;
        displayactivecards = false;
        displaytempblockedcards = false;
        displayperblockedcards = false;
        displayperblockedandactivecards = false;
        displayperblockedandtempcards = false;
        hideAll();
        AdfFacesContext.getCurrentInstance().addPartialTarget(treePanel);

        if (searchLevel != null && searchLevel.trim() != null)
            if (searchLevel.equalsIgnoreCase("displayCardGroupIdName") ||
                searchLevel.equalsIgnoreCase("externalCardID") ||
                searchLevel.equalsIgnoreCase("cardTextline2")) {
                if (searchLevel.equalsIgnoreCase("displayCardGroupIdName")) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " inside searchTraverse CG level");
                    searchAttributes.clear();
                    searchAttributes.add("displayCardGroupIdName");

                    for (int p = 0;
                         p < cardgroupsSocList.size() && searchString != null;
                         p++) {
                        if (searchString.equalsIgnoreCase(cardgroupsSocList.get(p))) {
                            executeSearch = true;

                        }
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() +
                                 " executeSearch in cardgroup level" +
                                 executeSearch);

                    }


                } else if (searchLevel.equalsIgnoreCase("externalCardID")) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " inside searchTraverse externalCardID level");
                    searchAttributes.clear();
                    searchAttributes.add("externalCardID");
                    for (int p = 0;
                         p < cardsSocList.size() && searchString != null;
                         p++) {
                        if (searchString.equalsIgnoreCase(cardsSocList.get(p))) {
                            executeSearch = true;
                        }

                    }

                } else if (searchLevel.equalsIgnoreCase("cardTextline2")) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " inside searchTraverse cardtextline 2 level");
                    searchAttributes.clear();
                    searchAttributes.add("cardTextline2");

                    for (int p = 0;
                         p < cardTextline2SocList.size() && searchString !=
                         null; p++) {
                        if (searchString.equalsIgnoreCase(cardTextline2SocList.get(p))) {
                            executeSearch = true;
                        }

                    }
                }


                if (executeSearch) {
                    JUCtrlHierBinding treeBinding = null;

                    //get handle to tree if it does not exist. If tree component cannot be
                    //found in view, exit this function
                    if (tree1 == null) {
                        this.findTreeInView();
                        if (tree1 == null) {
                            //tree not found
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     " The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                            return;
                        } else
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " tree not null");
                    }
                    //Get the JUCtrlHierbinding reference from the PageDef
                    CollectionModel model = (CollectionModel)tree1.getValue();
                    treeBinding = (JUCtrlHierBinding)model.getWrappedData();

                    //Read the attributes to search in from the SelectManyChoice component
                    String searchAttributeArray[] =
                        (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

                    //Define a node to search in. In this example, the root node is used
                    JUCtrlHierNodeBinding root =
                        treeBinding.getRootNodeBinding();

                    //However, if the user used the "Show as Top" context menu option to
                    //shorten the tree display, then we only search starting from this top
                    //mode

                    List topNode = (List)tree1.getFocusRowKey();
                    if (topNode != null) {
                        //make top node the root node for the search
                        root = treeBinding.findNodeByKeyPath(topNode);
                    }

                    //Select the tree items that match the search criteria and expand the
                    //tree to display them
                    RowKeySet resultRowKeySet =
                        searchTreeNode(root, searchAttributeArray, searchType,
                                       searchString);
                    RowKeySet disclosedRowKeySet =
                        buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
                    tree1.setSelectedRowKeys(resultRowKeySet);
                    tree1.setDisclosedRowKeys(disclosedRowKeySet);

                    AdfFacesContext.getCurrentInstance().addPartialTarget(tree1);

                    Iterator rksIterator = resultRowKeySet.iterator();

                    if (rksIterator != null) {
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " iterator is " +
                                 rksIterator);
                    } else
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " Iterator null ");
                    while (rksIterator.hasNext()) {
                        List key1 = (List)rksIterator.next();
                        treeBinding = null;

                        CollectionModel collectionModel =
                            (CollectionModel)tree1.getValue();
                        treeBinding =
                                (JUCtrlHierBinding)collectionModel.getWrappedData();

                        RowKeySetImpl rksImpl;
                        rksImpl = new RowKeySetImpl();
                        JUCtrlHierNodeBinding nodeBinding1 = null;
                        nodeBinding1 = treeBinding.findNodeByKeyPath(key1);
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " Node Binding is  " +
                                 nodeBinding1);
                        rksImpl.add(key1);

                        rootNode = treeBinding.getRootNodeBinding();
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " rootNode " + rootNode);
                        dropNodeParent = nodeBinding1.getParent();
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " dropNodeParent " +
                                 dropNodeParent);

                        /* code added to expand and disclose the node from backing bean */

                        for (Object ob :
                             nodeBinding1.getParent().getAttributeValues()) {
                            if (ob != null) {
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " dropNodeParent after conversion " +
                                         ob.toString());
                            }
                            break;
                        }

                        for (Object o : nodeBinding1.getAttributeValues()) {

                            id = o.toString();
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + "  id is " + id);
                            break;


                        }
                        Row rw = null;
                        String rowType = "";
                        rw = nodeBinding1.getRow();
                        rowType = rw.getStructureDef().getDefName();


                        if (rowType.contains("AccountInfo")) {
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " rowType is Account");
                        } else if (rowType.contains("CardInfo")) {
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " rowType is Card");
                            getBindings().getDefaultPanel().setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());

                            for (Object o :
                                 nodeBinding1.getAttributeValues()) {

                                cardId = o.toString();
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " inside card overview id is " + id);

                            }
                            cardOverview();

                        } else if (rowType.contains("CardGroupInfo")) {
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     " rowType is cardGroupInfo");

                            //hiding default panel
                            getBindings().getDefaultPanel().setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
                            //clicked node belongs to cardGroup so execute cardGroup overview

                            for (Object o :
                                 nodeBinding1.getAttributeValues()) {

                                cardGroupId = o.toString();
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " inside cardgroup overview id is " +
                                         cardGroupId);

                            }
                            cardGroupOverview();

                        }


                    }

                } else {

                    if (tree1 != null) {
                        RowKeySet _disclosedRowKeys =
                            tree1.getDisclosedRowKeys();

                        if (_disclosedRowKeys != null &&
                            _disclosedRowKeys.size() > 0) {
                            _disclosedRowKeys.clear();

                        } else
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     " No key to disclose in adf tree");

                        tree1.setDisclosedRowKeys(_disclosedRowKeys);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(tree1);
                        if (getBindings().getAdfTree() != null)
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAdfTree());
                    }

                    noSearchResults.setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(noSearchResults);
                    getBindings().getDefaultPanel().setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
                }

            }

    }

    private void findTreeInView() {
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " search tree method called");
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        //hard coding tree component Id with its surrounding naming container ID
        //PanelCollection
        root.invokeOnComponent(fctx, "pt1:r1:0:t1", new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext,
                                                  UIComponent uiComponent) {
                    tree1 = (RichTree)uiComponent;
                }
            });
    }

    /**
     * Method that parses an ADF bound ADF Faces tree component to find search string matches
     * in one of the specified attribute names. Attribute names are ignored if they don't exist
     * in the search node. The method performs a recursiv search and returns a RowKeySet with the
     * row keys of all nodes that contain the search string
     * @param  node The JUCtrlHierNodeBinding instance to search
     * @param  searchAttributes An array of attribute names to search in
     * @param  searchType defines where the search is started within the text. Valid values are
     *         START, CONTAIN, END. If NULL the "CONTAIN" is set as the default
     * @param  searchString  The search condition
     * @return RowKeySet row keys
     */
    private RowKeySet searchTreeNode(JUCtrlHierNodeBinding node,
                                     String[] searchAttributes,
                                     String searchType, String searchString) {
        RowKeySetImpl rowKeys = new RowKeySetImpl();
        //set default search
        String _searchType =
            searchType == null ? "CONTAIN" : searchType.length() > 0 ?
                                             searchType : "CONTAIN";

        //Sanity checks
        if (node == null) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Node passed as NULL");
            return rowKeys;
        }
        if (searchAttributes == null || searchAttributes.length < 1) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     node.getName() +
                     ": search attribute is NULL or has a ZERO length");
            return rowKeys;
        }
        if (searchString == null || searchString.length() < 1) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     node.getName() +
                     ": Search string cannot be NULL or EMPTY");
            return rowKeys;
        }

        Row nodeRow = node.getRow();
        if (nodeRow != null) {
            for (int i = 0; i < searchAttributes.length; i++) {
                String compareString = "";
                try {
                    Object attribute =
                        nodeRow.getAttribute(searchAttributes[i]);
                    if (attribute instanceof String) {
                        compareString = (String)attribute;
                    } else {
                        //try the toString method as a simple fallback
                        compareString = attribute.toString();
                    }
                } catch (oracle.jbo.JboException attributeNotFound) {
                    //node does not have attribute. Exclude from search
                }
                //compare strings case insesitive.
                if (_searchType.equalsIgnoreCase("CONTAIN") &&
                    compareString.toUpperCase().indexOf(searchString.toUpperCase()) >
                    -1) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                } else if (_searchType.equalsIgnoreCase("START") &&
                           compareString.toUpperCase().startsWith(searchString.toUpperCase())) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                } else if (_searchType.equalsIgnoreCase("END") &&
                           compareString.toUpperCase().endsWith(searchString.toUpperCase())) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                }
            }
        }

        List<JUCtrlHierNodeBinding> children;
        children = node.getChildren();

        if (children != null) {
            for (JUCtrlHierNodeBinding _node : children) {
                //Each child search returns a row key set that must be added to the
                //row key set returned by the overall search
                RowKeySet rks =
                    searchTreeNode(_node, searchAttributes, this.searchType,
                                   searchString);
                if (rks != null && rks.size() > 0) {
                    rowKeys.addAll(rks);
                }
            }
        }
        return rowKeys;
    }

    /**
     * Helper method that returns a list of parent node for the RowKeySet passed
     * as the keys argument. The RowKeySet can be used to disclose the folders in
     * which the keys reside. Node that to disclose a full branch, all RowKeySet
     * that are in the path must be defined
     *
     * @param  treeBinding ADF tree binding instance read from the PageDef file
     * @param  keys  RowKeySet containing List entries of oracle.jbo.Key
     * @return RowKeySet of parent keys to disclose
     */
    private RowKeySet buildDiscloseRowKeySet(JUCtrlHierBinding treeBinding,
                                             RowKeySet keys) {
        RowKeySetImpl discloseRowKeySet = new RowKeySetImpl();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            List keyPath = (List)iter.next();
            JUCtrlHierNodeBinding node =
                treeBinding.findNodeByKeyPath(keyPath);
            if (node != null && node.getParent() != null &&
                !node.getParent().getKeyPath().isEmpty()) {
                //store the parent path
                discloseRowKeySet.add(node.getParent().getKeyPath());

                //call method recursively until no parents are found
                RowKeySetImpl parentKeySet = new RowKeySetImpl();
                parentKeySet.add(node.getParent().getKeyPath());
                RowKeySet rks =
                    buildDiscloseRowKeySet(treeBinding, parentKeySet);
                discloseRowKeySet.addAll(rks);
            }
        }
        return discloseRowKeySet;
    }

    public List suggesstedItemsResult(String string) {

        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();

        if (searchLevel != null && searchLevel.trim() != null) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " searchLevel" + getSearchLevel().toString());
            if (searchLevel.equalsIgnoreCase("displayCardGroupIdName")) {
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " cardgroup level selected");

                for (int z = 0; z < cardgroupsSocList.size(); z++) {
                    if (cardgroupsSocList.get(z).toUpperCase().contains(string.toUpperCase())) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(cardgroupsSocList.get(z));
                        selectItem.setValue(cardgroupsSocList.get(z));
                        selectItems.add(selectItem);
                    }

                }
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         "selectItems size " + selectItems.size());
            } else if (searchLevel.equalsIgnoreCase("externalCardID")) {
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " embossed card no level selected");
                for (int z = 0; z < cardsSocList.size(); z++) {
                    if (cardsSocList.get(z).toUpperCase().contains(string.toUpperCase())) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(cardsSocList.get(z));
                        selectItem.setValue(cardsSocList.get(z));
                        selectItems.add(selectItem);
                    }

                }
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         "selectItems size " + selectItems.size());
            } else if (searchLevel.equalsIgnoreCase("cardTextline2")) {
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " cardtextline level selected");
                for (int z = 0; z < cardTextline2SocList.size(); z++) {
                    if (cardTextline2SocList.get(z).toUpperCase().contains(string.toUpperCase())) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(cardTextline2SocList.get(z));
                        selectItem.setValue(cardTextline2SocList.get(z));
                        selectItems.add(selectItem);
                    }

                }
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         "selectItems size " + selectItems.size());

            }

        }

        return selectItems;
    }

    public void setPartner(PartnerInfo partner) {
        this.partner = partner;
    }

    public PartnerInfo getPartner() {
        return partner;
    }

    public void setAccount(AccountInfo account) {
        this.account = account;
    }

    public AccountInfo getAccount() {
        return account;
    }

    public void setBusinessProfile(boolean businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String close_Action() {
        getBindings().getShowAllPopUp().cancel();
        return null;
    }

    public boolean isBusinessProfile() {
        return businessProfile;
    }

    public void setPrivateProfile(boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    public boolean isPrivateProfile() {
        return privateProfile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }


    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setCardgroupName(String cardgroupName) {
        this.cardgroupName = cardgroupName;
    }

    public String getCardgroupName() {
        return cardgroupName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setIsPartner(boolean isPartner) {
        this.isPartner = isPartner;
    }

    public boolean isIsPartner() {
        return isPartner;
    }

    public void setIsmanager(boolean ismanager) {
        this.ismanager = ismanager;
    }

    public boolean isIsmanager() {
        return ismanager;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public boolean isIsEmployee() {
        return isEmployee;
    }

    public void setPartnerIdName(String partnerIdName) {
        this.partnerIdName = partnerIdName;
    }

    public String getPartnerIdName() {
        return partnerIdName;
    }

    public void setIsManagerCg(boolean isManagerCg) {
        this.isManagerCg = isManagerCg;
    }

    public boolean isIsManagerCg() {
        return isManagerCg;
    }

    public void setDisplayCardTypeName(String displayCardTypeName) {
        this.displayCardTypeName = displayCardTypeName;
    }

    public String getDisplayCardTypeName() {
        return displayCardTypeName;
    }

    public void setCardTypeNameMap(Map<String, String> cardTypeNameMap) {
        this.cardTypeNameMap = cardTypeNameMap;
    }

    public Map<String, String> getCardTypeNameMap() {
        return cardTypeNameMap;
    }

    public void setSearchLevel(String searchLevel) {
        this.searchLevel = searchLevel;
    }

    public String getSearchLevel() {
        return searchLevel;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setCardTextline2SocList(List<String> cardTextline2SocList) {
        this.cardTextline2SocList = cardTextline2SocList;
    }

    public List<String> getCardTextline2SocList() {
        return cardTextline2SocList;
    }

    public void setCardgroupsSocList(List<String> cardgroupsSocList) {
        this.cardgroupsSocList = cardgroupsSocList;
    }

    public List<String> getCardgroupsSocList() {
        return cardgroupsSocList;
    }

    public void setCardsSocList(List<String> cardsSocList) {
        this.cardsSocList = cardsSocList;
    }

    public List<String> getCardsSocList() {
        return cardsSocList;
    }

    public void setSearchAttributes(List searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    public List getSearchAttributes() {
        return searchAttributes;
    }

    /**
     * @param searchType
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setTree1(RichTree tree1) {
        this.tree1 = tree1;
    }

    public RichTree getTree1() {
        return tree1;
    }

    public void setSearchStringInputtext(RichInputText searchStringInputtext) {
        this.searchStringInputtext = searchStringInputtext;
    }

    public RichInputText getSearchStringInputtext() {
        return searchStringInputtext;
    }

    public void setExecuteSearch(boolean executeSearch) {
        this.executeSearch = executeSearch;
    }

    public boolean isExecuteSearch() {
        return executeSearch;
    }

    public void setNoSearchResults(RichPanelGroupLayout noSearchResults) {
        this.noSearchResults = noSearchResults;
    }

    public RichPanelGroupLayout getNoSearchResults() {
        return noSearchResults;
    }

    private void CallPrtCardsPerRVO(String paramValue, String partnerid,
                                    String accountid, String cgid) {
        String currentDate = "";
        String nextMonth = "";
        Date dateNow = new java.util.Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
        currentDate = dateformat.format(dateNow);
        Calendar cal = Calendar.getInstance();
        nextMonth = dateformat.format(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        nextMonth = dateformat.format(cal.getTime());
        ViewObject perCardVO =
            ADFUtils.getViewObject("PrtCardsPerRVO1Iterator");
        perCardVO.setNamedWhereClauseParam("countryCode",
                                           session.getAttribute(Constants.userLang));
        perCardVO.setNamedWhereClauseParam("paramValue", paramValue);
        perCardVO.setNamedWhereClauseParam("partnerid", partnerid);
        perCardVO.setNamedWhereClauseParam("accountid", accountid);
        perCardVO.setNamedWhereClauseParam("cgid", cgid);
        perCardVO.setNamedWhereClauseParam("currentDate", currentDate);
        perCardVO.setNamedWhereClauseParam("nextMonth", nextMonth);
        perCardVO.executeQuery();
    }

    public void setHideblockedcards(boolean hideblockedcards) {
        this.hideblockedcards = hideblockedcards;
    }

    public boolean isHideblockedcards() {
        return hideblockedcards;
    }

    public void setDisplayallcards(boolean displayallcards) {
        this.displayallcards = displayallcards;
    }

    public boolean isDisplayallcards() {
        return displayallcards;
    }

    public void setTreePanel(RichPanelGroupLayout treePanel) {
        this.treePanel = treePanel;
    }

    public RichPanelGroupLayout getTreePanel() {
        return treePanel;
    }

    public void setFilterAttributes(List filterAttributes) {
        this.filterAttributes = filterAttributes;
    }

    public List getFilterAttributes() {
        return filterAttributes;
    }

    public void setDisplayactivecards(boolean displayactivecards) {
        this.displayactivecards = displayactivecards;
    }

    public boolean isDisplayactivecards() {
        return displayactivecards;
    }

    public void setDisplaytempblockedcards(boolean displaytempblockedcards) {
        this.displaytempblockedcards = displaytempblockedcards;
    }

    public boolean isDisplaytempblockedcards() {
        return displaytempblockedcards;
    }

    public void setDisplayperblockedcards(boolean displayperblockedcards) {
        this.displayperblockedcards = displayperblockedcards;
    }

    public boolean isDisplayperblockedcards() {
        return displayperblockedcards;
    }

    public void setDisplayperblockedandactivecards(boolean displayperblockedandactivecards) {
        this.displayperblockedandactivecards = displayperblockedandactivecards;
    }

    public boolean isDisplayperblockedandactivecards() {
        return displayperblockedandactivecards;
    }

    public void setDisplayperblockedandtempcards(boolean displayperblockedandtempcards) {
        this.displayperblockedandtempcards = displayperblockedandtempcards;
    }

    public boolean isDisplayperblockedandtempcards() {
        return displayperblockedandtempcards;
    }

    public void setBlockcardshiddentree(RichTree blockcardshiddentree) {
        this.blockcardshiddentree = blockcardshiddentree;
    }

    public RichTree getBlockcardshiddentree() {
        return blockcardshiddentree;
    }

    public void setOnlyactivecardstree(RichTree onlyactivecardstree) {
        this.onlyactivecardstree = onlyactivecardstree;
    }

    public RichTree getOnlyactivecardstree() {
        return onlyactivecardstree;
    }

    public void setOnlytempblockedcardstree(RichTree onlytempblockedcardstree) {
        this.onlytempblockedcardstree = onlytempblockedcardstree;
    }

    public RichTree getOnlytempblockedcardstree() {
        return onlytempblockedcardstree;
    }

    public void setOnlyblockedcardstree(RichTree onlyblockedcardstree) {
        this.onlyblockedcardstree = onlyblockedcardstree;
    }

    public RichTree getOnlyblockedcardstree() {
        return onlyblockedcardstree;
    }

    public void setPermblockedandactivecardstree(RichTree permblockedandactivecardstree) {
        this.permblockedandactivecardstree = permblockedandactivecardstree;
    }

    public RichTree getPermblockedandactivecardstree() {
        return permblockedandactivecardstree;
    }

    public void setPermblockedandtempblockedcardstree(RichTree permblockedandtempblockedcardstree) {
        this.permblockedandtempblockedcardstree =
                permblockedandtempblockedcardstree;
    }

    public RichTree getPermblockedandtempblockedcardstree() {
        return permblockedandtempblockedcardstree;
    }


    public class Bindings {
        private RichPanelGroupLayout accountOverview;
        private RichPanelGroupLayout cardGroupOverview;
        private RichPanelGroupLayout cardOverview;
        private RichPanelGroupLayout companyOverview;
        private RichPanelGroupLayout restrictedAccess;
        private RichTree adfTree;
        private RichOutputText cardTypeOT;
        private RichPopup showAllPopUp;
        private RichPanelGroupLayout defaultPanel;

        public void setDefaultPanel(RichPanelGroupLayout defaultPanel) {
            this.defaultPanel = defaultPanel;
        }

        public RichPanelGroupLayout getDefaultPanel() {
            return defaultPanel;
        }

        public void setCardTypeOT(RichOutputText cardTypeOT) {
            this.cardTypeOT = cardTypeOT;
        }

        public RichOutputText getCardTypeOT() {
            return cardTypeOT;
        }

        public void setShowAllPopUp(RichPopup showAllPopUp) {
            this.showAllPopUp = showAllPopUp;
        }

        public RichPopup getShowAllPopUp() {
            return showAllPopUp;
        }

        public void setAccountOverview(RichPanelGroupLayout accountOverview) {
            this.accountOverview = accountOverview;
        }

        public RichPanelGroupLayout getAccountOverview() {
            return accountOverview;
        }

        public void setCardGroupOverview(RichPanelGroupLayout cardGroupOverview) {
            this.cardGroupOverview = cardGroupOverview;
        }

        public RichPanelGroupLayout getCardGroupOverview() {
            return cardGroupOverview;
        }

        public void setCardOverview(RichPanelGroupLayout cardOverview) {
            this.cardOverview = cardOverview;
        }

        public RichPanelGroupLayout getCardOverview() {
            return cardOverview;
        }

        public void setCompanyOverview(RichPanelGroupLayout companyOverview) {
            this.companyOverview = companyOverview;
        }

        public RichPanelGroupLayout getCompanyOverview() {
            return companyOverview;
        }


        public void setRestrictedAccess(RichPanelGroupLayout restrictedAccess) {
            this.restrictedAccess = restrictedAccess;
        }

        public RichPanelGroupLayout getRestrictedAccess() {
            return restrictedAccess;
        }


        public void setAdfTree(RichTree adfTree) {
            this.adfTree = adfTree;
        }

        public RichTree getAdfTree() {
            return adfTree;
        }


    }


    public void treeListner(SelectionEvent selectionEvent) {
        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Entering in tree selection listner ");

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);

        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " Inside tree listner");

        JUCtrlHierNodeBinding nodeBinding1 = null;
        Row rw = null;
        String rowType = "";
        RichTree tree1 = (RichTree)selectionEvent.getSource();
        if (tree1 != null) {

            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Tree is not null in selection listner");
            if (session != null) {
                session.setAttribute("adfTree", tree1);

                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " adftree stored in session");


            } else
                log.warning(accessDC.getDisplayRecord() + this.getClass() +
                            " Session null and adf tree not stored in session");


        } else
            log.warning(accessDC.getDisplayRecord() + this.getClass() +
                        " Tree is null in selection listner also");


        RowKeySet rowKeySet1 = selectionEvent.getAddedSet();
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " RowKeySet " + rowKeySet1);

        Iterator rksIterator = rowKeySet1.iterator();
        while (rksIterator.hasNext()) {
            List key1 = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            JUCtrlHierBinding treeBinding2 = null;
            CollectionModel collectionModel =
                (CollectionModel)tree1.getValue();
            treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
            treeBinding2 = (JUCtrlHierBinding)collectionModel.getRowData();
            rksImpl = new RowKeySetImpl();
            nodeBinding1 = treeBinding.findNodeByKeyPath(key1);
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Node Binding 1 " + nodeBinding1);


            rksImpl.add(key1);

            rootNode = treeBinding.getRootNodeBinding();
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " rootNode " + rootNode);
            dropNodeParent = nodeBinding1.getParent();
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " dropNodeParent " + dropNodeParent);


            for (Object ob : nodeBinding1.getParent().getAttributeValues()) {
                if (ob != null) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " dropNodeParent after conversion " +
                             ob.toString());
                }
                break;
            }

            for (Object o : nodeBinding1.getAttributeValues()) {

                id = o.toString();
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         "  id is " + id);
                break;


            }
            if (session != null) {

                if (session.getAttribute("Partner_Object_List") != null) {

                    partnerList =
                            (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
                    if (partnerList != null) {
                        for (int k = 0; k < partnerList.size(); k++) {
                            if (partnerList.get(k).getPartnerValue().toString().equals(id)) {
                                partner = partnerList.get(k);
                                AccountList = partner.getAccountList();
                            }

                        }

                    }

                }


            }


            rw = nodeBinding1.getRow();
            rowType = rw.getStructureDef().getDefName();

        }

        if (rowType.contains("AccountInfo")) {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            //clicked node belongs to account so execute Account overview
            accountOverview();


        } else if (rowType.contains("CardGroupInfo")) {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            //clicked node belongs to cardGroup so execute cardGroup overview

            for (Object o : nodeBinding1.getAttributeValues()) {

                cardGroupId = o.toString();
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " inside cardgroup overview id is " + cardGroupId);

            }
            cardGroupOverview();

        } else if (rowType.contains("CardInfo")) {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            //clicked node belongs to card so execute card overview
            for (Object o : nodeBinding1.getAttributeValues()) {
                cardId = o.toString();
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " inside card overview id is " + id);
            }
            cardOverview();

        } else {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());

            for (Object o : nodeBinding1.getAttributeValues()) {

                partnerIdName = o.toString();
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " partnerIdName is " + id);

            }
            companyOverview();

        }

        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Exiting from tree selection listner ");
    }


    public void processAttributeChange() {

    }


    public void setOverviewText(String overviewText) {
        this.overviewText = overviewText;
    }

    public String getOverviewText() {
        return overviewText;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setCardTypeList(ArrayList<String> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public List<String> getCardTypeList() {
        return cardTypeList;
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setCardGroupId(String cardGroupId) {
        this.cardGroupId = cardGroupId;
    }

    public String getCardGroupId() {
        return cardGroupId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setDisplayAccountOverview(boolean displayAccountOverview) {
        this.displayAccountOverview = displayAccountOverview;
    }

    public boolean isDisplayAccountOverview() {
        return displayAccountOverview;
    }

    public void setDisplayCardGroupOverview(boolean displayCardGroupOverview) {
        this.displayCardGroupOverview = displayCardGroupOverview;
    }

    public boolean isDisplayCardGroupOverview() {
        return displayCardGroupOverview;
    }


    public void hideAll() {

        getBindings().getAccountOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountOverview());
        getBindings().getCompanyOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCompanyOverview());
        getBindings().getCardOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardOverview());
        getBindings().getCardGroupOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupOverview());
        noSearchResults.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(noSearchResults);

    }

    public AccountSummaryBean.Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public void accountOverview() {
        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Entering in account overview function ");

        hideAll();

        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());


        partnerId = dropNodeParent.toString();
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " partnerId inside accountoverview " + partnerId);

        partnerIdName = partnerId.substring(partnerId.indexOf(" ") + 1);

        partnerId = partnerId.substring(0, partnerId.indexOf(" "));
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " partnerId inside accountoverview " + partnerId);


        accountId = id;

        if (partnerList != null) {
            for (int k = 0; k < partnerList.size(); k++) {
                if (partnerList.get(k).getPartnerValue().toString().equals(partnerId)) {
                    partner = partnerList.get(k);
                    AccountList = partner.getAccountList();
                }

            }

        }
        for (int k = 0; k < AccountList.size(); k++) {

            if (AccountList.get(k).getAccountNumber().equalsIgnoreCase(id)) {
                displayAccountOverview =
                        AccountList.get(k).isAccountOverview();
                break;
            }
        }


        if (displayAccountOverview) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Account node clicked, Account Overview is true in partner object " +
                     partner.getPartnerValue() + " & Accountid " + id);

            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;

            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtAccountVO2Iterator");

            } else {
                log.severe(accessDC.getDisplayRecord() + this.getClass() +
                           " account bindings inside account Overview is null");

                iter1 = null;
            }

            if (iter1 != null) {


                ViewObject accountVO = iter1.getViewObject();

                if (("PARTNER_ID =: pid").equalsIgnoreCase(accountVO.getWhereClause())) {

                    accountVO.removeNamedWhereClauseParam("pid");

                    accountVO.setWhereClause("");
                    accountVO.executeQuery();


                }
                accountVO.setWhereClause("ACCOUNT_ID =: accid");
                accountVO.defineNamedWhereClauseParam("accid", id, null);
                accountVO.setNamedWhereClauseParam("countryCode",
                                                   session.getAttribute(Constants.userLang));

                accountVO.executeQuery();

            }
            getBindings().getAccountOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountOverview());

            CallPrtCardsPerRVO("account", partnerId, id, "");


        } else {
            hideAll();

            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Account node clicked But Account Overview is false in partner object " +
                     partner.getPartnerValue() + " & Accountid " + id);
            getBindings().getRestrictedAccess().setVisible(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }

        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Exiting from AccountOverview function ");
    }

    public void cardGroupOverview() {

        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Entering in cardGroupOverview function ");

        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

        partnerId = dropNodeParent.getParent().toString();
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " partnerId inside cardgroupoverview " + partnerId);

        partnerIdName = partnerId.substring(partnerId.indexOf(" ") + 1);
        partnerId = partnerId.substring(0, partnerId.indexOf(" "));
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " partnerId inside cardgroupoverview " + partnerId);

        accountId = dropNodeParent.toString();

        if (session != null) {

            if (session.getAttribute("Partner_Object_List") != null) {

                partnerList =
                        (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
                if (partnerList != null) {
                    for (int k = 0; k < partnerList.size(); k++) {
                        if (partnerList.get(k).getPartnerValue().toString().equals(id)) {
                            partner = partnerList.get(k);
                            AccountList = partner.getAccountList();
                        }

                    }

                }

            }


        }

        if (partnerList != null) {
            for (int k = 0; k < partnerList.size(); k++) {
                if (partnerList.get(k).getPartnerValue().toString().equals(partnerId)) {
                    partner = partnerList.get(k);
                    AccountList = partner.getAccountList();
                }

            }

        }


        for (int k = 0; k < AccountList.size(); k++) {
            account = AccountList.get(k);
            cardgrouplist = account.getCardGroup();

            for (int cardgrp_count = 0; cardgrp_count < cardgrouplist.size();
                 cardgrp_count++) {
                if (cardgrouplist.get(cardgrp_count).getCardGroupID().equalsIgnoreCase(id)) {

                    displayCardGroupOverview =
                            cardgrouplist.get(cardgrp_count).isCardGroupOverview();
                    break;
                }
            }
        }


        if (displayCardGroupOverview) {


            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " cardGroup node clicked, cardGroup Overview is true in partner object " +
                     partner.getPartnerValue() + " & cardGroupId " + id);
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;


            if (bindings != null) {
                iter1 =
                        bindings.findIteratorBinding("PrtCardgroupVO3Iterator");

            } else {

                log.severe(accessDC.getDisplayRecord() + this.getClass() +
                           " card bindings inside cardGroup Overview is null ");
                iter1 = null;
            }

            if (iter1 != null) {

                ViewObject cardGroupVO = iter1.getViewObject();


                if (("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc").equalsIgnoreCase(cardGroupVO.getWhereClause())) {

                    cardGroupVO.removeNamedWhereClauseParam("accid");
                    cardGroupVO.removeNamedWhereClauseParam("cc");
                    cardGroupVO.setWhereClause("");
                    cardGroupVO.executeQuery();
                }
                String maintype = id.substring(0, 3);

                String subtype = id.substring(3, 6);

                String cardgroupseq = id.substring(6);


                cardGroupVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub AND ACCOUNT_ID=: acid");


                cardGroupVO.defineNamedWhereClauseParam("cgid", cardgroupseq,
                                                        null);

                cardGroupVO.defineNamedWhereClauseParam("cc",
                                                        session.getAttribute(Constants.userLang),
                                                        null);
                cardGroupVO.defineNamedWhereClauseParam("cgmain", maintype,
                                                        null);
                cardGroupVO.defineNamedWhereClauseParam("cgsub", subtype,
                                                        null);
                cardGroupVO.defineNamedWhereClauseParam("acid", accountId,
                                                        null);


                cardGroupVO.executeQuery();


                bindings =
                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter3;
                if (bindings != null) {
                    iter3 = bindings.findIteratorBinding("PrtCardVO4Iterator");

                } else {
                    log.severe(accessDC.getDisplayRecord() + this.getClass() +
                               " card bindings in PrtCardVO4Iterator is null");

                    iter3 = null;
                }

                ViewObject cardVO = iter3.getViewObject();

                if ("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc".equalsIgnoreCase(cardVO.getWhereClause())) {

                    cardVO.removeNamedWhereClauseParam("cardid");
                    cardVO.removeNamedWhereClauseParam("cc");
                    cardVO.setWhereClause("");
                    cardVO.executeQuery();
                }


                cardVO = iter3.getViewObject();
                cardVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub AND ACCOUNT_ID=: acid");
                cardVO.defineNamedWhereClauseParam("cgid", cardgroupseq, null);

                cardVO.defineNamedWhereClauseParam("cc",
                                                   session.getAttribute(Constants.userLang),
                                                   null);
                cardVO.defineNamedWhereClauseParam("cgmain", maintype, null);
                cardVO.defineNamedWhereClauseParam("cgsub", subtype, null);
                cardVO.defineNamedWhereClauseParam("acid", accountId, null);


                cardVO.executeQuery();

                if (cardVO.getEstimatedRowCount() != 0) {
                    cardTypeList = new ArrayList<String>();
                    while (cardVO.hasNext()) {
                        PrtCardVORowImpl currRow =
                            (PrtCardVORowImpl)cardVO.next();
                        if (currRow.getCardType() != null) {
                            if (!cardTypeList.contains(currRow.getCardType()) &&
                                currRow.getBlockLevel().toString().equalsIgnoreCase("KSI") &&
                                !currRow.getBlockAction().toString().equalsIgnoreCase("2")) {
                                cardTypeList.add(currRow.getCardType());
                            }
                            String card = cardTypeList.toString();
                            cardType =
                                    card.substring(1, card.length() - 1).replace(" ",
                                                                                 "");
                        }
                    }
                }
                ViewObject vo =
                    ADFUtils.getViewObject("PrtCardTypeNameMap1Iterator");
                vo.setNamedWhereClauseParam("country",
                                            session.getAttribute(Constants.userLang));
                vo.setNamedWhereClauseParam("cardType", cardType);
                vo.executeQuery();
                cardTypeNameMap = new HashMap<String, String>();
                if (vo.getEstimatedRowCount() != 0) {

                    while (vo.hasNext()) {
                        PrtCardTypeNameMapVORowImpl currRow =
                            (PrtCardTypeNameMapVORowImpl)vo.next();
                        if (currRow.getTypeName() != null &&
                            currRow.getCardType() != null) {
                            cardTypeNameMap.put(currRow.getCardType(),
                                                currRow.getTypeName());

                        }

                    }


                }

                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " CardType List size inside cardgroup Overview " +
                         cardTypeList.size());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowAllPopUp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardTypeOT());

                for (int i = 0; i < cardTypeList.size(); i++) {
                    if (cardTypeNameMap.size() > 0 &&
                        cardTypeNameMap.get(cardTypeList.get(i)) != null) {
                        displayCardTypeName =
                                displayCardTypeName + cardTypeList.get(i) +
                                " - " +
                                cardTypeNameMap.get(cardTypeList.get(i)) +
                                "<br/>";
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() +
                                 " displayCardTypeName-------------->" +
                                 displayCardTypeName);
                    } else {
                        displayCardTypeName =
                                displayCardTypeName + cardTypeList.get(i) +
                                "<br/>";
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() +
                                 " displayCardTypeName-------------->" +
                                 displayCardTypeName);
                    }
                }
                if (cardTypeList.size() > 0)
                    displayCardTypeName =
                            displayCardTypeName.substring(0, displayCardTypeName.length() -
                                                          5);

            }


            CallPrtCardsPerRVO("cardgroup", partnerId, accountId, id);

            getBindings().getCardGroupOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupOverview());


        } else {

            hideAll();

            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " cardGroup node clicked But cardGroup Overview is false in partner object " +
                     partner.getPartnerValue() + " & cardGroupId " + id);
            getBindings().getRestrictedAccess().setVisible(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }


        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Exiting cardGroupOverview function ");

    }

    public void cardOverview() {

        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Inside cardOverview function");


        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());


        for (Object ob : dropNodeParent.getAttributeValues()) {
            cardGroupId = ob.toString();
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " cardgroupID after conversion " + cardGroupId);

        }

        dropNodeParent = dropNodeParent.getParent();
        accountId = dropNodeParent.toString();
        partnerId = dropNodeParent.getParent().toString();
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " partnerId inside cardoverview " + partnerId);
        partnerIdName = partnerId.substring(partnerId.indexOf(" ") + 1);

        partnerId = partnerId.substring(0, partnerId.indexOf(" "));
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " partnerId inside cardoverview " + partnerId);


        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " card node clicked for partner object" +
                 partner.getPartnerValue() + " & cardid " + id);

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;


        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtCardVO4Iterator");

        } else {

            log.warning(accessDC.getDisplayRecord() + this.getClass() +
                        " card bindings inside card Overview is null");
            iter1 = null;
        }

        if (iter1 != null) {

            ViewObject cardVO = iter1.getViewObject();

            if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub AND ACCOUNT_ID=: acid".equalsIgnoreCase(cardVO.getWhereClause())) {

                cardVO.removeNamedWhereClauseParam("cgid");
                cardVO.removeNamedWhereClauseParam("cc");
                cardVO.removeNamedWhereClauseParam("cgmain");
                cardVO.removeNamedWhereClauseParam("cgsub");
                cardVO.removeNamedWhereClauseParam("acid");
                cardVO.setWhereClause("");
                cardVO.executeQuery();
            }

            cardVO.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");

            cardVO.defineNamedWhereClauseParam("cardid", id, null);

            cardVO.defineNamedWhereClauseParam("cc",
                                               session.getAttribute(Constants.userLang),
                                               null);


            cardVO.executeQuery();


        }

        getBindings().getCardOverview().setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardOverview());


        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Exiting cardOverview function");
    }

    public void companyOverview() {

        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Inside companyOverview function");

        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;

        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtPartnerVO1Iterator");

        } else {
            log.severe(accessDC.getDisplayRecord() + this.getClass() +
                       " PrtPartnerVO1Iterator bindings is null");
            iter1 = null;
        }

        if (iter1 != null) {

            ViewObject partnerVO = iter1.getViewObject();
            partnerVO.setWhereClause("PARTNER_ID =: partid AND COUNTRY_CODE =: cc");
            partnerVO.defineNamedWhereClauseParam("partid", id, null);

            partnerVO.defineNamedWhereClauseParam("cc",
                                                  session.getAttribute(Constants.userLang),
                                                  null);

            partnerVO.executeQuery();

        }

        CallPrtCardsPerRVO("partner", id, "", "");

        if (session != null) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " partner value from session " +
                     partner.getPartnerValue());
        }
        if (partner.isCompanyOverview()) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " partner node clicked, company/partner Overview is true in partner object & partnerId " +
                     partner.getPartnerValue().toString());
            getBindings().getCompanyOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCompanyOverview());
        } else {
            hideAll();
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " partner node clicked But company/partner Overview is false in partner object & partnerId " +
                     partner.getPartnerValue().toString());
            getBindings().getRestrictedAccess().setVisible(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }
        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Exiting commpanyOverview function");
    }
}
