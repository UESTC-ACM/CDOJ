package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.util.annotation.CSVMap;

/**
 * User in onsite contest
 */
public class OnsiteUserDTO {

  @CSVMap("userName")
  public String userName;

  @CSVMap("password")
  public String password;

  @CSVMap("teamName")
  public String teamName;

  @CSVMap("members")
  public String members;
}
