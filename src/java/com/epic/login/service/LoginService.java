/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.service;

import com.epic.db.DBConnection;
import com.epic.login.bean.ModuleBean;
import com.epic.login.bean.PageBean;
import com.epic.login.bean.TaskBean;
import com.epic.login.bean.UserLoginBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kreshan
 */
public class LoginService {

    public boolean getDbUserDetails(UserLoginBean ulb) throws Exception {
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;
        boolean isUser = false;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            String sql = "SELECT USERNAME,NAME,PROFILE_ID,STATUS,PASSWORD FROM WEB_USER WHERE USERNAME=? ";
            perSt = con.prepareStatement(sql);
            perSt.setString(1, ulb.getUserName().toLowerCase());
            //perSt.setString(2, Util.generateHash(ulb.getPassword()));
            res = perSt.executeQuery();
            if (res.next()) {
                ulb.setUserName(res.getString("USERNAME"));
                ulb.setProfileId(res.getInt("PROFILE_ID"));
                ulb.setStatus(res.getInt("STATUS"));
                ulb.setName(res.getString("NAME"));
                ulb.setDbPassword(res.getString("PASSWORD"));
                isUser = true;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (res != null) {
                res.close();
            }
            if (perSt != null) {
                perSt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return isUser;

    }

//    public boolean varifilogin(UserLoginBean userloginBean) throws Exception{
//
//        
//       
//        if (Util.generateHash(userloginBean.getPassword()).equals(userloginBean.getDBpassword())) {
//
//            return true;
//        } else {
//            return false;
//        }
//
//    }
    //get REC section page according to the user role
    public Map<ModuleBean, List<PageBean>> getModulePageByUser(int profileId) throws Exception {
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;
        Map<ModuleBean, List<PageBean>> modulePageList = null;
        
        modulePageList = new HashMap<ModuleBean, List<PageBean>>();

        ModuleBean module = new ModuleBean();
            module.setMODULE_ID("01");
            module.setMODULE_NAME("User Management");

                    List<PageBean> pageList= pageList = new ArrayList<PageBean>();
                    PageBean page = new PageBean();
                    page.setPAGE_ID("0101");
                    page.setPAGE_NAME("User management");
                    page.setPAGE_URL("usrMng");
                    pageList.add(page);
                    
                    PageBean page2 = new PageBean();
                    page2.setPAGE_ID("0102");
                    page2.setPAGE_NAME("User profilemanagement");
                    page2.setPAGE_URL("usrprofileMng");
                    pageList.add(page2);
                    
                    modulePageList.put(module, pageList);
                    
//        try {
//            con = DBConnection.getConnection();
//            //con.setAutoCommit(true);
//            perSt = con.prepareStatement("SELECT ISC.SECTION_ID,ISC.SECTION_NAME,ISC.SECTION_URL,ISP.MODULE_ID,ISP.DESCRIPTION,IP.PROFILE_ID "
//                    + " FROM MCS_MODULES ISP,MCS_SECTION ISC,MCS_USER_PROFILE_PRIVILAGE IP "
//                    + " WHERE ISP.MODULE_ID=IP.MODULE_ID AND ISC.SECTION_ID=IP.SECTION_ID AND IP.PROFILE_ID=? "
//                    + " GROUP BY ISC.SECTION_ID,ISC.SECTION_NAME,ISC.SECTION_URL,ISP.MODULE_ID,ISP.DESCRIPTION,IP.PROFILE_ID ORDER BY ISP.MODULE_ID,ISC.SECTION_ID ");
//
//            perSt.setInt(1, profileId);
//
//            res = perSt.executeQuery();
//            modulePageList = new HashMap<ModuleBean, List<PageBean>>();
//            String currentModule = "";
//            List<PageBean> pageList = null;
//            ModuleBean module = null;
//            while (res.next()) {
//                if (!res.getString("MODULE_ID").equals(currentModule)) {
//                    currentModule = res.getString("MODULE_ID");
//                    if (pageList != null && module != null) {
//                        modulePageList.put(module, pageList);
//                        pageList = null;
//                        module = null;
//                    }
//                    module = new ModuleBean();
//                    module.setMODULE_ID(res.getString("MODULE_ID"));
//                    module.setMODULE_NAME(res.getString("DESCRIPTION"));
//
//                    pageList = new ArrayList<PageBean>();
//                    PageBean page = new PageBean();
//                    page.setPAGE_ID(res.getString("SECTION_ID"));
//                    page.setPAGE_NAME(res.getString("SECTION_NAME"));
//                    page.setPAGE_URL(res.getString("SECTION_URL"));
//
//                    pageList.add(page);
//
//                } else {
//
//                    PageBean page = new PageBean();
//                    page.setPAGE_ID(res.getString("SECTION_ID"));
//                    page.setPAGE_NAME(res.getString("SECTION_NAME"));
//                    page.setPAGE_URL(res.getString("SECTION_URL"));
//
//                    pageList.add(page);
//                }
//            }
//            if (pageList != null && module != null) {
//                modulePageList.put(module, pageList);
//                pageList = null;
//                module = null;
//            }
//
//        } catch (Exception ex) {
////            con.rollback();
//            throw ex;
//        } finally {
//            if (res != null) {
//                res.close();
//            }
//            if (perSt != null) {
//                perSt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
        return modulePageList;

    }

    public List<String> getUserprofilePageidList(int dBuserProfile) throws SQLException, Exception {

        List<String> pageidlist = new ArrayList<String>();
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;

        pageidlist.add("0101");
        pageidlist.add("0102");
//        try {
//            con = DBConnection.getConnection();
//            //con.setAutoCommit(true);
//            String sql = "select SECTION_ID from MCS_USER_PROFILE_PRIVILAGE where PROFILE_ID=? GROUP BY SECTION_ID";
//            perSt = con.prepareStatement(sql);
//            perSt.setInt(1, dBuserProfile);
//            res = perSt.executeQuery();
//
//            while (res.next()) {
//                pageidlist.add(res.getString("SECTION_ID"));
//            }
//
//        } catch (Exception ex) {
////            con.rollback();
//            throw ex;
//        } finally {
//             if (res != null) {
//                res.close();
//            }
//            if (perSt != null) {
//                perSt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
        return pageidlist;

    }

    // please change below method
    public HashMap<String, List<TaskBean>> getAllPageTask(int profileID) throws Exception {
        HashMap<String, List<TaskBean>> pageTaskMap = new HashMap<String, List<TaskBean>>();
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;
        
        List<TaskBean> taskBeanList = null;
        TaskBean taskBean =null;
        taskBeanList = new ArrayList<TaskBean>();
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID("01");
                    taskBean.setTASK_NAME("01");
        taskBeanList.add(taskBean);
                    taskBean =null;
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID("02");
                    taskBean.setTASK_NAME("02");
        taskBeanList.add(taskBean);
                    taskBean =null;
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID("03");
                    taskBean.setTASK_NAME("03");
        taskBeanList.add(taskBean);
                    taskBean =null;
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID("04");
                    taskBean.setTASK_NAME("04");
        taskBeanList.add(taskBean);
        
        
        pageTaskMap.put("0101", taskBeanList);
        
        
        taskBeanList=null;
        taskBeanList = new ArrayList<TaskBean>();
                    taskBean =null;
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID("01");
                    taskBean.setTASK_NAME("01");
        taskBeanList.add(taskBean);
        
        pageTaskMap.put("0102", taskBeanList);
//        try {
//            con = DBConnection.getConnection();
//            //con.setAutoCommit(true);
//            String sql = "SELECT SECTION_ID,TASK_ID FROM MCS_USER_PROFILE_PRIVILAGE where PROFILE_ID=? ORDER BY SECTION_ID";
//            perSt = con.prepareStatement(sql);
//            perSt.setInt(1, profileID);
//            res = perSt.executeQuery();
//
//            String currentPage = "";
//            List<TaskBean> taskBeanList = new ArrayList<TaskBean>();
//            TaskBean taskBean;
//            while (res.next()) {
//
//                if (res.getString("SECTION_ID").equals(currentPage)) {
//                    taskBean = new TaskBean();
//                    taskBean.setTASK_ID(res.getString("TASK_ID"));
//                    taskBean.setTASK_NAME(res.getString("TASK_ID"));
//                    taskBeanList.add(taskBean);
//                } else {
//
//                    if (currentPage != "") {
//                        pageTaskMap.put(currentPage, taskBeanList);
//                        taskBeanList = null;
//                        taskBeanList = new ArrayList<TaskBean>();
//                    }
//
//                    currentPage = res.getString("SECTION_ID");
//                    taskBean = new TaskBean();
//                    taskBean.setTASK_ID(res.getString("TASK_ID"));
//                    taskBean.setTASK_NAME(res.getString("TASK_ID"));
//                    taskBeanList.add(taskBean);
//                }
//
//            }
//
//            if (currentPage != "" && taskBeanList != null) {
//                pageTaskMap.put(currentPage, taskBeanList);
//                taskBeanList = null;
//                taskBeanList = new ArrayList<TaskBean>();
//                currentPage = "";
//            }
//        } catch (Exception ex) {
////            con.rollback();
//            throw ex;
//        } finally {
//             if (res != null) {
//                res.close();
//            }
//            if (perSt != null) {
//                perSt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
        return pageTaskMap;
    }



}
