package cn.edu.uestc.acmicpc.db.condition.base;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.JoinedType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

@RunWith(BlockJUnit4ClassRunner.class)
public class ConditionTest {

  @Test
  public void testToSQLString_empty() {
    Condition condition = new Condition();
    Assert.assertEquals("", condition.toHQLString());
  }

  @Test
  public void testToSQLString_simpleEntry() throws AppException {
    Condition condition = new Condition();
    condition.addEntry("id", ConditionType.EQUALS, 1);
    Assert.assertEquals("where (id='1')", condition.toHQLString());
  }

  @Test
  public void testToSQLString_pairEntries() throws AppException {
    Condition condition = new Condition();
    condition.addEntry("id", ConditionType.GREATER_OR_EQUALS, 1);
    condition.addEntry("id", ConditionType.LESS_OR_EQUALS, 5);
    Assert.assertEquals("where (id>='1' and id<='5')", condition.toHQLString());
  }

  @Test
  public void testToSQLString_pairEntries_or() throws AppException {
    Condition condition = new Condition(JoinedType.OR);
    condition.addEntry("id", ConditionType.GREATER_OR_EQUALS, 1);
    condition.addEntry("id", ConditionType.LESS_OR_EQUALS, 5);
    Assert.assertEquals("where (id>='1' or id<='5')", condition.toHQLString());
  }

  @Test
  public void testToSQLString_pairConditions() throws AppException {
    Condition condition = new Condition(JoinedType.AND);
    Condition first = new Condition(JoinedType.OR);
    Condition second = new Condition(JoinedType.OR);
    first.addEntry("id", ConditionType.GREATER_OR_EQUALS, 1);
    first.addEntry("id", ConditionType.LESS_OR_EQUALS, 5);
    second.addEntry("price", ConditionType.GREATER_THAN, 10);
    second.addEntry("price", ConditionType.LESS_OR_EQUALS, 20);
    condition.addEntry(first);
    condition.addEntry(second);
    Assert.assertEquals(
        "where ((id>='1' or id<='5') and (price>'10' or price<='20'))",
        condition.toHQLString());
  }

  @Test
  public void testToSQLString_pairConditions_bothNull() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(new Condition());
    condition.addEntry(new Condition());
    Assert.assertEquals("", condition.toHQLString());
  }
}
