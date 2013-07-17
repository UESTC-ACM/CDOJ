/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Global DAO interface.
 * 
 * @param <Entity>
 *            Entity's type
 * @param <PK>
 *            Primary key's type
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public interface IDAO<Entity extends Serializable, PK extends Serializable> {
	/**
	 * Add entity into database, and return number of Row changed.
	 * 
	 * @param entity
	 *            entity to be added.
	 * @return number of rows changed.
	 * @throws AppException
	 */
	public Serializable add(Entity entity) throws AppException;

	/**
	 * Add entity or update entity, according to key value of the entity.
	 * 
	 * @param entity
	 *            entity to be added or updated
	 * @throws AppException
	 */
	public void addOrUpdate(Entity entity) throws AppException;

	/**
	 * Get entity by key value.
	 * 
	 * @param key
	 *            key value
	 * @return entity which key value matches
	 * @throws AppException
	 */
	public Entity get(PK key) throws AppException;

	/**
	 * Update an entity object.
	 * 
	 * @param entity
	 *            entity to be updated
	 * @throws AppException
	 */
	public void update(Entity entity) throws AppException;

	/**
	 * Delete entity from database.
	 * 
	 * @param entity
	 *            entity to be deleted
	 * @throws AppException
	 */
	public void delete(Entity entity) throws AppException;

	/**
	 * List all entities in tables.
	 * 
	 * @return entity list in tables.
	 * @throws AppException
	 */
	public List<?> findAll() throws AppException;

	/**
	 * List all entities in tables by conditions.
	 * 
	 * @param condition
	 *            extra conditions for query
	 * @return expected entity list
	 * @throws AppException
	 */
	public List<?> findAll(Condition condition) throws AppException;

	/**
	 * Count the number of records in the table.
	 * 
	 * @return number of records we query
	 * @throws AppException
	 */
	public Long count() throws AppException;

	/**
	 * Count the number of records in the table by conditions.
	 * 
	 * @param condition
	 *            condition object
	 * @return number of records we query
	 * @throws AppException
	 */
	public Long count(Condition condition) throws AppException;

	/**
	 * Get unique entity by the field name, if the field is not unique field,
	 * throw {@code AppException}.
	 * 
	 * @param fieldName
	 *            the unique field name
	 * @param value
	 *            field's value
	 * @return unique result, null if not exist
	 * @throws AppException
	 */
	public Entity getEntityByUniqueField(String fieldName, Object value)
			throws FieldNotUniqueException, AppException;

	/**
	 * Get unique entity by the field name, if the field is not unique field,
	 * throw {@code AppException}.
	 * 
	 * @param fieldName
	 *            the unique field name
	 * @param value
	 *            field's value
	 * @param propertyName
	 *            property's name for JoinColumn
	 * @param forceUnique
	 *            force the field's unique property
	 * @return unique result, null if not exist
	 * @throws FieldNotUniqueException
	 * @throws AppException
	 */
	public Entity getEntityByUniqueField(String fieldName, Object value,
			String propertyName, boolean forceUnique)
			throws FieldNotUniqueException, AppException;

	/**
	 * Count number of entities for custom counting.
	 * 
	 * @param condition
	 *            user custom condition entity
	 * @return number of records for database query result
	 * @throws AppException
	 */
	Long customCount(Condition condition) throws AppException;

	/**
	 * Get SQL where clause according to condition entity.
	 * 
	 * @param condition
	 *            specific condition entity
	 * @return where clause we need
	 * @throws AppException
	 */
	public String getSQLString(Condition condition) throws AppException;

	/**
	 * Update all records according condition entity.
	 * 
	 * @param properties
	 *            properties for setting
	 * @param condition
	 *            specific condition entity
	 * @throws AppException
	 */
	public void updateEntitiesByCondition(Map<String, Object> properties,
			Condition condition) throws AppException;

	/**
	 * Delete all records according condition entity.
	 * 
	 * @param condition
	 *            specific condition entity
	 * @throws AppException
	 */
	public void deleteEntitiesByCondition(Condition condition)
			throws AppException;

	/**
	 * Execute SQL immediately
	 */
	public void flush();

	/**
	 * Crate a hibernate query.
	 * 
	 * @param hql
	 *            hibernate query string
	 * @return number of rows effected
	 */
	public int executeHQL(String hql);

	/**
	 * Create a basic database query.
	 * 
	 * @param sql
	 *            SQL string for query
	 * @return number of rows effected
	 */
	public int executeSQL(String sql);
}
