/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.session8tu.util;

/**
 * containing common constant for the application
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 23, 2012  
 */
public class BookConstant 
{
  /* intent name - use package name to ensure uniqueness */
  public static final String PACKAGE_NAME         = "com.exoplatform.session8tu";
  
  public static final String ADD_BOOK_INTENT      = PACKAGE_NAME + ".intent.AddBook";
  
  public static final String EDIT_BOOK_INTENT     = PACKAGE_NAME + ".intent.EditBook";

  public static final String DISPLAY_BOOK_INTENT  = PACKAGE_NAME + ".intent.BookDetail";
  
  public static final String RETURN_HOME_INTENT   = PACKAGE_NAME + ".intent.ReturnHome";
  
  public static final String BOOK                 = PACKAGE_NAME + ".Book";
}
