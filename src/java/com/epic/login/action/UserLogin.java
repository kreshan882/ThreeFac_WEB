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
import com.epic.util.QRHelper;
import com.epic.util.SessionVarlist;
import com.epic.util.SystemMessage;
import com.epic.util.Util;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
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
public static int k=0;
    
    public String execute() {
        return SUCCESS;
    }
 public String goHomeByValQR() {
     System.out.println(">>>"+inputBean.getUserName());
        try {
            if (service.getDbUserDetails(inputBean)) {
 
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

                                //set user and sessionid to hashmap              
                                ServletContext sc = ServletActionContext.getServletContext();
                                HashMap<String, String> userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
                                if (userMap == null) {
                                    userMap = new HashMap<String, String>();
                                }
                                userMap.put(sub.getUsername(), session.getId());
                                sc.setAttribute(SessionVarlist.USERMAP, userMap);

                                
                                LogFileCreator.writeInfoToLog(SystemMessage.LOGIN_MSG + " User:" + inputBean.getUserName());

                                profilePageidList = service.getUserprofilePageidList(inputBean.getProfileId());
                                session.setAttribute("profilePageidList", profilePageidList);

                                HashMap<String, List<TaskBean>> pageTaskList = service.getAllPageTask(inputBean.getProfileId());
                                session.setAttribute("pageTaskList", pageTaskList);

                                Map<ModuleBean, List<PageBean>> modulePageList = service.getModulePageByUser(inputBean.getProfileId());
                                session.setAttribute("modulePageList", modulePageList);
                              
                                return "success";
                                

                     
               

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
    public String loginCheck() {

        try {
            if (service.getDbUserDetails(inputBean)) {
//                if (inputBean.getStatus() == Status.ACTIVE) {
//                        if (Util.generateHash(inputBean.getPassword()).equals(inputBean.getDbPassword())) {
//
//                          
//                                sub.setUsername(inputBean.getUserName());
//                                sub.setUserProfileId(inputBean.getProfileId());
//                                sub.setLogFilePath(InitConfigValue.LOGPATH);
//                                sub.setName(inputBean.getName());
//                                sub.setStatus(inputBean.getStatus());
//
//                                HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
//                                if (sessionPrevious != null) {
//                                    sessionPrevious.invalidate();
//                                }
//
//                                HttpSession session = ServletActionContext.getRequest().getSession(true);
//                                sub.setCurrentSessionId(session.getId());
//                                session.setAttribute("SessionObject", sub);
//
//                                //set user and sessionid to hashmap              
//                                ServletContext sc = ServletActionContext.getServletContext();
//                                HashMap<String, String> userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
//                                if (userMap == null) {
//                                    userMap = new HashMap<String, String>();
//                                }
//                                userMap.put(sub.getUsername(), session.getId());
//                                sc.setAttribute(SessionVarlist.USERMAP, userMap);
//
//                                
//                                LogFileCreator.writeInfoToLog(SystemMessage.LOGIN_MSG + " User:" + inputBean.getUserName());
//
//                                profilePageidList = service.getUserprofilePageidList(inputBean.getProfileId());
//                                session.setAttribute("profilePageidList", profilePageidList);
//
//                                HashMap<String, List<TaskBean>> pageTaskList = service.getAllPageTask(inputBean.getProfileId());
//                                session.setAttribute("pageTaskList", pageTaskList);
//
//                                Map<ModuleBean, List<PageBean>> modulePageList = service.getModulePageByUser(inputBean.getProfileId());
//                                session.setAttribute("modulePageList", modulePageList);
//                                if (inputBean.getStatus() == Status.CHECK_THREEFAC) {
//                                    return "changePassword";
//                                }else if (inputBean.getStatus() == Status.ACTIVE) {
//                                    return "success";
//                                }
//                                
//                                
//                            
//                        } else {
//                            addActionError(SystemMessage.LOGIN_INVALID_PW); //merchant paeeword wrong
//                            return "login";
//                        }
//                     
//                } else if (inputBean.getStatus() == Status.CHECK_THREEFAC) {
                                System.out.println("username:"+inputBean.getUserName());
                                System.out.println("Imei:"+inputBean.getImei());
                                
                                //Get encMsg from sequrity ws (for IMEI)
                                String EncMsg = "hello qr kreshanmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm";
                                String filePath = InitConfigValue.QR_IMG_PATH+"1234567"+".png";
                                QRHelper.createQRImage(new File(filePath), EncMsg, 1024, "png");
                                
                                
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
public String statusCgecking() {

    System.out.println("statusCgecking-K:"+k);
    k++;
//    if(k==4){
//        inputBean.setSuccess(true);
//        k=0;
//    }else{
//        inputBean.setSuccess(false);
//    }
    String resp=UserLogin.sendAndRec("check status");
    if("000".equals(resp)){
        inputBean.setSuccess(true);
    }else{
        inputBean.setSuccess(false);
    }
    return "statusCgecking";
    
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
    
    
     public static String sendAndRec(String req){
        String res="000";
        try{
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("enc_msg", req);
            System.out.println("::"+req);
            
            URL url = new URL("http://127.0.0.1:8080/ThreeFac_SEC/api/"+"login_validate");//mobile_Reg, send_qr_detail, login_validate
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(req);
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            res=in.readLine();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
         System.out.println("responce:"+res);
        return res;
    }

}
