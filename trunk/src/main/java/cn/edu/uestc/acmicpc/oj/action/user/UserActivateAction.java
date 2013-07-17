/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.oj.action.user;

import cn.edu.uestc.acmicpc.db.dao.iface.IUserSerialKeyDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.ioc.dao.UserSerialKeyDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.UserDTOAware;
import cn.edu.uestc.acmicpc.ioc.util.EMailSenderAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.EMailSender;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.opensymphony.xwork2.validator.annotations.*;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * Action use for reset password.
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class UserActivateAction extends BaseAction implements
		UserSerialKeyDAOAware, EMailSenderAware, UserDTOAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7795250262388594969L;

	@Autowired
	private EMailSender eMailSender;

	private User targetUser;

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

	private String targetUserName;

	public String getTargetUserName() {
		return targetUserName;
	}

	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}

	/**
	 * User serial key for user activation.
	 */
	private String serialKey;

	@Autowired
	private IUserSerialKeyDAO userSerialKeyDAO;

	public String getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	/**
	 * Action to send user serial key.
	 * 
	 * @return {@code JSON} flag
	 */
	@LoginPermit(NeedLogin = true)
	@SkipValidation
	public String toSendSerialKey() {
		try {
			targetUser = userDAO.getEntityByUniqueField("userName",
					targetUserName);
			if (targetUser == null)
				throw new AppException("No such user!");
			UserSerialKey userSerialKey = userSerialKeyDAO
					.getEntityByUniqueField("userId", targetUser,
							"userByUserId", true);
			if (userSerialKey != null) {
				// less than 30 minutes
				/*
				 * if (new Date().getTime() - userSerialKey.getTime().getTime()
				 * <= 1800000) { throw new AppException(
				 * "serial key can only be generated in every 30 minutes."); }
				 */
			} else {
				userSerialKey = new UserSerialKey();
			}
			StringBuilder stringBuilder = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < Global.USER_SERIAL_KEY_LENGTH; ++i) {
				stringBuilder
						.append((char) (random.nextInt(126 - 33 + 1) + 33));
			}
			String serialKey = stringBuilder.toString();
			userSerialKey.setTime(new Timestamp(new Date().getTime()));
			userSerialKey.setSerialKey(serialKey);
			userSerialKey.setUserByUserId(targetUser);
			userSerialKeyDAO.update(userSerialKey);

			String url = settings.SETTING_HOST
					+ getActionURL("/user",
							"activate/" + targetUser.getUserName() + "/"
									+ StringUtil.encodeSHA1(serialKey));
			stringBuilder = new StringBuilder();
			stringBuilder.append("Dear ").append(targetUser.getUserName())
					.append(" :\n\n");
			stringBuilder
					.append("To reset your password, simply click on the link below or paste into the url field on your favorite browser:\n\n");
			stringBuilder.append(url).append("\n\n");
			stringBuilder
					.append("The activation link will only be good for 30 minutes, after that you will have to try again from the beginning. When you visit the above page, you'll be able to set your password as you like.\n\n");
			stringBuilder
					.append("If you have any questions about the system, feel free to contact us anytime at acm@uestc.edu.cn.\n\n");
			stringBuilder.append("The UESTC OJ Team.\n");

			eMailSender.send(targetUser.getEmail(), "UESTC Online Judge",
					stringBuilder.toString());

			json.put("result", "ok");
		} catch (AppException e) {
			json.put("result", "error");
			json.put("err_msg", e.getMessage());
		} catch (Exception e) {
			json.put("result", "error");
			json.put("err_msg", "Unknown exception occurred.");
			e.printStackTrace();
		}
		return JSON;
	}

	private String targetSerialKey;

	public String getTargetSerialKey() {
		return targetSerialKey;
	}

	public void setTargetSerialKey(String targetSerialKey) {
		this.targetSerialKey = targetSerialKey;
	}

	@SkipValidation
	public String toActivatePage() {
		return SUCCESS;
	}

	@Autowired
	private UserDTO userDTO;

	@Override
	public UserDTO getUserDTO() {
		return userDTO;
	}

	@Override
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	/**
	 * Action to activate user.
	 * 
	 * @return {@code JSON} flag
	 */
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "userDTO.userName", key = "error.userName.validation"),
			@RequiredStringValidator(fieldName = "userDTO.password", key = "error.password.validation") }, stringLengthFields = { @StringLengthFieldValidator(fieldName = "userDTO.password", key = "error.password.validation", minLength = "6", maxLength = "20", trim = false) }, customValidators = { @CustomValidator(type = "regex", fieldName = "userDTO.userName", key = "error.userName.validation", parameters = {
			@ValidationParameter(name = "expression", value = "\\b^[a-zA-Z0-9_]{4,24}$\\b"),
			@ValidationParameter(name = "trim", value = "false") }) }, fieldExpressions = { @FieldExpressionValidator(fieldName = "userDTO.passwordRepeat", expression = "userDTO.password == userDTO.passwordRepeat", key = "error.passwordRepeat.validation") })
	public String toActivateUser() {
		try {
			User user = userDAO.getEntityByUniqueField("userName",
					userDTO.getUserName());
			if (user == null) {
				addFieldError("userDTO.userName", "No such user!");
				return INPUT;
			}

			UserSerialKey userSerialKey = userSerialKeyDAO
					.getEntityByUniqueField("userId", user, "userByUserId",
							true);

			if (userSerialKey == null
					|| new Date().getTime() - userSerialKey.getTime().getTime() > 1800000) {
				addFieldError("targetSerialKey",
						"Serial Key exceed time limit! Please regenerate a new key.");
				return INPUT;
			}
			if (!StringUtil.encodeSHA1(userSerialKey.getSerialKey()).equals(
					targetSerialKey)) {
				addFieldError("targetSerialKey", "Serial Key is wrong!");
				return INPUT;
			}

			user.setPassword(StringUtil.encodeSHA1(userDTO.getPassword()));
			userDAO.update(user);
			userSerialKeyDAO.delete(userSerialKey);

			json.put("result", "ok");
		} catch (AppException e) {
			json.put("result", "error");
			json.put("err_msg", e.getMessage());
		} catch (Exception e) {
			json.put("result", "error");
			json.put("err_msg", "Unknown exception occurred.");
			e.printStackTrace();
		}
		return JSON;
	}

	@Override
	public void setUserSerialKeyDAO(IUserSerialKeyDAO userSerialKeyDAO) {
		this.userSerialKeyDAO = userSerialKeyDAO;
	}

	@Override
	public void setEMailSender(EMailSender eMailSender) {
		this.eMailSender = eMailSender;
	}
}
