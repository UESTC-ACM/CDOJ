package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserAdminEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserAdminSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * 
 * @author liverliu
 *
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseController{
  
  private DepartmentService departmentService;
  private GlobalService globalService;
  private UserService userService;
  
  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }
  @Autowired
  public void setGlobalService(GlobalService globalService) {
    this.globalService = globalService;
  }
  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }


  /**
   * Show userlist in admin dashboard
   * 
   * @param model
   * @return
   */
  @RequestMapping("list")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String list(ModelMap model) {
    model.put("departmentList", departmentService.getDepartmentList());
    model.put("authenticationTypeList", globalService.getAuthenticationTypeList());
    return "admin/user/userList";
  }
  
  /**
   * Search user in admin dashboard.
   * 
   * 
   * @param userCondition
   * @return
   */
  @RequestMapping("search")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody 
  Map<String, Object> search(@RequestBody UserCondition userCondition){
    
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = userService.count(userCondition);
      PageInfo pageInfo = buildPageInfo(count, userCondition.currentPage, 
          Global.RECORD_PER_PAGE, "", null);
      List<UserAdminSummaryDTO> userList = userService.adminSearch(userCondition, 
          pageInfo);
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "success");
      json.put("userList", userList);
    }  catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      json.put("result", "error");
      e.printStackTrace();
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }
  
  
  /**
   * Edit user only by adminstrator.
   * 
   * @param userAdminEditDTO
   * @param validateResult
   * @return
   */
  @RequestMapping("edit")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> edit(@RequestBody @Valid UserAdminEditDTO userAdminEditDTO,
                           BindingResult validateResult){
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        
        if(userAdminEditDTO.getType() != Global.AuthenticationType.ADMIN.ordinal()
         ||userAdminEditDTO.getType() != Global.AuthenticationType.CONSTANT.ordinal()
         ||userAdminEditDTO.getType() != Global.AuthenticationType.NORMAL.ordinal()) {
          throw new FieldException("type", "Type Error.");
        }
        UserDTO userDTO = null;
        if(userDTO == null && userAdminEditDTO.getUserId() != null){
          userDTO = userService.getUserDTOByUserId(userAdminEditDTO.getUserId());
        }
        if(userDTO == null && userAdminEditDTO.getUserName() != null){
          userDTO = userService.getUserDTOByUserName(userAdminEditDTO.getUserName());
        }
        if(userDTO == null && userAdminEditDTO.getEmail() != null ){
          userDTO = userService.getUserDTOByEmail(userAdminEditDTO.getEmail());
        }
        if(userDTO == null){
          throw new AppException("Can't find user.");
        }
        userDTO.setNickName(userAdminEditDTO.getNickName());
        userDTO.setSchool(userAdminEditDTO.getSchool());
        userDTO.setDepartmentId(userAdminEditDTO.getDepartmentId());
        userDTO.setStudentId(userAdminEditDTO.getStudentId());
        userDTO.setType(userAdminEditDTO.getType());
        userService.updateUser(userDTO);
        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }
}
