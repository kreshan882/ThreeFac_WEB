/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.action;

import com.epic.init.Status;
import com.epic.util.LogFileCreator;
import com.epic.login.bean.HomeValues;
import com.epic.login.bean.ModuleBean;
import com.epic.login.bean.PageBean;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.login.bean.UserLoginBean;
import com.epic.login.service.LoginService;
import com.epic.init.InitConfigValue;
import com.epic.util.SessionVarlist;
import com.epic.util.SystemMessage;
import com.epic.util.Util;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author kreshan
 */
public class UserLogin extends ActionSupport implements Action, ModelDriven<UserLoginBean> {

    UserLoginBean inputBean = new UserLoginBean();
    LoginService service = new LoginService();
    HomeValues homeValues = new HomeValues();
    SessionUserBean sub = new SessionUserBean();

    HttpServletRequest request = ServletActionContext.getRequest();
    List<String> profilePageidList = new ArrayList<String>();

    public String execute() {
        return SUCCESS;
    }

    public String loginCheck() {

        try {
            if (service.getDbUserDetails(inputBean)) {
                                System.out.println("username:"+inputBean.getUserName());
                                System.out.println("Imei:"+inputBean.getImei());
                                String EncMsg="hi qr code";
                                System.out.println("EncMsg:"+EncMsg);
                                
                                sub.setQrEncMsg(EncMsg);
                                sub.setUsername(inputBean.getUserName());
                                sub.setUserProfileId(inputBean.getProfileId());
                                sub.setLogFilePath(InitConfigValue.LOGPATH);
                                sub.setName(inputBean.getName());
                                sub.setStatus(inputBean.getStatus());

                                HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
                                if (sessionPrevious != null) {
                                    sessionPrevious.invalidate();
                                }

                                HttpSession session = ServletActionContext.getRequest().getSession(true);
                                sub.setCurrentSessionId(session.getId());
                                session.setAttribute("SessionObject", sub);
                    return "qrcoderead";
                    
//                }else {
//                    addActionError("User inactive");
//                    return "login";
//                }

            } else {
                addActionError(SystemMessage.LOGIN_INVALID);//unvalid user only
                return "login";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError("Error contact administrator");
            LogFileCreator.writeErrorToLog(ex);
        } finally {
            inputBean.setUserName("");
            inputBean.setPassword("");
        }
        return "login";

    }

    public String homeFunction() throws Exception {
        return SUCCESS;

    }

    @Override
    public UserLoginBean getModel() {
        return inputBean;
    }

    public String Logout() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            System.out.println("user login.........................");
            if (session != null) {

                if (inputBean.getMessage() != null && !inputBean.getMessage().isEmpty()) {
                    String message = inputBean.getMessage();
                    if (message.equals("error1")) {
                        addActionError("Access denied. Please login again.");
                    } else if (message.equals("error2")) {
                        addActionError("You have been logged another mechine.");
                    } else if (message.equals("error3")) {
                        addActionMessage("Your password changed successfully. Please login with the new password.");
                    } else if (message.equals("error4")) {
                        addActionError("Session timeout.");
                    }else if (message.equals("error5")) {
                        addActionError("Operation fail.");
                    }
                }

                SessionUserBean su = (SessionUserBean) session.getAttribute("SessionObject");
                if (su != null) {
                    System.out.println(SystemMessage.LOGOUT_MSG);
                } else {
                    addActionError("Session timeout.");
                }

                session.removeAttribute("SessionObject");
                session.removeAttribute("pageTaskList");
                session.removeAttribute("SessionHomeValues");
                session.removeAttribute("profilePageidList");
                session.removeAttribute("modulePageList");
                session.invalidate();
                session = null;
            } else {
                addActionError("Session timeout");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return LOGIN;
    }

}
