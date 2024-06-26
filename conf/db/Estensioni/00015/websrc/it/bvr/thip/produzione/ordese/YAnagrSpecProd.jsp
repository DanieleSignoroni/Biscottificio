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
  BODataCollector YAnagrSpecProdBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YAnagrSpecProdForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YAnagrSpecProdForm", "YAnagrSpecProd", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/bvr/thip/produzione/ordese/YAnagrSpecProd.js"); 
  YAnagrSpecProdForm.setServletEnvironment(se); 
  YAnagrSpecProdForm.setJSTypeList(jsList); 
  YAnagrSpecProdForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YAnagrSpecProdForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YAnagrSpecProdForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YAnagrSpecProdForm.getMode(); 
  String key = YAnagrSpecProdForm.getKey(); 
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
        YAnagrSpecProdForm.outTraceInfo(getClass().getName()); 
        String collectorName = YAnagrSpecProdForm.findBODataCollectorName(); 
                YAnagrSpecProdBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YAnagrSpecProdBODC instanceof WebDataCollector) 
            ((WebDataCollector)YAnagrSpecProdBODC).setServletEnvironment(se); 
        YAnagrSpecProdBODC.initialize("YAnagrSpecProd", true, 0); 
        YAnagrSpecProdForm.setBODataCollector(YAnagrSpecProdBODC); 
        int rcBODC = YAnagrSpecProdForm.initSecurityServices(); 
        mode = YAnagrSpecProdForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YAnagrSpecProdForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YAnagrSpecProdBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YAnagrSpecProdForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YAnagrSpecProdForm); 
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
  myToolBarTB.setParent(YAnagrSpecProdForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YAnagrSpecProdForm.getBodyOnBeforeUnload()%>" onload="<%=YAnagrSpecProdForm.getBodyOnLoad()%>" onunload="<%=YAnagrSpecProdForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YAnagrSpecProdForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YAnagrSpecProdForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YAnagrSpecProdBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YAnagrSpecProdForm.getServlet()%>" method="post" name="YAnagrSpecProdForm" style="height:100%"><%
  YAnagrSpecProdForm.writeFormStartElements(out); 
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
          <td>
            <% 
  WebTextInput YAnagrSpecProdIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YAnagrSpecProd", "IdAzienda"); 
  YAnagrSpecProdIdAzienda.setParent(YAnagrSpecProdForm); 
%>
<input class="<%=YAnagrSpecProdIdAzienda.getClassType()%>" id="<%=YAnagrSpecProdIdAzienda.getId()%>" maxlength="<%=YAnagrSpecProdIdAzienda.getMaxLength()%>" name="<%=YAnagrSpecProdIdAzienda.getName()%>" size="<%=YAnagrSpecProdIdAzienda.getSize()%>" type="hidden"><% 
  YAnagrSpecProdIdAzienda.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td height="100%">
            <!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YAnagrSpecProdForm); 
 mytabbed.addTab("tab1", "it.bvr.thip.produzione.ordese.resources.YAnagrSpecProd", "tab1", "YAnagrSpecProd", null, null, null, null); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAnagrSpecProd", "IdSpecifica", null); 
   label.setParent(YAnagrSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="IdSpecifica"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YAnagrSpecProdIdSpecifica =  
     new com.thera.thermfw.web.WebTextInput("YAnagrSpecProd", "IdSpecifica"); 
  YAnagrSpecProdIdSpecifica.setParent(YAnagrSpecProdForm); 
%>
<input class="<%=YAnagrSpecProdIdSpecifica.getClassType()%>" id="<%=YAnagrSpecProdIdSpecifica.getId()%>" maxlength="<%=YAnagrSpecProdIdSpecifica.getMaxLength()%>" name="<%=YAnagrSpecProdIdSpecifica.getName()%>" size="<%=YAnagrSpecProdIdSpecifica.getSize()%>"><% 
  YAnagrSpecProdIdSpecifica.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAnagrSpecProd", "TitoloSpecifica", null); 
   label.setParent(YAnagrSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="TitoloSpecifica"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YAnagrSpecProdTitoloSpecifica =  
     new com.thera.thermfw.web.WebTextInput("YAnagrSpecProd", "TitoloSpecifica"); 
  YAnagrSpecProdTitoloSpecifica.setParent(YAnagrSpecProdForm); 
%>
<input class="<%=YAnagrSpecProdTitoloSpecifica.getClassType()%>" id="<%=YAnagrSpecProdTitoloSpecifica.getId()%>" maxlength="<%=YAnagrSpecProdTitoloSpecifica.getMaxLength()%>" name="<%=YAnagrSpecProdTitoloSpecifica.getName()%>" size="<%=YAnagrSpecProdTitoloSpecifica.getSize()%>"><% 
  YAnagrSpecProdTitoloSpecifica.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAnagrSpecProd", "Testo", null); 
   label.setParent(YAnagrSpecProdForm); 
%><label class="<%=label.getClassType()%>" for="Testo"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YAnagrSpecProdTesto =  
     new com.thera.thermfw.web.WebTextArea("YAnagrSpecProd", "Testo"); 
  YAnagrSpecProdTesto.setParent(YAnagrSpecProdForm); 
%>
<textarea class="<%=YAnagrSpecProdTesto.getClassType()%>" cols="60" id="<%=YAnagrSpecProdTesto.getId()%>" maxlength="<%=YAnagrSpecProdTesto.getMaxLength()%>" name="<%=YAnagrSpecProdTesto.getName()%>" rows="5" size="<%=YAnagrSpecProdTesto.getSize()%>"></textarea><% 
  YAnagrSpecProdTesto.write(out); 
%>

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
  errorList.setParent(YAnagrSpecProdForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YAnagrSpecProdForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YAnagrSpecProdForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YAnagrSpecProdBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YAnagrSpecProdForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YAnagrSpecProdBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YAnagrSpecProdBODC.getErrorList().getErrors()); 
           if(YAnagrSpecProdBODC.getConflict() != null) 
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
     if(YAnagrSpecProdBODC != null && !YAnagrSpecProdBODC.close(false)) 
        errors.addAll(0, YAnagrSpecProdBODC.getErrorList().getErrors()); 
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
     String errorPage = YAnagrSpecProdForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YAnagrSpecProdBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YAnagrSpecProdForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
