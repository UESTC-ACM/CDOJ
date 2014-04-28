#!/usr/bin/env python2
# coding=utf-8

import os
import stat

entities = []

def initEntities(dir_name):
    for item in os.listdir(dir_name):
        sub_path = os.path.join(dir_name, item)
        mode = os.stat(sub_path)[stat.ST_MODE]
        if stat.S_ISDIR(mode):
            initEntities(sub_path)
        elif sub_path[-5:] == '.java':
            entity = sub_path[sub_path.rfind('/') + 1: -5]
            entities.append(entity)

def generateDaoIface(entity):
    file_name = dao_iface_dir + 'I' + entity + 'DAO.java'
    out = open(file_name, 'w')
    out.write(
        '''package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.entity.{0};

/**
 * {0}DAO AOP interface.
 */
public interface I{0}DAO extends IDAO<{0}, Integer> {{
}}
'''.format(entity)
    )

def generateDaoImpl(entity):
    file_name = dao_impl_dir + entity + 'DAO.java'
    out = open(file_name, 'w')

    out.write(
        '''package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.I{0}DAO;
import cn.edu.uestc.acmicpc.db.entity.{0};

import org.springframework.stereotype.Repository;

/**
 * DAO for {0} entity.
 */
@Repository
public class {0}DAO extends DAO<{0}, Integer> implements I{0}DAO {{

  @Override
  protected Class<Integer> getPKClass() {{
    return Integer.class;
  }}

  @Override
  protected Class<{0}> getReferenceClass() {{
    return {0}.class;
  }}
}}
'''.format(entity)
    )

def generateDaos():
    for item in entities:
        generateDaoIface(item)
        generateDaoImpl(item)

if __name__ == '__main__':
    base_dir = os.getcwd()
    entity_dir = base_dir + '/src/main/java/cn/edu/uestc/acmicpc/db/entity/'
    initEntities(entity_dir)
    dao_iface_dir = base_dir + '/src/main/java/cn/edu/uestc/acmicpc/db/dao/iface/'
    dao_impl_dir = base_dir + '/src/main/java/cn/edu/uestc/acmicpc/db/dao/impl/'
    if not os.path.exists(dao_iface_dir):
        os.makedirs(dao_iface_dir)
    if not os.path.exists(dao_impl_dir):
        os.makedirs(dao_impl_dir)
    generateDaos()
    quit(0)
