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

package cn.edu.uestc.acmicpc.util.exception;

import java.io.Serializable;

/**
 * Global Application Exception class
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class AppException extends Exception implements Serializable {
    private static final long serialVersionUID = -4267655542557102261L;

    /**
     * Construct a new application exception with {@code null} as its detail
     * message and cause.
     */
    public AppException() {
        super();
    }

    /**
     * Construct a new exception with the specified detail message
     * and {@code null} cause.
     *
     * @param message the detail message
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * Construct a new exception with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new exception with the specified cause and
     * {@code null} detail message.
     *
     * @param cause the cause
     */
    public AppException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct a new exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     */
    public AppException(String message, Throwable cause,
                        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
