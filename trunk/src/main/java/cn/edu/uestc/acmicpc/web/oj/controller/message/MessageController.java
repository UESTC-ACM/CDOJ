package cn.edu.uestc.acmicpc.web.oj.controller.message;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
  private final MessageService messageService;
  private final Settings settings;

  @Autowired
  public MessageController(MessageService messageService, Settings settings) {
    this.messageService = messageService;
    this.settings = settings;
  }

  @RequestMapping("fetch/{messageId}")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> fetch(HttpSession session,
      @PathVariable("messageId") Integer messageId) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDto currentUser = getCurrentUser(session);
      MessageDto messageDto = messageService.getMessageDto(messageId);
      AppExceptionUtil.assertNotNull(messageDto, "No such message.");
      if (!Objects.equals(currentUser.getUserId(), messageDto.getSenderId()) &&
          !Objects.equals(currentUser.getUserId(), messageDto.getReceiverId())) {
        throw new AppException("Permission denied.");
      }
      if (Objects.equals(currentUser.getUserId(), messageDto.getReceiverId())
          && !messageDto.getIsOpened()) {
        messageService.read(messageDto.getMessageId());
      }
      json.put("message", messageDto);
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
  public @ResponseBody Map<String, Object> search(HttpSession session,
      @RequestBody MessageCondition messageCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDto currentUser = getCurrentUser(session);
      Boolean valid = false;
      if (currentUser != null) {
        if (messageCondition.userId != null) {
          if (checkPermission(session, messageCondition.userId)) {
            valid = true;
          }
        } else {
          if (checkPermission(session, messageCondition.userAId)) {
            valid = true;
          }
        }
      }
      if (valid) {
        Long count = messageService.count(messageCondition);
        PageInfo pageInfo = buildPageInfo(count, messageCondition.currentPage,
            settings.RECORD_PER_PAGE, null);
        List<MessageForUserDto> messageList = messageService.getMessageForUserDtoList(
            messageCondition, pageInfo);
        json.put("list", messageList);
        json.put("pageInfo", pageInfo);
      } else {
        PageInfo pageInfo = buildPageInfo(0L, 1L,
            settings.RECORD_PER_PAGE, null);
        json.put("list", new LinkedList<>());
        json.put("pageInfo", pageInfo);
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
