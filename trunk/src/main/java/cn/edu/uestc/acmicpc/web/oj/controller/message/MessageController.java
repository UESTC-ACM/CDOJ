package cn.edu.uestc.acmicpc.web.oj.controller.message;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
  private MessageService messageService;

  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @RequestMapping("fetch/{messageId}")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> fetch(HttpSession session,
                            @PathVariable("messageId") Integer messageId) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO currentUser = getCurrentUser(session);
      MessageDTO messageDTO = messageService.getMessageDTO(messageId);
      AppExceptionUtil.assertNotNull(messageDTO, "No such message.");
      if (!currentUser.getUserId().equals(messageDTO.getSenderId()) &&
          !currentUser.getUserId().equals(messageDTO.getReceiverId())) {
        throw new AppException("Permission denied.");
      }
      if (!messageDTO.getIsOpened()) {
        messageService.read(messageDTO.getMessageId());
      }
      json.put("message", messageDTO);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody MessageCondition messageCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO currentUser = getCurrentUser(session);
      if (currentUser != null) {
        Boolean valid = false;
        if (messageCondition.userId != null) {
          if (isAdmin(session) || currentUser.getUserId().equals(messageCondition.userId)) {
            valid = true;
          }
        } else {
          if (isAdmin(session) || currentUser.getUserId().equals(messageCondition.userAId)) {
            valid = true;
          }
        }
        if (valid) {
          Long count = messageService.count(messageCondition);
          PageInfo pageInfo = buildPageInfo(count, messageCondition.currentPage,
              Global.RECORD_PER_PAGE, null);
          List<MessageForUserDTO> messageList = messageService.getMessageForUserDTOList(messageCondition, pageInfo);
          json.put("list", messageList);
        }
      }
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

}
