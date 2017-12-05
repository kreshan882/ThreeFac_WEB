/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cla.user.action;

import com.epic.cla.user.bean.UsrProfileBean;
import com.epic.cla.user.bean.UsrProfileManagementInputBean;
import com.epic.cla.user.service.UsrProfileManagementService;
import com.epic.init.Module;
import com.epic.init.PageVarList;
import com.epic.init.TaskVarList;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.util.AccessControlService;
import com.epic.util.Common;
import com.epic.util.LogFileCreator;
import com.epic.util.SystemMessage;
import com.epic.util.Util;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author kreshan
 */
public class UsrProfileManagement extends ActionSupport implements ModelDriven<UsrProfileManagementInputBean> , AccessControlService{

    UsrProfileManagementService  service = new UsrProfileManagementService();
    UsrProfileManagementInputBean inputBean = new UsrProfileManagementInputBean();

    public SessionUserBean getSub(){
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }
    
    public HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }
    
    
    public String execute() {
        return SUCCESS;
    }

     public String List() {
            try {
                List<UsrProfileBean> dataList = null;  
                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String orderBy = "";    
                
                if (!inputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                }

                dataList = service.loadData( inputBean,orderBy, from, rows);

                if (!dataList.isEmpty()) {
                    records = dataList.get(0).getFullCount();
                    inputBean.setRecords(records);
                    inputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    inputBean.setTotal(total);
                } else {
                    inputBean.setRecords(0L);
                    inputBean.setTotal(0);
                }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }
        return "list";
    }
    
       
     public String Add() {
        try {
            if (doValidationAdd(inputBean)) {
                
                if (service.addData( inputBean)) {
                    addActionMessage(SystemMessage.USRPROFILE_ADD);
                    LogFileCreator.writeInfoToLog(SystemMessage.USRPROFILE_ADD);
                } else {
                    addActionError(SystemMessage.USRPROFILE_ADD_FAIL);                    
                }
            }
            
        } catch (Exception ex) {
                addActionError(SystemMessage.USRPROFILE_ADD_FAIL);
                ex.printStackTrace();
                LogFileCreator.writeErrorToLog(ex);
        }
        
        return "add";
    }
        
    public String Find() {
        try {
            service.findData(inputBean);
        } catch (Exception e) { 
            e.printStackTrace();
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
            LogFileCreator.writeErrorToLog(e);
        }

        return "find";
    }
        
    
    public String Update() {
        try { 
                if (inputBean.getUpestatus().equals("-1")) {
                    addActionError(SystemMessage.USRPROFILE_STATUS_SELECT);
                    return "update";
                }
                if (service.updateData(inputBean)) { 
                    addActionMessage(SystemMessage.USR_PROFILE_UPDATED);             
                    LogFileCreator.writeInfoToLog(SystemMessage.USR_PROFILE_UPDATED);
                } else {
                    addActionError(SystemMessage.USR_PROFILE_UPDATED_ERROR);
                }
        } catch (Exception ex) {
            addActionError(SystemMessage.USR_PROFILE_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }
    
    public String Delete() {
        try {
            if(1==Integer.parseInt(inputBean.getUpprofileId())){
                inputBean.setMessage(SystemMessage.USRPROFILE_DELETED_ERROR_ADMIN);
                inputBean.setSuccess(false); 
                return "delete";
            }
            if(service.deleteData( inputBean)){
                 LogFileCreator.writeInfoToLog(SystemMessage.USRPROFILE_DELETED);
                 inputBean.setMessage(SystemMessage.USRPROFILE_DELETED);
                 inputBean.setSuccess(true);
           }else{
                 inputBean.setMessage(SystemMessage.USRPROFILE_DELETED_ERROR);
                 inputBean.setSuccess(false);
           }
                
        } catch (Exception ex) {
                inputBean.setMessage(SystemMessage.USRPROFILE_DELETED_ERROR);
                inputBean.setSuccess(false);
                ex.printStackTrace();
                LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

   
 

    
    public String loadModuleSection() {
        try {
            inputBean.getPageIdList().putAll(service.getPageList(inputBean.getUpmoduleId()));
        } catch (Exception e) {    
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }

        return "loaddata";
    }
    
    public String loadSectionTask() {
        
        try {
            service.getTaskListsLoad(inputBean);
        } catch (Exception e) {    
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }

        return "loaddata";
    }
    
       
    public String UpdateTask() {
        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateTaskData(inputBean)) {
                    
                    addActionMessage(SystemMessage.USR_PROFILE_UPDATED);             
                    LogFileCreator.writeInfoToLog(SystemMessage.USR_PROFILE_UPDATED);

                } else {
                    addActionError(SystemMessage.USR_PROFILE_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.USR_PROFILE_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }
    
    private boolean  doValidationAdd (UsrProfileManagementInputBean userBean) throws Exception{
        boolean ok = false;
        
        try {
            
            if (userBean.getProfilename() == null || userBean.getProfilename().isEmpty()) {
                addActionError(SystemMessage.USRPROFILE_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(userBean.getProfilename())) {
                addActionError(SystemMessage.USRPROFILE_NAME_INVALID);
                return ok;
            } else if (service.profilenameAlready(userBean.getProfilename())) {
                addActionError(SystemMessage.USRPROFILE_NAME_ALREADY);
                return ok;
            }else {
                ok = true;
            }
            
        } catch (Exception e) {
           throw e; 
        }
        return ok;
    
    }
    
        private boolean  doValidationUpdate (UsrProfileManagementInputBean userBean) throws Exception{
        boolean ok = false;
        
        try {
            
            if (userBean.getUpmoduleId().equals("-1")) {
                addActionError(SystemMessage.USRPROFILE_MODULE_SELECT);
                return ok;
            } else if (userBean.getUppageId().equals("-1")) {
                addActionError(SystemMessage.USRPROFILE_PAGE_SELECT);
                return ok;
            } else {
                ok = true;
            }
            
        } catch (Exception e) {
           throw e; 
        }
        return ok;
    
    }
    @Override
    public UsrProfileManagementInputBean getModel() {
        try {
            inputBean.getModuleIdList().putAll(service.getModuleList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputBean;
    }

     private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.USER_PROFILE_MANAGEMENT, request);
        inputBean.setVadd(true);
        inputBean.setVupdate(true);
        inputBean.setVdelete(true);
        inputBean.setVdownload(true);
        inputBean.setVresetpass(true);
        if (tasklist != null && tasklist.size() > 0) {
            for (TaskBean task : tasklist) {
                if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.ADD)) {
                    inputBean.setVadd(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.UPDATE)) {
                    inputBean.setVupdate(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.DELETE)) {
                    inputBean.setVdelete(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.DOWNLOAD)) {
                    inputBean.setVdownload(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.PWRESET)) {
                    inputBean.setVresetpass(false);
                } 
            }
        }

        return true;
    }
        
    @Override
    public boolean checkAccess(String method,int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.USER_PROFILE_MANAGEMENT;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("Find".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE;
        } else if ("Download".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } else if ("ResetPw".equals(method)) {
            task = TaskVarList.PWRESET;
        } else if ("UpdateTask".equals(method)) {
            task = TaskVarList.UPDATE;
        }
        
        if ("execute".equals(method)) {
            status = true;
        }else if ("loadModuleSection".equals(method)) {
            status = true;
        }else if ("loadSectionTask".equals(method)) {
            status = true;
        } else {
              HttpSession session = ServletActionContext.getRequest().getSession(false);
              status = new Common().checkMethodAccess(task, page, session);
        } 
       return status;
    }
}
