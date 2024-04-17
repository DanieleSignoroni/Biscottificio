<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///W:\PthDev\Projects\Panthera\BiscottificioVerona\WebContent\dtd/xhtml1-transitional.dtd">
<html>
<!-- WIZGEN Therm 2.0.0 as Form - multiBrowserGen = true -->
<%=WebGenerator.writeRuntimeInfo()%>
  <head>
<%@ page contentType="text/html; charset=Cp1252"%>
<%@ page import= " 
  java.sql.*, 
  java.util.*, 
  java.lang.reflect.*, 
  javax.naming.*, 
  com.thera.thermfw.common.*, 
  com.thera.thermfw.type.*, 
  com.thera.thermfw.web.*, 
  com.thera.thermfw.security.*, 
  com.thera.thermfw.base.*, 
  com.thera.thermfw.ad.*, 
  com.thera.thermfw.persist.*, 
  com.thera.thermfw.gui.cnr.*, 
  com.thera.thermfw.setting.*, 
  com.thera.thermfw.collector.*, 
  com.thera.thermfw.batch.web.*, 
  com.thera.thermfw.batch.*, 
  com.thera.thermfw.pref.* 
"%> 
<%
  ServletEnvironment se = (ServletEnvironment)Factory.createObject("com.thera.thermfw.web.ServletEnvironment"); 
  BODataCollector YLgmSpecProdBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YLgmSpecProdForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YLgmSpecProdForm", "YLgmSpecProd", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/bvr/thip/produzione/ordese/YLgmSpecProd.js"); 
  YLgmSpecProdForm.setServletEnvironment(se); 
  YLgmSpecProdForm.setJSTypeList(jsList); 
  YLgmSpecProdForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YLgmSpecProdForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YLgmSpecProdForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YLgmSpecProdForm.getMode(); 
  String key = YLgmSpecProdForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  boolean conflitPresent = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YLgmSpecProdForm.outTraceInfo(getClass().getName()); 
        String collectorName = YLgmSpecProdForm.findBODataCollectorName(); 
                YLgmSpecProdBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YLgmSpecProdBODC instanceof WebDataCollector) 
            ((WebDataCollector)YLgmSpecProdBODC).setServletEnvironment(se); 
        YLgmSpecProdBODC.initialize("YLgmSpecProd", true, 0); 
        YLgmSpecProdForm.setBODataCollector(YLgmSpecProdBODC); 
        int rcBODC = YLgmSpecProdForm.initSecurityServices(); 
        mode = YLgmSpecProdForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YLgmSpecProdForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YLgmSpecProdBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YLgmSpecProdForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YLgmSpecProdForm); 
   request.setAttribute("menuBar", menuBar); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="menuBar"/> 
</jsp:include> 
<% 
  menuBar.write(out); 
  menuBar.writeChildren(out); 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YLgmSpecProdForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YLgmSpecProdForm.getBodyOnBeforeUnload()%>" onload="<%=YLgmSpecProdForm.getBodyOnLoad()%>" onunload="<%=YLgmSpecProdForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YLgmSpecProdForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YLgmSpecProdForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YLgmSpecProdBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YLgmSpecProdForm.getServlet()%>" method="post" name="YLgmSpecProdForm" style="height:100%"><%
  YLgmSpecProdForm.writeFormStartElements(out); 
%>

      <table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
        <tr>
          <td style="height:0">
            <% menuBar.writeElements(out); %> 

          </td>
        </tr>
        <tr>
          <td style="height:0">
            <% myToolBarTB.writeChildren(out); %> 

          </td>
        </tr>
        <tr>
          <td height="100%">
            <!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YLgmSpecProdForm); 
 mytabbed.addTab("tab1", "it.bvr.thip.produzione.ordese.resources.YLgmSpecProd", "tab1", "YLgmSpecProd", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;">
              <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
                <table style="width: 100%;">
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YLgmSpecProd", "IdLegame", null); 
   label.setParent(YLgmSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="IdLegame"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YLgmSpecProdIdLegame =  
     new com.thera.thermfw.web.WebTextInput("YLgmSpecProd", "IdLegame"); 
  YLgmSpecProdIdLegame.setParent(YLgmSpecProdForm); 
%>
<input class="<%=YLgmSpecProdIdLegame.getClassType()%>" id="<%=YLgmSpecProdIdLegame.getId()%>" maxlength="<%=YLgmSpecProdIdLegame.getMaxLength()%>" name="<%=YLgmSpecProdIdLegame.getName()%>" size="<%=YLgmSpecProdIdLegame.getSize()%>"><% 
  YLgmSpecProdIdLegame.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YLgmSpecProd", "IdArticolo", null); 
   label.setParent(YLgmSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="Articolo"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YLgmSpecProdArticolo =  
     new com.thera.thermfw.web.WebMultiSearchForm("YLgmSpecProd", "Articolo", false, false, true, 1, null, null); 
  YLgmSpecProdArticolo.setParent(YLgmSpecProdForm); 
  YLgmSpecProdArticolo.write(out); 
%>
<!--<span class="multisearchform" id="Articolo"></span>-->
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YLgmSpecProd", "IdCliente", null); 
   label.setParent(YLgmSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="Cliente"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YLgmSpecProdCliente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YLgmSpecProd", "Cliente", false, false, true, 1, null, null); 
  YLgmSpecProdCliente.setParent(YLgmSpecProdForm); 
  YLgmSpecProdCliente.write(out); 
%>
<!--<span class="multisearchform" id="Cliente"></span>-->
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YLgmSpecProd", "IdSpecifica", null); 
   label.setParent(YLgmSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="Specifica"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YLgmSpecProdSpecifica =  
     new com.thera.thermfw.web.WebMultiSearchForm("YLgmSpecProd", "Specifica", false, false, true, 1, null, null); 
  YLgmSpecProdSpecifica.setParent(YLgmSpecProdForm); 
  YLgmSpecProdSpecifica.write(out); 
%>
<!--<span class="multisearchform" id="Specifica"></span>-->
                    </td>
                  </tr>
                </table>
              <% mytabbed.endTab(); %> 
</div>
            </div><% mytabbed.endTabbed();%> 

     </td>
   </tr>
</table><!--</span>-->
          </td>
        </tr>
        <tr>
          <td style="height:0">
            <% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YLgmSpecProdForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YLgmSpecProdForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YLgmSpecProdForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YLgmSpecProdBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YLgmSpecProdForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YLgmSpecProdBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YLgmSpecProdBODC.getErrorList().getErrors()); 
           if(YLgmSpecProdBODC.getConflict() != null) 
                conflitPresent = true; 
     } 
     else 
        errors.add(new ErrorMessage("BAS0000010")); 
  } 
  catch(NamingException e) { 
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("CBS000025", errorMessage));  } 
  catch(SQLException e) {
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("BAS0000071", errorMessage));  } 
  catch(Throwable e) {
     e.printStackTrace(Trace.excStream);
  }
  finally 
  {
     if(YLgmSpecProdBODC != null && !YLgmSpecProdBODC.close(false)) 
        errors.addAll(0, YLgmSpecProdBODC.getErrorList().getErrors()); 
     try 
     { 
        se.end(); 
     }
     catch(IllegalArgumentException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
     catch(SQLException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
  } 
  if(!errors.isEmpty())
  { 
      if(!conflitPresent)
  { 
     request.setAttribute("ErrorMessages", errors); 
     String errorPage = YLgmSpecProdForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YLgmSpecProdBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YLgmSpecProdForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
