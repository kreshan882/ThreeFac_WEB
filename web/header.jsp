<%-- 
    Document   : header
    Created on : Aug 12, 2014, 6:09:22 PM
    Author     : kreshan
--%>



<%@page import="com.epic.util.ModuleComparator"%>
<%@page import="java.util.TreeSet"%>
<%@page import="com.epic.login.bean.PageBean"%>
<%@page import="com.epic.login.bean.ModuleBean"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>

<style type="text/css">
    /* Dropdown Button */
    .dropbtn {
        background-color: transparent;
        color: white;
        font-size: 16px;
        border: none;
        cursor: pointer;
        margin:0 122px;
    }

    /* Dropdown button on hover & focus */

    /* The container <div> - needed to position the dropdown content */
    .dropdownnav {
        position: relative;
        /*display: inline-block;*/
        width: 100%; /* Spans the width of the page */
        /*	height: 50px; */
        margin: 0; /* Ensures there is no space between sides of the screen and the menu */
        z-index: 99; /* Makes sure that your menu remains on top of other page elements */
        position: relative; 
        left: 87%;
        /*	background-color: #366b82;*/
        color: #000000 !important;
    }

    /* Dropdown Content (Hidden by Default) */
    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        min-width: 160px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    }

    /* Links inside the dropdown */
    .dropdown-content a {
        color: #525252;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        font-size: 13px;
        font-family: arial;
    }
    /* Show the dropdown menu on hover */
    .dropdownnav:hover .dropdown-content {
        display: block;
    }

    /* Change color of dropdown links on hover */
    .dropdown-content a:hover {background-color: #f1f1f1}

    /* Show the dropdown menu (use JS to add this class to the .dropdown-content container when the user clicks on the dropdown button) */
    .show {display:block;}
</style>
<script type="text/javascript">
    /* When the user clicks on the button, 
     toggle between hiding and showing the dropdown content */
//function myFunction() {
//    document.getElementById("myDropdown").classList.toggle("show");
//}

// Close the dropdown menu if the user clicks outside of it
//window.onclick = function(event) {
//  if (!event.target.matches('.dropimg')) {
//
//    var dropdowns = document.getElementsByClassName("dropdown-content");
//    var i;
//    for (i = 0; i < dropdowns.length; i++) {
//      var openDropdown = dropdowns[i];
//      if (openDropdown.classList.contains('show')) {
//        openDropdown.classList.remove('show');
//      }
//    }
//  }
//}
    $(document).ready(function () {
        var adStatus = ${SessionObject.adStatus};
        var userType = ${SessionObject.userType};
        var status = ${SessionObject.status};
        if (adStatus === 1 && userType === 1) {
            $("#changePwd").hide();
        }
        if(status===12){
            $("#mainMenu").hide();
        }
    })
    function clearCookie() {
        $.cookie("selectedpage", null, {path: "/"});
        $.cookie("selectedsec", null, {path: "/"});
    }
</script>
<div class="banner1">
    <div class="leftimg"></div>
    <div class="rightimg"></div>
</div><!--end of banner1-->

<div class="ticker">
    <div id="pheader" class="ticker_heading"></div><!--ticker current path-->
    <div class="username "><span>${SessionObject.username}</span></div> 

    <!--                    
    <div id="wrap">
                      <ul class="navbar">
                            
                          <li><div class="setting">
                                  <a class="logout-icon" href="#"></a></div>
                                    <ul>
                                        <li><a class="change_pswd" href="changePassword.action">Change Password</a></li>
                                       <li><a href="LogoutloginCall.action">Logout</a></li>
                                       
                                    </ul>         
                             </li>
                             
                      </ul>
    </div>
    -->

    <div class="dropdownnav">
        <button  class="dropbtn"><img class="dropimg" onclick="myFunction()" src="${pageContext.request.contextPath}/resources/images/setting.png"/></button>
        <div id="myDropdown" class="dropdown-content">
            <a id="changePwd" href="changePassword.action">Change Password</a>
            <a style="border-top: solid 1px rgb(207, 206, 206);" onclick="clearCookie()" href="LogoutloginCall.action">Logout</a>
        </div>
    </div>


</div><!--end of ticker-->    


<div id="mainMenu" class="left_menu_bar">
    <span  class="login_data"><div class="home"><a onclick="clearCookie()" style="position:fixed;border-color: transparent;" href="homeCall.action"><img src="${pageContext.request.contextPath}/resources/images/home.png"/></a></div></span>
    <div id="accordian">
        <ul>



            <%

                try {

                    HashMap<ModuleBean, List<PageBean>> sectionPageList = (HashMap<ModuleBean, List<PageBean>>) request.getSession().getAttribute("modulePageList");
//                    String warnmsg = request.getSession().getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD);
            %>



            <%
                    ModuleComparator sec1 = new ModuleComparator();
                    Set<ModuleBean> section = new TreeSet<ModuleBean>(sec1);

                    Set<ModuleBean> section1 = sectionPageList.keySet();
                    for (ModuleBean sec2 : section1) {
                        section.add(sec2);
                    }

                    int secId = 1;
                    int pageId = 1000;

                    out.println("<li class=\'main-navigation-menu\' id=\'treemenu\' >");

                    for (ModuleBean sec : section) {

                        out.println("<h3>" + sec.getMODULE_NAME() + "</h3>");

                        out.println("<ul id=\"" + secId + "\">");

                        //                        out.println("d.add(" + i + "," + 0 + ",\'" + sec.getDescription() + "\');");
                        List<PageBean> pageList = sectionPageList.get(sec);
                        for (PageBean pageBean : pageList) {

                            pageId++;

                            out.println("<li>");

                            out.println("<a id= " + pageId + " href=\'" + request.getContextPath() + "/" + pageBean.getPAGE_URL() + ".action\'>");
                            out.println("<span class=\'title\'>" + pageBean.getPAGE_NAME() + "</span>");
                            out.println("</a>");

                            out.println("</li>");

//                            out.println("d.add(" + j + "," + i + ",\'" + pageBean.getDescription() + "\'" + ",\' " + request.getContextPath() + pageBean.getUrl() + ".mpi\');");
                        }

                        out.println(" </ul>");

                        secId++;
                    }
                    out.println("</li>");

                } catch (Exception ee) {

                    ee.printStackTrace();
                }
            %>


        </ul>                   

    </div><!--end of accordian_menu_bar-->
</div><!--end of left_menu_bar-->
