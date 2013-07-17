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

package cn.edu.uestc.acmicpc.training.action.index;

import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingUserDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class RegisterAction extends BaseAction implements TrainingUserDAOAware, TrainingUserDTOAware {

    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(
                            fieldName = "trainingUserDTO.name",
                            key = "error.trainingUserName.validation"
                    )
            },
            stringLengthFields = {
                    @StringLengthFieldValidator(
                            fieldName = "trainingUserDTO.name",
                            key = "error.trainingUserName.validation",
                            minLength = "2",
                            maxLength = "24",
                            trim = false
                    )
            }
    )
    public String toRegister() {
        try {
            if (currentUser == null)
                throw new AppException("Please login first!");

            TrainingUser trainingUser = trainingUserDAO.getEntityByUniqueField("name", trainingUserDTO.getName());
            if (trainingUser != null) {
                addFieldError("trainingUserDTO.name", "Name has been used!");
                return INPUT;
            }

            trainingUser = trainingUserDTO.getEntity();
            trainingUser.setUserByUserId(currentUser);

            trainingUserDAO.add(trainingUser);

            json.put("result", "ok");
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
            return JSON;
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    @Autowired
    private ITrainingUserDAO trainingUserDAO;

    @Autowired
    private TrainingUserDTO trainingUserDTO;

    @Override
    public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
        this.trainingUserDAO = trainingUserDAO;
    }

    @Override
    public void setTrainingUserDTO(TrainingUserDTO trainingUserDTO) {
        this.trainingUserDTO = trainingUserDTO;
    }

    @Override
    public TrainingUserDTO getTrainingUserDTO() {
        return trainingUserDTO;
    }
}
